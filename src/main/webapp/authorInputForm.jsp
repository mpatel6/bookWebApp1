<%-- 
    Document   : authorInputForm
    Created on : Sep 27, 2015, 6:11:01 PM
    Author     : Ankita
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author Input Form</title>
    </head>
    <body>

        <form id="authorData" name="authorData" method="POST" action="AuthorController">
            <h1>Author Information Input Page</h1>
            <input type="text" id="authorName" name="authorName" placeholder="Author Name" >
            <input type="text" id="dateCreated" name="dateCreated" placeholder="YYYY-MM-DD" >
            <input type="submit" id="addAuthorSubmit" name="action" value="add"   >
             <input type="submit" id="listPage" name="action" value="list"   >
            <br>
            <br>
            <br>

            <h1>Book Information Input For Above Author</h1>
            <input type="text" id="bookTitle" name="bookTitle" placeholder="Book Title" >
            <input type="text" id="isbn" name="isbn" placeholder="isbn" >

        </form>
    </body>
</html>
