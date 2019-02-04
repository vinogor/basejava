package ru.vinogor.web;

import ru.vinogor.Config;
import ru.vinogor.model.*;
import ru.vinogor.storage.Storage;
import ru.vinogor.util.DateUtil;
import ru.vinogor.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume r;  // = storage.get(uuid);

        if (isCreate) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

//        r.setFullName(fullName);

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                r.getContacts().remove(type);
            } else {
                r.setContact(type, value);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            System.out.println(value);
            System.out.println(values);
//            if ((value == null || value.trim().length() == 0) && values.length < 2) {
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.setSection(type, new ListOfTextSection(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> listOfDiffItems = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!(name == null || name.trim().length() == 0)) {
                                List<Stage> stages = new ArrayList<>();
                                String pfx = type.name() + i;

                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] headlines = request.getParameterValues(pfx + "title");
                                String[] contents = request.getParameterValues(pfx + "description");

                                for (int j = 0; j < headlines.length; j++) {
                                    if (!(headlines[j] == null || headlines[j].trim().length() == 0)) {
                                        stages.add(
                                                new Stage(
                                                        DateUtil.parse(startDates[j]),
                                                        DateUtil.parse(endDates[j]),
                                                        headlines[j],
                                                        contents[j])
                                        );

                                    }
                                }
                                listOfDiffItems.add(new Organization(new Link(name, urls[i]), stages));
                            }
                        }
                        r.setSection(type, new ListOfOrganization(listOfDiffItems));
                        break;
                }
            }
        }

        if (isCreate) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

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
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);

                for (SectionType type : new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}) {
                    ListOfOrganization section = (ListOfOrganization) r.getSection(type);
                    List<Organization> emptyFirstOrganizations = new ArrayList<>();

                    emptyFirstOrganizations.add(new Organization(new Link(), new Stage()));

                    if (section != null) {
                        for (Organization organization : section.getListOfDiffItems()) {
                            List<Stage> emptyFirstPositions = new ArrayList<>();
                            emptyFirstPositions.add(new Stage());
                            emptyFirstPositions.addAll(organization.getStages());
                            emptyFirstOrganizations.add(new Organization(organization.getHomePage(), emptyFirstPositions));
                        }
                    }
                    r.setSection(type, new ListOfOrganization(emptyFirstOrganizations));
                }

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