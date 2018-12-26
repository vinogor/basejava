package ru.vinogor.web;

import ru.vinogor.Config;
import ru.vinogor.model.ContactType;
import ru.vinogor.model.Resume;
import ru.vinogor.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException  {

        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

//        request.setAttribute("resumes", storage.getAllSorted());
//        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }
}

//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
//
//        String uuid = request.getParameter("uuid");
//        if (uuid == null) {
//            response.getWriter().write("" +
//                    "<table border = \"1\">" +
//                    "<tr>" +
//                    "   <th> uuid      </th> " +
//                    "   <th> full name </th> " +
//                    "</tr>"
//            );
//
//            for (Resume resume : storage.getAllSorted()) {
//                response.getWriter().write("" +
//                        "<tr> " +
//                        "   <td>" + resume.getUuid() + "    </td>" +
//                        "   <td>" + resume.getFullName() + "</td> " +
//                        "</tr>"
//                );
//            }
//
//            response.getWriter().write("</table>");
//        } else {
//
//            Resume resume = storage.get(uuid);
//            response.getWriter().write("" +
//                    "<table border = \"1\">" +
//                    "<tr>" +
//                    "   <th> uuid      </th>" +
//                    "   <th> full name </th>" +
//                    "</tr>" +
//                    "<tr>" +
//                    "   <td>" + resume.getUuid() + "</td>" +
//                    "   <td>" + resume.getFullName() + "</td>" +
//                    "</tr>" +
//                    "</table>");
//        }
//    }
//}