
package edu.wctc.mpatel.bookwebapp1.model;

import java.sql.SQLException;
import java.util.List;


public interface AuthorDaoStrategy {

    public void deleteAuthorById(Integer authorId) throws Exception;

    public List<Author> listAllAuthors() throws SQLException, Exception;

    public void addNewAuthor(Author author) throws Exception;

    public void updateAuthor(Author author) throws Exception;
    
}
