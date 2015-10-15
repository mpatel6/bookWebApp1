<%-- 
    Document   : authorInputForm
    Created on : Sep 27, 2015, 6:11:01 PM
    Author     : Ankita
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>       
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author Input Form</title>
    </head>
    <body>
        
        <form id="authorEditData" name="authorEditData" method="POST" action="AuthorController?action=editData">
        <h1>Author Edit Page</h1>
         <input type="text" id="authorId" name="authorId" value="${author.getAuthorId()}" readonly>
        <input type="text" id="authorName" name="authorName" value="${author.getAuthorName()}">
        <input type="text" id="dateCreated" name="dateCreated" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${author.dateCreated}"></fmt:formatDate>">
        
        <input type="submit" id="authorInputSubmit" name="submit" value="Submit"   >
        </form>
    </body>
</html>
