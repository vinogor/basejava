package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {

// 1
        Resume resume = new Resume("uuid_001", "Григорий Кислин");

        System.out.println("Значение поля uuid и fullName:");
        System.out.println(resume);
        System.out.println();
// 2
        Map<String, String> contacts = new HashMap<>();
        contacts.put("Тел.: ", "+7(921) 855-0482");
        contacts.put("Skype: ", "grigory.kislin");
        contacts.put("Почта: ", "gkislin@yandex.ru");
        contacts.put("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin");
        contacts.put("Профиль GitHub", "https://github.com/gkislin");
        contacts.put("Профиль Stackoverflow", "https://stackoverflow.com/users/548473/gkislin");
        contacts.put("Домашняя страница", "http://gkislin.ru/");

        resume.setContacts(contacts);
        System.out.println("Содержимое блока КОНТАКТЫ:");
        System.out.println(resume.getContacts());
        System.out.println();
// 3
        SectionText objective = new SectionText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(OBJECTIVE, objective);

        System.out.println("Содержимое секции ПОЗИЦИЯ:");
        System.out.println(resume.getSection(OBJECTIVE));
        System.out.println();
// 4
        SectionText personal = new SectionText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(PERSONAL, personal);

        System.out.println("Содержимое секции ЛИЧНЫЕ КАЧЕСТВА:");
        System.out.println(resume.getSection(OBJECTIVE));
        System.out.println();
// 5
        List<String> list = new ArrayList<>();
        list.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        list.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        list.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        SectionListOfText achievement = new SectionListOfText(list);
        resume.setSection(ACHIEVEMENT, achievement);

        System.out.println("Содержимое секции ДОСТИЖЕНИЯ:");
        System.out.println(resume.getSection(ACHIEVEMENT));
        System.out.println();

// 6
        List<String> list2 = new ArrayList<>();
        list2.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list2.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        list2.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        SectionListOfText qualifications = new SectionListOfText(list2);
        resume.setSection(QUALIFICATIONS, qualifications);

        System.out.println("Содержимое секции КВАЛИФИКАЦИЯ:");
        System.out.println(resume.getSection(QUALIFICATIONS));
        System.out.println();

// 7
        List<DiffItems> list3 = new ArrayList<>();

        String name = "Java Online Projects";
        String link = "http://javaops.ru/";
        String period = "10/2013 - Сейчас";
        String headline = "Автор проекта.";
        String content = "Создание, организация и проведение Java онлайн проектов и стажировок.";
        list3.add(new DiffItems(name, link, period, headline, content));

        name = "Wrike";
        link = "https://www.wrike.com/";
        period = "10/2014 - 01/2016";
        headline = "Старший разработчик (backend)";
        content = "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.";
        list3.add(new DiffItems(name, link, period, headline, content));

        SectionListOfDiffItems experience = new SectionListOfDiffItems(list3);
        resume.setSection(EXPERIENCE, experience);

        System.out.println("Содержимое секции ОПЫТ РАБОТЫ:");
        System.out.println(resume.getSection(EXPERIENCE));
        System.out.println();

// 8
        List<DiffItems> list4 = new ArrayList<>();

        name = "Coursera";
        link = "https://www.coursera.org/course/progfun";
        period = "03/2013 - 05/2013";
        headline = "\"Functional Programming Principles in Scala\" by Martin Odersky";
        list4.add(new DiffItems(name, link, period, headline, null));

        name = "Luxoft";
        link = "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_modelirovanie_na_uml_dlya_sistemnyh_analitikov_.html";
        period = "03/2011 - 04/2011";
        headline = "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"";
        list4.add(new DiffItems(name, link, period, headline, null));

        SectionListOfDiffItems education = new SectionListOfDiffItems(list4);
        resume.setSection(EDUCATION, education);

        System.out.println("Содержимое секции ОБРАЗОВАНИЕ:");
        System.out.println(resume.getSection(EDUCATION));
        System.out.println();
    }
}
