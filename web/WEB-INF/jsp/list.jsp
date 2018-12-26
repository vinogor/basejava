<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="ru.vinogor.model.Resume" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="ru.vinogor.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Delete</th>
            <th>Edit</th>
        </tr>

        <%--<%--%>
        <%--for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {--%>
        <%--%>--%>
        <%--<tr>--%>
        <%--<td><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%>--%>
        <%--</a>--%>
        <%--</td>--%>
        <%--<td><%=resume.getContacts(ContactType.EMAIL)%>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<%--%>
        <%--}--%>
        <%--%>--%>

        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="ru.vinogor.model.Resume"/>
            <tr>
                <%--<td><a href="resume?uuid=${resume.uuid}">${resume.fullName}</a></td>--%>
                <%--<td>${resume.getContacts(ContactType.EMAIL)}</td>--%>

                    <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                    <td><%=ContactType.EMAIL.toHtml(resume.getContacts(ContactType.EMAIL))%></td>
                    <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                    <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>

            </tr>
        </c:forEach>

    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>