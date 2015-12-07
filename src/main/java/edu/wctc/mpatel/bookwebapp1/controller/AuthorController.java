package edu.wctc.mpatel.bookwebapp1.controller;

import edu.wctc.mpatel.bookwebapp1.entity.Author;
import edu.wctc.mpatel.bookwebapp1.entity.Book;
import edu.wctc.mpatel.bookwebapp1.service.AuthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AuthorController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthorsAjax.jsp";
    private static final String ADD_PAGE = "/authorInputForm.jsp";
    private static final String UPDATE_PAGE = "/authorEditForm.jsp";
    private static final String HOME_PAGE = "/index.html";

    private static final String ACTION_PARAM = "action";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String HOME_PAGE_ACTION = "homePage";
    private static final String AJAX_LIST_ACTION = "listAjax";
    private static final String ADD_BOOK_TO_AUTHOR = "addBookToAuthor";

  

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
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        AuthorService authService = (AuthorService) ctx.getBean("authorService");
        PrintWriter out  = response.getWriter();
        try {
            Author author;
            Set<Book> books = new HashSet<>();
            Book book;
            String authorId = request.getParameter("authorId");
            String authorName = request.getParameter("authorName");
            String dateCreated = request.getParameter("dateCreated");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            
            String bookId = request.getParameter("bookId");
            String bookTitle = request.getParameter("bookTitle");
            String isbn = request.getParameter("isbn");
            
            Date date;
            //format.parse(dateCreated);                    

            switch (action) {
                case LIST_ACTION:
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;
//                case AJAX_LIST_ACTION:
//                    List<Author> authors = authService.findAll();
//                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
//                    
//                    authors.forEach((authorObj)->{
//                    jsonArrayBuilder.add(
//                    Json.createObjectBuilder()
//                            .add("authorId", authorObj.getAuthorId())
//                            .add("authorName", authorObj.getAuthorName())
//                            .add("dateCreated", authorObj.getDateCreated().toString())
//                    );
//                    
//                    });
//                    JsonArray authorsJson = jsonArrayBuilder.build();
//                    response.setContentType("application/json");
//                    out.write(authorsJson.toString());
//                    out.flush();
//                    return;
                    
                    

                case ADD_ACTION:
                    if (authorName == null) {
                        destination = ADD_PAGE;
                    } else {
                        author = new Author(0);
                        author.setAuthorName(authorName);
                        author.setDateCreated(format.parse(dateCreated));
                        
                        
                        book = new Book(0);
                        book.setTitle(bookTitle);
                        book.setIsbn(isbn);
                        book.setAuthorId(author);
                        books.add(book);
                        author.setBookSet(books);     
                        
                        authService.edit(author);
                        this.refreshList(request, authService);
                        destination = LIST_PAGE;
                    }
                    break;
                    
                 case ADD_BOOK_TO_AUTHOR:
                    if (bookTitle == null || isbn==null) {
                        author = authService.findByIdAndFetchBooksEagerly(authorId);
                        request.setAttribute("author", author);
                        destination = UPDATE_PAGE;
                    } else {
//                      if (authorName == null) {
//                        author = authService.find(new Integer(authorId));
//                        request.setAttribute("author", author);
//                        destination = UPDATE_PAGE;
//                    } else {
                        author = authService.findByIdAndFetchBooksEagerly(authorId);
                        author.setAuthorName(authorName);
                        author.setDateCreated(format.parse(dateCreated));
                        book = new Book(0);
                        book.setTitle(bookTitle);
                        book.setIsbn(isbn);
                        book.setAuthorId(author);
                        books.add(book);
                        author.setBookSet(books);  
                        authService.edit(author);
                        this.refreshList(request, authService);
                        destination = UPDATE_PAGE;
                    }
                    break;

                case UPDATE_ACTION:

                    if (authorName == null) {
                        author = authService.findByIdAndFetchBooksEagerly(authorId);
                        request.setAttribute("author", author);
                        destination = UPDATE_PAGE;
                    } else {
                        author = authService.findByIdAndFetchBooksEagerly(authorId);
                        author.setAuthorName(authorName);
                        author.setDateCreated(format.parse(dateCreated));
                        authService.edit(author);
                        this.refreshList(request, authService);
                        destination = LIST_PAGE;
                    }
                    break;
                
               

                case DELETE_ACTION:

                    String authorIdToDel = request.getParameter("deleteAuthor");
                    Author authorToDel = authService.findByIdAndFetchBooksEagerly(authorIdToDel);
                    authService.remove(authorToDel);
                    this.refreshList(request, authService);
                    break;

                case HOME_PAGE_ACTION:
                    //response.sendRedirect("index.html");
                    destination = HOME_PAGE;
                    break;

                default:
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                    destination = LIST_PAGE;
                    break;
            }

        } catch (IllegalArgumentException iae) {
            request.setAttribute("errMsg", iae.getMessage());
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

    private void refreshList(HttpServletRequest request, AuthorService authService) throws ClassNotFoundException {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }

    @Override
    public void init() throws ServletException {

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
