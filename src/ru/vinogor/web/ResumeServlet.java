package ru.vinogor.web;

import ru.vinogor.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        storage = Config.get().getStorage();
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");

        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");

        if (uuid == null) {
            response.getWriter().write("Hello Resumes!");


        } else {
            response.getWriter().write("Hello " + uuid + '!');
        }
    }
}