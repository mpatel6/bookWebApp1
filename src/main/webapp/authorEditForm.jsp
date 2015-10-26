<%-- 
    Document   : authorInputForm
    Created on : Sep 27, 2015, 6:11:01 PM
    Author     : Ankita
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>       
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author Input Form</title>
    </head>
    <body>
        
        <form id="authorEditData" name="authorEditData" method="POST" action="AuthorController?">
        <h1>Author Edit Page</h1>
        <input type="text" id="authorId" name="authorId" value="${author.getAuthorId()}" readonly>
        <input type="text" id="authorName" name="authorName" value="${author.getAuthorName()}">
        <input type="text" id="dateCreated" name="dateCreated" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${author.dateCreated}"></fmt:formatDate>">        
        <input type="submit" id="authorUpdateSubmit" name="action" value="update">
        <input type="submit" id="listPage" name="action" value="list"   >
        <br>
        <br>
        
        
        
         <h1>Book Information Input For Above Author</h1>
            <input type="text" id="bookTitle" name="bookTitle" placeholder="Book Title" >
            <input type="text" id="isbn" name="isbn" placeholder="isbn" >
            <input type="submit" id="authorBookAddSubmit" name="action" value="addBookToAuthor">
            
            <br>
            <br>
        
        <table width="500" border="1" cellspacing="0" cellpadding="4">
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">Book Title</th>
                    <th align="left" class="tableHead">Book ISBN</th>
                </tr>
         
        <c:choose>
            <c:when test = "${not empty author.bookSet}">
                           
               <c:forEach var="a" items="${author.bookSet}">
                <tr>
                <td>${a.title} </td>
                <td>${a.isbn} </td>
                </tr>
              </c:forEach> 
            </c:when>
            
        </c:choose>
        
        </form>
    </body>
</html>
