<%-- 
    Document   : listBooks
    Created on : Oct 24, 2015, 1:35:32 PM
    Author     : Ankita
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book List</title>
    </head>
    <body>
        <h1>Book List</h1>
        <form  name="listForm" method="POST" action="BookController">
            <input type="submit" name="action" value="homePage"/> 
            
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <input type="submit" name="action" value="update"/>&nbsp
            <input type="submit" name="action" value="add"/>
            <!--  <input type="submit" name="submit" value="delete"/>&nbsp -->
            </sec:authorize>
            
            
            <table width="500" border="1" cellspacing="0" cellpadding="4">
                <tr style="background-color: black;color:white;">
                    <!--   <th align="left" class="tableHead">Delete</th> -->
                    <th align="left" class="tableHead">Update</th>
                    <th align="left" class="tableHead">Book ID</th>
                    <th align="left" class="tableHead">Book Title</th>
                    <th align="right" class="tableHead">ISBN</th>
                    <th align="right" class="tableHead">Author ID</th>
                    <th align="right" class="tableHead">Author Name</th>
                </tr>
                <c:forEach var="b" items="${books}" varStatus="rowCount">
                    <c:choose>
                        <c:when test="${rowCount.count % 2 == 0}">
                            <tr style="background-color: white;">
                            </c:when>
                            <c:otherwise>
                            <tr style="background-color: #ccffff;">
                            </c:otherwise>
                        </c:choose>
                       <!-- <td><input type="checkbox" name="deleteAuthor" value="${b.bookId}"> </td>   -->  
                        <td><input type="checkbox" name="bookId" value="${b.bookId}"> </td>       
                        <td align="left"><a href="BookController?action=delete&deleteBook=${b.bookId}">${b.bookId}</a></td>
                        <td align="left">${b.title}</td>
                        <td align="left">${b.isbn}</td>
                        <td align="left">${b.authorId.authorId}</td>
                        <td align="left"> ${b.authorId.authorName}
                        <c:choose>
                                <c:when test="${not empty b.authorId}">
                                   
                                </c:when>
                                <c:otherwise>
                                    None
                                </c:otherwise>
                            </c:choose> 
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
            </c:if>
    </body>
</html>
