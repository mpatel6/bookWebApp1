package edu.wctc.mpatel.bookwebapp1.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class AuthorService {

    private AuthorDaoStrategy dao;

    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;
    }

    public List<Author> listAllAuthors() throws Exception {
        return dao.listAllAuthors();
    }

    public void deleteAuthorById(Integer recordValue) throws Exception {
        dao.deleteAuthorById(recordValue);
    }

    public void addNewAuthor(Author author) throws Exception {
        dao.addNewAuthor(author);
    }

    public void updateAuthor(Author author) throws Exception {
        dao.updateAuthor(author);
    }

//    public static void main(String[] args) throws Exception {
//        AuthorService authorService = new AuthorService(new AuthorDAO(new MySqlDb(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book2", "root", "admin"));
//
//       //authorService.deleteAuthorById("author_id", "18");
//        List<Object> recordValues = new ArrayList<>();
//        recordValues.add("Aditi Shah");
//        recordValues.add("2007-05-06");
//
//        List<String> recordFields = new ArrayList<>();
//        recordFields.add("author_name");
//        recordFields.add("date_created");
//
//           authorService.addNewAuthor(new Author("Mack Patel", new Date()));
//     //   authorService.updateAuthor("author_id", "22", recordFields, recordValues);
//        List<Author> authorList = new ArrayList<>();
//        authorList = authorService.listAllAuthors();
//        for (Author author : authorList) {
//            System.out.println(author);
//        }
//
//    }
}
