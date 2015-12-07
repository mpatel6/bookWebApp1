

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List Using AJAX </title>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="authorList">
        <h1>Author List Using AJAX</h1>
   <form  name="listForm" method="POST" action="AuthorController">
            <sec:csrfInput />
            <input type="submit" name="action" value="homePage"/> 
            
            <sec:authorize access="hasAnyRole('ROLE_MGR')">
            <input type="submit" name="action" value="update"/>&nbsp
            <input type="submit" name="action" value="add"/>
            </sec:authorize>
      <!--  <input type="submit" name="submit" value="delete"/>&nbsp -->
            
            <table id="authorListTable" width="500" border="1" cellspacing="0" cellpadding="4">
                <thead>
                <tr style="background-color: black;color:white;">
                 <!--   <th align="left" class="tableHead">Update</th>-->
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Author Name</th>
                    <th align="right" class="tableHead">Date Added</th>
                </tr>
                </thead>
                <tbody id="authorTblBody">
                    
                </tbody>
            </table>
       </form>
      
            
            
         <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>  
            
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>
         <script src="resources/js/bookApp.js" type="text/javascript"></script>
    </body>
</html>
