package edu.wctc.mpatel.bookwebapp1.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDao implements AuthorDaoStrategy {

    private DBStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public AuthorDao() {
    }

    public AuthorDao(DBStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void deleteAuthorById(String authorId) throws SQLException, ClassNotFoundException {
        db.openConnection(driverClass, url, userName, password);
        Integer authorID = Integer.parseInt(authorId);
        int noOfRecordDeleted = db.deleteRecordById("author", "author_id", authorID);
        db.closeConnection();

    }

    @Override
    public void addNewAuthor(Author author) throws SQLException, ClassNotFoundException {
        db.openConnection(driverClass, url, userName, password);

        List<String> recordFields = Arrays.asList("author_name", "date_created");
        List recordValues = Arrays.asList(author.getAuthorName(), author.getDateCreated());
        int noOfRecordsInserted = db.insertRecord("author", recordFields, recordValues);
        db.closeConnection();
        //  return noOfRecordsInserted;
    }

    @Override
    public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {
        db.openConnection(driverClass, url, userName, password);

        List<String> recordFields = Arrays.asList("author_name", "date_created");
        List recordValues = Arrays.asList(author.getAuthorName(), author.getDateCreated());

        int noOfRecordsUpdated = db.updateRecord("author", "author_id", author.getAuthorId(), recordFields, recordValues);
        db.closeConnection();
        // return noOfRecordsUpdated;
    }

    @Override
    public Author getAuthorById(String authorId) throws SQLException, ClassNotFoundException {
        db.openConnection(driverClass, url, userName, password);

        Map<String, Object> rawData = new HashMap<>();
        rawData = db.findRecordByPrimaryKey("Author", "author_id", authorId);
        Author author = new Author();
        Object obj = rawData.get("author_id");
        author.setAuthorId(Integer.parseInt(obj.toString()));

        String authorName = rawData.get("author_name") == null ? "" : rawData.get("author_name").toString();
        author.setAuthorName(authorName);

        obj = rawData.get("date_created");
        Date dateCreated = (Date) rawData.get("date_created");
        author.setDateCreated(dateCreated);
        db.closeConnection();

        return author;
    }

    @Override
    public List<Author> listAllAuthors() throws SQLException, ClassNotFoundException {
        db.openConnection(driverClass, url, userName, password);
        List<Author> authorList = new ArrayList<>();

        List<Map<String, Object>> rawData = new ArrayList<>();
        rawData = db.findAllRecords("author");

        for (Map rawRec : rawData) {
            Author author = new Author();
            Object obj = rawRec.get("author_id");
            author.setAuthorId(Integer.parseInt(obj.toString()));

            String authorName = rawRec.get("author_name") == null ? "" : rawRec.get("author_name").toString();
            author.setAuthorName(authorName);

            obj = rawRec.get("date_created");
            Date dateCreated = (Date) rawRec.get("date_created");
            author.setDateCreated(dateCreated);
            authorList.add(author);

        }

        db.closeConnection();

        return authorList;
    }

//    public static void main(String[] args) throws Exception {
//        AuthorDao dao = new AuthorDao(new MySqlDb(), "com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book2",
//                "root",
//                "admin");
////
//////        int noOfRecordsDeleted = dao.deleteAuthorById("author","author_id","11");
//////        System.out.println("Number of Records Deleted = " + noOfRecordsDeleted);
////        List<Object> recordValues = new ArrayList<>();
////        recordValues.add("David Shah");
////        recordValues.add("2015-05-06");
////
////        List<String> recordFields = new ArrayList<>();
////        recordFields.add("author_name");
////        recordFields.add("date_created");
////
//////        int noOfRecordsInserted = dao.addNewAuthor("author",recordFields,recordValues);
//////        System.out.println("Number of Records Added = " + noOfRecordsInserted);
//////        int noOfRecordsUpdated = dao.updateAuthor("author","author_id","12",recordFields,recordValues);
//////        System.out.println("Number of Records Added = " + noOfRecordsUpdated);
////        List<Author> authorList = new ArrayList<>();
////        authorList = dao.listAllAuthors();
////
////        for (Author author : authorList) {
////            System.out.println(author);
////        }
//        Author author = dao.getAuthorById("2");
//        System.out.println(author);
//    }

}
