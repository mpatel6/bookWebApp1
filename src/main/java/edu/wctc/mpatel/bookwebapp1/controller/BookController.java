/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mpatel.bookwebapp1.controller;

import edu.wctc.mpatel.bookwebapp1.entity.Author;
import edu.wctc.mpatel.bookwebapp1.entity.Book;
import edu.wctc.mpatel.bookwebapp1.service.AuthorFacade;
import edu.wctc.mpatel.bookwebapp1.service.BookFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ankita
 */
public class BookController extends HttpServlet {

    private static final String LIST_PAGE = "/listBooks.jsp";
    private static final String ADD_PAGE = "/addBook.jsp";
    private static final String UPDATE_PAGE = "/updateBook.jsp";
    private static final String HOME_PAGE = "/index.html";

    private static final String ACTION_PARAM = "action";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String HOME_PAGE_ACTION = "homePage";
//    private static final String ADD_BOOK_TO_AUTHOR = "addBookToAuthor";

    @Inject
    private BookFacade bookService;

    @Inject
    private AuthorFacade authService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        Book book = null;

        try {

            String title = request.getParameter("title");
            String isbn = request.getParameter("isbn");
            String authorId = request.getParameter("authorId");
            String bookId = request.getParameter("bookId");

            switch (action) {
                case LIST_ACTION:
                    this.refreshBookList(request, bookService);
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;
                case HOME_PAGE_ACTION:
                    destination = HOME_PAGE;
                    break;

                case ADD_ACTION:
                    if (title == null) {
                        destination = ADD_PAGE;
                        this.refreshBookList(request, bookService);
                        this.refreshList(request, authService);
                    } else {

                        book = new Book(0);
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        Author author = null;
                        if (authorId != null) {
                            author = authService.find(new Integer(authorId));
                            book.setAuthorId(author);
                        }
                        bookService.edit(book);
                        this.refreshBookList(request, bookService);
                        this.refreshList(request, authService);
                        destination = LIST_PAGE;
                    }
                    break;
                case UPDATE_ACTION:
                    if (title == null) {
                        book = bookService.find(new Integer(bookId));
                        request.setAttribute("book", book);
                        destination = UPDATE_PAGE;
                        this.refreshBookList(request, bookService);
                        this.refreshList(request, authService);

                    } else {
                        book = bookService.find(new Integer(bookId));
                        book.setTitle(title);
                        book.setIsbn(isbn);
                        Author author = null;
                        if (authorId != null) {
                            author = authService.find(new Integer(authorId));
                            book.setAuthorId(author);
                        }
                        bookService.edit(book);
                        this.refreshBookList(request, bookService);
                        this.refreshList(request, authService);
                        destination = LIST_PAGE;
                    }
                    break;

                case DELETE_ACTION:
                    String bookIdToDel = request.getParameter("deleteBook");
                    Book bookToDel = bookService.find(new Integer(bookIdToDel));
                    bookService.remove(bookToDel);
                    this.refreshBookList(request, bookService);
                    this.refreshList(request, authService);
                    break;

                default:
                    this.refreshBookList(request, bookService);
                    this.refreshList(request, authService);
                    destination = LIST_PAGE;
                    break;

            }
        } catch (IllegalArgumentException iae) {
            request.setAttribute("errMsg", iae.getMessage());
        } catch (ClassNotFoundException cnfe) {
            request.setAttribute("errMsg", cnfe.getCause().getMessage());
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getMessage());
        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);

    }

    private void refreshBookList(HttpServletRequest request, BookFacade bookService) throws ClassNotFoundException {
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
    }

    private void refreshList(HttpServletRequest request, AuthorFacade authService) throws ClassNotFoundException {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
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
