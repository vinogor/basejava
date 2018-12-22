package ru.vinogor.web;

import ru.vinogor.model.Resume;
import ru.vinogor.storage.SqlStorage;
import ru.vinogor.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private Storage storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "1234");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        if (uuid == null) {
            response.getWriter().write("" +
                    "<table border = \"1\">" +
                    "<tr>" +
                    "   <th> uuid      </th> " +
                    "   <th> full name </th> " +
                    "</tr>"
            );

            for (Resume resume : storage.getAllSorted()) {
                response.getWriter().write("" +
                        "<tr> " +
                        "   <td>" + resume.getUuid() + "    </td>" +
                        "   <td>" + resume.getFullName() + "</td> " +
                        "</tr>"
                );
            }

            response.getWriter().write("</table>");
        } else {

            Resume resume = storage.get(uuid);
            response.getWriter().write("" +
                    "<table border = \"1\">" +
                    "<tr>" +
                    "   <th> uuid      </th>" +
                    "   <th> full name </th>" +
                    "</tr>" +
                    "<tr>" +
                    "   <td>" + resume.getUuid() + "</td>" +
                    "   <td>" + resume.getFullName() + "</td>" +
                    "</tr>" +
                    "</table>");
        }
    }
}