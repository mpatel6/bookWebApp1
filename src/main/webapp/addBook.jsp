<%-- 
    Document   : addBook
    Created on : Oct 24, 2015, 2:55:19 PM
    Author     : Ankita
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Input Form</title>
    </head>
    <body>
        <form id="bookData" name="bookData" method="POST" action="BookController">


            <h1>Book Input</h1>
            <input type="text" id="bookTitle" name="title" placeholder="Book Title" >
            <input type="text" id="isbn" name="isbn" placeholder="isbn" >
            <select id="authorDropDown" name="authorId">
                
                <c:choose>
                    <c:when test="${not empty book.authorId}">
                        <option value="">None</option>
                        <c:forEach var="author" items="${authors}">                                       
                            <option value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">selected</c:if>>${author.authorName}</option>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="author" items="${authors}" varStatus="rowCount">                                       
                            <option value="${author.authorId}" <c:if test="${rowCount.count == 1}">selected</c:if>>${author.authorName}</option>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </select>
            <input type="submit" id="addAuthorSubmit" name="action" value="add"   >
            <input type="submit" id="listPage" name="action" value="list"   >

        </form>
    </body>
</html>
