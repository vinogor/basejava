<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.vinogor.model.ListOfOrganization" %>
<%@ page import="ru.vinogor.model.ListOfTextSection" %>
<%@ page import="ru.vinogor.model.TextSection" %>
<%@ page import="ru.vinogor.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.vinogor.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;
        <a href="resume?uuid=${resume.uuid}&action=edit">
            <img src="img/pencil.png">
        </a>
    </h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.vinogor.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>

    <table>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.vinogor.model.SectionType, ru.vinogor.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.vinogor.model.AbstractSection"/>

            <tr>
                <td><h2><a firm="type.firm">${type.title}</a></h2></td>
            </tr>

            <c:choose>

                <c:when test="${type=='OBJECTIVE'}">
                    <tr>
                        <td>
                            <h3><%=((TextSection) section).getContent()%></h3>
                        </td>
                    </tr>
                </c:when>

                <c:when test="${type=='PERSONAL'}">
                    <tr>
                        <td>
                            <%=((TextSection) section).getContent()%>
                        </td>
                    </tr>
                </c:when>

                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <tr>
                        <td>
                            <ul>
                                <c:forEach var="item" items="<%=((ListOfTextSection) section).getListOfItems()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>
                </c:when>

                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organizations" items="<%=((ListOfOrganization) section).getListOfDiffItems()%>">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${empty organizations.homePage.url}">
                                        <h3>${organizations.homePage.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${organizations.homePage.url}">${organizations.homePage.name}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="stage" items="${organizations.stages}">
                            <jsp:useBean id="stage" type="ru.vinogor.model.Stage"/>
                            <tr>
                                <%--<td > <%=HtmlUtil.formatDates(stage)%>--%>
                                <td > <%=(DateUtil.format(stage.getStart()) + " - " + DateUtil.format(stage.getEnd()))%>
                                    <b>${stage.headline}</b><br>${stage.content}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <br/>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
