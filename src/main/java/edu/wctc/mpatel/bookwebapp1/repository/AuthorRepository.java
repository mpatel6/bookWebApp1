
package edu.wctc.mpatel.bookwebapp1.repository;

import edu.wctc.mpatel.bookwebapp1.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    //Projection query:To get a single value like min or max
    //only get parts of object. Don't use automation one to many and use projection query with joins.
    //    @Query ("SELECT a.authorName from Author a")
    //    public Object[] findAllWithNameOnly();
    
    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.bookSet WHERE a.authorId = (:id)")
    public Author findByIdAndFetchBooksEagerly(@Param("id") Integer id);
    
}
