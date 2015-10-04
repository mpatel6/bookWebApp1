
package edu.wctc.mpatel.bookwebapp1.model;

import java.sql.SQLException;
import java.util.List;


public interface AuthorDaoStrategy {

    public void deleteAuthorById(String authorId) throws ClassNotFoundException, SQLException;

    public List<Author> listAllAuthors() throws SQLException, ClassNotFoundException;

    public void addNewAuthor(Author author) throws SQLException, ClassNotFoundException;

    public void updateAuthor(Author author) throws SQLException, ClassNotFoundException;
    
    public Author getAuthorById(String authorId) throws SQLException, ClassNotFoundException;
}
