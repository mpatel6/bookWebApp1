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
        
        <form id="authorData" name="authorData" method="POST" action="AuthorController?authorInput=inputData">
        <h1>Author Information Input Page</h1>
        <input type="text" id="authorName" name="authorName" placeholder="Enter Author Name" >
        <input type="date" id="dateCreated" name="dateCreated" placeholder="Enter Date Created YYYY-MM-DD" >
        <input type="submit" id="authorInputSubmit" name="action" value="list"   >
        </form>
    </body>
</html>
