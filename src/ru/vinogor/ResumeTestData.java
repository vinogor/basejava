package ru.vinogor;

import ru.vinogor.model.*;

public class ResumeTestData {
    public static void fillOutResume(Resume resume) {

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");


//        resume.addContact(ContactType.PROFILE_LINKEDIN, "https://www.linkedin.com/in/gkislin");
//        resume.addContact(ContactType.PROFILE_GITHUB, "https://github.com/gkislin");
//        resume.addContact(ContactType.PROFILE_STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
//        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");


        resume.addSection(SectionType.OBJECTIVE, new TextSection(
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"
        ));
        resume.addSection(SectionType.PERSONAL, new TextSection(
                "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."
        ));

        resume.addSection(SectionType.ACHIEVEMENT, new ListOfTextSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."
        ));
        resume.addSection(SectionType.QUALIFICATIONS, new ListOfTextSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle"
        ));

//        /*
//
//        resume.addSection(SectionType.EXPERIENCE, new ListOfOrganization(
//                new Organization("Java Online Projects",
//                        "http://javaops.ru/",
//                        LocalDate.of(2013, 10, 1),
//                        LocalDate.of(3000, 1, 1),
//                        "Автор проекта.",
//                        "Создание, организация и проведение Java онлайн проектов и стажировок."
//                ),
//                new Organization("Wrike",
//                        "https://www.wrike.com/",
//                        LocalDate.of(2014, 10, 1),
//                        LocalDate.of(2016, 1, 1),
//                        "Старший разработчик (backend)",
//                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
//        ));
//        resume.addSection(EDUCATION, new ListOfOrganization(
//                new Organization(
//                        "Coursera",
//                        "https://www.coursera.org/course/progfun",
//                        LocalDate.of(2013, 3, 1),
//                        LocalDate.of(2013, 4, 1),
//                        "\"Functional Programming Principles in Scala\" by Martin Odersky",
//                        ""
//                ),
//                new Organization(
//                        "Luxoft",
//                        "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_modelirovanie_na_uml_dlya_sistemnyh_analitikov_.html",
//                        LocalDate.of(2011, 3, 1),
//                        LocalDate.of(2011, 4, 1),
//                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
//                        ""
//
//                ),
//                new Organization(
//                        new Link(
//                                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
//                                "http://www.ifmo.ru/ru/"),
//                        new Stage(
//                                LocalDate.of(1987, 9, 1),
//                                LocalDate.of(1993, 7, 1),
//                                "Инженер (программист Fortran, C)",
//                                ""),
//                        new Stage(
//                                LocalDate.of(1987, 9, 1),
//                                LocalDate.of(1993, 7, 1),
//                                "Инженер (программист Fortran, C)",
//                                "")
//                )
//        ));
//
//*/


    }
}
/*   ниже неудобный метод через другие конструкторы

// 1
       // Resume resume = new Resume("uuid_001", "Григорий Кислин");

        System.out.println("1 - Значение поля uuid и fullName:");
        System.out.println(resume);
        System.out.println();
// 2
        Map<ContactType, String> contacts = resume.getContacts();
        contacts.put(PHONE, "+7(921) 855-0482");
        contacts.put(SKYPE, "grigory.kislin");
        contacts.put(EMAIL, "gkislin@yandex.ru");
        contacts.put(PROFILE_LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(PROFILE_GITHUB, "https://github.com/gkislin");
        contacts.put(PROFILE_STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        contacts.put(HOMEPAGE, "http://gkislin.ru/");

        resume.setContact(contacts);
        System.out.println("2 - Содержимое блока КОНТАКТЫ:");
        System.out.println(resume.getContacts());
        System.out.println();
// 3
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(OBJECTIVE, objective);

        System.out.println("3 - Содержимое секции ПОЗИЦИЯ:");
        System.out.println(resume.getSection(OBJECTIVE));
        System.out.println();
// 4
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(PERSONAL, personal);

        System.out.println("4 - Содержимое секции ЛИЧНЫЕ КАЧЕСТВА:");
        System.out.println(resume.getSection(OBJECTIVE));
        System.out.println();
// 5
        List<String> list = new ArrayList<>();
        list.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        list.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        list.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");

        ListOfTextSection achievement = new ListOfTextSection(list);
        resume.setSection(ACHIEVEMENT, achievement);

        System.out.println("5 - Содержимое секции ДОСТИЖЕНИЯ:");
        System.out.println(resume.getSection(ACHIEVEMENT));
        System.out.println();

// 6
        List<String> list2 = new ArrayList<>();
        list2.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list2.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        list2.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        ListOfTextSection qualifications = new ListOfTextSection(list2);
        resume.setSection(QUALIFICATIONS, qualifications);

        System.out.println("6 - Содержимое секции КВАЛИФИКАЦИЯ:");
        System.out.println(resume.getSection(QUALIFICATIONS));
        System.out.println();

// 7
        List<Organization> list3 = new ArrayList<>();
        list3.add(new Organization(
                "Java Online Projects",
                "http://javaops.ru/",
                LocalDate.of(2013, 10, 1),
                null,
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        list3.add(new Organization(
                "Wrike",
                "https://www.wrike.com/",
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));

        ListOfOrganization experience = new ListOfOrganization(list3);
        resume.setSection(EXPERIENCE, experience);

        System.out.println("7 - Содержимое секции ОПЫТ РАБОТЫ:");
        System.out.println(resume.getSection(EXPERIENCE));
        System.out.println();

// 8
        List<Organization> list4 = new ArrayList<>();
        list4.add(new Organization(
                "Coursera",
                "https://www.coursera.org/course/progfun",
                LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 4, 1),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                null));
        list4.add(new Organization(
                "Luxoft",
                "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_modelirovanie_na_uml_dlya_sistemnyh_analitikov_.html",
                LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                null));

        list4.add(new Organization(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/ru/",
                LocalDate.of(1987, 9, 1),
                LocalDate.of(1993, 7, 1),
                "Инженер (программист Fortran, C)",
                null));
        list4.get(2).getStages().add(new Stage(
                LocalDate.of(1993, 9, 1),
                LocalDate.of(1996, 7, 1),
                "Аспирантура (программист С, С++)",
                null));


        ListOfOrganization education = new ListOfOrganization(list4);
        resume.setSection(EDUCATION, education);

        System.out.println("8 - Содержимое секции ОБРАЗОВАНИЕ:");
        System.out.println(resume.getSection(EDUCATION));
        System.out.println();
   */


