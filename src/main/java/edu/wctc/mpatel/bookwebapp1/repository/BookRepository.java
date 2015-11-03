/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mpatel.bookwebapp1.repository;


import edu.wctc.mpatel.bookwebapp1.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ankita
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable  {
    
}
