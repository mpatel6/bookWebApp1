package edu.wctc.mpatel.bookwebapp1.controller;

import edu.wctc.mpatel.bookwebapp1.model.Author;
import edu.wctc.mpatel.bookwebapp1.model.AuthorDaoStrategy;
import edu.wctc.mpatel.bookwebapp1.model.AuthorService;
import edu.wctc.mpatel.bookwebapp1.model.DBStrategy;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class AuthorController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String ADD_PAGE = "/authorInputForm.jsp";
    private static final String UPDATE_PAGE = "/authorEditForm.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String UPDATE_DELETE_ACTION = "updateDelete";
    private static final String ACTION_PARAM = "action";
    private static final String SUBMIT_ACTION = "submit";
    private static final String AUTHOR_INPUT = "inputData";
    private static final String UPDATE_INPUT = "editData";

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private DBStrategy db;
    private AuthorDaoStrategy authorDao;
    private String databaseName;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Statement at start");
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);

        try {

            AuthorService authService = injectDependenciesAndGetAuthorService();

            switch (action) {
                case LIST_ACTION:
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;

                case ADD_ACTION:

                    destination = ADD_PAGE;
                    System.out.println("Statement after add page destinatin set");

                    break;

                case AUTHOR_INPUT:
                    String authorName = request.getParameter("authorName");
                    String dateCreated = request.getParameter("dateCreated");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(dateCreated);
                    Author author = new Author(authorName, date);
                    authService.addNewAuthor(author);
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;

                case UPDATE_ACTION:
                    String authorId = request.getParameter("updateAuthor");

                    Author editAuthor = authService.getAuthorById(authorId);
                    request.setAttribute("author", editAuthor);
                    destination = UPDATE_PAGE;
                    break;

                case UPDATE_INPUT:

                    String authorId1 = request.getParameter("authorId");
                    String authorName1 = request.getParameter("authorName");
                    String dateCreated1 = request.getParameter("dateCreated");
                    DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = format1.parse(dateCreated1);
                    Author author1 = new Author(Integer.parseInt(authorId1), authorName1, date1);
                    authService.updateAuthor(author1);
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;

                case DELETE_ACTION:
                    authService.deleteAuthorById(request.getParameter("deleteAuthor"));
                    this.refreshList(request, authService);

                    break;

                case UPDATE_DELETE_ACTION:
                    String subAction = request.getParameter(SUBMIT_ACTION);

                    if (subAction.equals(DELETE_ACTION)) {
                        authService.deleteAuthorById(request.getParameter("deleteAuthor"));
                        this.refreshList(request, authService);

                    } else if (subAction.equals(UPDATE_ACTION)) {
                        String authorId2 = request.getParameter("updateAuthor");

                        Author editAuthor2 = authService.getAuthorById(authorId2);
                        request.setAttribute("author", editAuthor2);
                        destination = UPDATE_PAGE;

                    }

                    break;
                default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = LIST_PAGE;
                    break;
            }

        } catch (IllegalArgumentException iae) {
            request.setAttribute("errMsg", iae.getMessage());
        } catch (SQLException e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        } catch (ClassNotFoundException cnfe) {
            request.setAttribute("errMsg", cnfe.getCause().getMessage());
        } catch (ParseException pe) {
            request.setAttribute("errMsg", pe.getMessage());
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getMessage());
        }

        
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }

    private void refreshList(HttpServletRequest request, AuthorService authService) throws SQLException, ClassNotFoundException {
        List<Author> authors = authService.listAllAuthors();
        request.setAttribute("authors", authors);
    }

    private AuthorService injectDependenciesAndGetAuthorService() throws Exception {

        Class dbClass = Class.forName(dbStrategyClassName);
        DBStrategy db = (DBStrategy) dbClass.newInstance();
        AuthorDaoStrategy authorDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor =  null;
//          try{
//         constructor = daoClass.getConstructor(new Class[]{
//            DBStrategy.class, String.class, String.class, String.class, String.class
//        });
//         } catch(NoSuchMethodException nsme){
//        
//         }
//         if (constructor != null) {
//        Object[] constructorArgs = new Object[]{
//            db, driverClass, url, userName, password
//        };
//        authorDao = (AuthorDaoStrategy) constructor
//                .newInstance(constructorArgs);
//
//          } else {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/book2");
            constructor = daoClass.getConstructor(new Class[]{
                DBStrategy.class, DataSource.class 
            });
            Object[] constructorArgs = new Object[]{
                db, ds
            };
            authorDao = (AuthorDaoStrategy) constructor
                    .newInstance(constructorArgs);
//        }
        return new AuthorService(authorDao);
    }

    @Override
    public void init() throws ServletException {

        driverClass = getServletContext().getInitParameter("driverClass");
        url = getServletContext().getInitParameter("url");
        userName = getServletContext().getInitParameter("userName");
        password = getServletContext().getInitParameter("password");
        dbStrategyClassName = this.getServletContext().getInitParameter("dbStrategy");
        daoClassName = this.getServletContext().getInitParameter("authorDao");
        databaseName = getServletContext().getInitParameter("databaseName");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
