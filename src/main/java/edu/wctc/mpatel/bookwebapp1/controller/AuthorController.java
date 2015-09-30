package edu.wctc.mpatel.bookwebapp1.controller;

import edu.wctc.mpatel.bookwebapp1.model.Author;
import edu.wctc.mpatel.bookwebapp1.model.AuthorDaoStrategy;
import edu.wctc.mpatel.bookwebapp1.model.AuthorService;
import edu.wctc.mpatel.bookwebapp1.model.DBStrategy;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String ADD_PAGE = "/authorInputForm.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    private static final String SUBMIT_ACTION = "submit";

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private DBStrategy db;
    private AuthorDaoStrategy authorDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
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
                    
                    String subAction = request.getParameter(SUBMIT_ACTION);
                    
                    
//                    if (subAction.equals(ADD_EDIT_ACTION)) {
//                        // must be add or edit, go to addEdit page
//                        String[] authorIds = request.getParameterValues("authorId");
//                        if (authorIds == null) {
//                            // must be an add action, nothing to do but
//                            // go to edit page
//                        } else {
//                            // must be an edit action, need to get data
//                            // for edit and then forward to edit page
//                            
//                            // Only process first row selected
//                            String authorId = authorIds[0];
//                            Author author = authService.getAuthorById(authorId);
//                            request.setAttribute("author", author);
//                        }
//
//                        destination = ADD_EDIT_PAGE;
//
//                    } else {
//                        // must be DELETE
//                        // get array based on records checked
//                        String[] authorIds = request.getParameterValues("authorId");
//                        for (String id : authorIds) {
//                            authService.deleteAuthorById(id);
//                        }
//
//                        this.refreshList(request, authService);
//                        destination = LIST_PAGE;
//                    }
                    break;
                    
                case UPDATE_ACTION:
//                    String authorName = request.getParameter("authorName");
//                    String authorId = request.getParameter("authorId");
//                    authService.saveOrUpdateAuthor(authorId, authorName);
//                    this.refreshList(request, authService);
//                    destination = LIST_PAGE;
                    break;
                    
                case DELETE_ACTION:
//                    this.refreshList(request, authService);
//                    destination = LIST_PAGE;
                    break;

                default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = LIST_PAGE;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }

    private void refreshList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.listAllAuthors();
        request.setAttribute("authors", authors);
    }

    private AuthorService injectDependenciesAndGetAuthorService() throws Exception {

        Class dbClass = Class.forName(dbStrategyClassName);
        DBStrategy db = (DBStrategy) dbClass.newInstance();
        AuthorDaoStrategy authorDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor = daoClass.getConstructor(new Class[]{
            DBStrategy.class, String.class, String.class, String.class, String.class
        });

        // if (constructor != null) {
        Object[] constructorArgs = new Object[]{
            db, driverClass, url, userName, password
        };
        authorDao = (AuthorDaoStrategy) constructor
                .newInstance(constructorArgs);

      //  } else {
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource) ctx.lookup("jdbc/book");
//            constructor = daoClass.getConstructor(new Class[]{
//                DataSource.class, DBStrategy.class
//            });
//            Object[] constructorArgs = new Object[]{
//                ds, db
//            };
//            authorDao = (AuthorDaoStrategy) constructor
//                    .newInstance(constructorArgs);
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
