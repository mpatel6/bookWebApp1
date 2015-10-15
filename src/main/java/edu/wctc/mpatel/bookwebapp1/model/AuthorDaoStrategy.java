
package edu.wctc.mpatel.bookwebapp1.model;

import java.sql.SQLException;
import java.util.List;


public interface AuthorDaoStrategy {

    public void deleteAuthorById(String authorId) throws ClassNotFoundException, DataAccessException;

    public List<Author> listAllAuthors() throws DataAccessException, ClassNotFoundException;

    public void addNewAuthor(Author author) throws DataAccessException, ClassNotFoundException;

    public void updateAuthor(Author author) throws DataAccessException, ClassNotFoundException;
    
    public Author getAuthorById(String authorId) throws DataAccessException, ClassNotFoundException;
}
