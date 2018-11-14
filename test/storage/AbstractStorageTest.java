package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static model.SectionType.EDUCATION;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    Storage storage;

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_NOT_EXISTING = "not_existing_uuid";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");

        RESUME_1.addContact(ContactType.PHONE, "+7(921) 855-0482");
        RESUME_1.addContact(ContactType.SKYPE, "grigory.kislin");
        RESUME_1.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        RESUME_1.addContact(ContactType.PROFILE_LINKEDIN, "https://www.linkedin.com/in/gkislin");
        RESUME_1.addContact(ContactType.PROFILE_GITHUB, "https://github.com/gkislin");
        RESUME_1.addContact(ContactType.PROFILE_STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        RESUME_1.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");

        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection(
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"
        ));
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection(
                "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."
        ));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListOfTextSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."
        ));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListOfTextSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle"
        ));
        RESUME_1.addSection(SectionType.EXPERIENCE, new ListOfOrganization(
                new Organization("Java Online Projects",
                        "http://javaops.ru/",
                        LocalDate.of(2013, 10, 1),
                        null,
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."
                ),
                new Organization("Wrike",
                        "https://www.wrike.com/",
                        LocalDate.of(2014, 10, 1),
                        LocalDate.of(2016, 1, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
        ));
        RESUME_1.addSection(EDUCATION, new ListOfOrganization(
                new Organization(
                        "Coursera",
                        "https://www.coursera.org/course/progfun",
                        LocalDate.of(2013, 3, 1),
                        LocalDate.of(2013, 4, 1),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky",
                        null
                ),
                new Organization(
                        "Luxoft",
                        "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_modelirovanie_na_uml_dlya_sistemnyh_analitikov_.html",
                        LocalDate.of(2011, 3, 1),
                        LocalDate.of(2011, 4, 1),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                        null
                ),
                new Organization(
                        new Link(
                                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                                "http://www.ifmo.ru/ru/"),
                        new Stage(
                                LocalDate.of(1987, 9, 1),
                                LocalDate.of(1993, 7, 1),
                                "Инженер (программист Fortran, C)",
                                null),
                        new Stage(
                                LocalDate.of(1987, 9, 1),
                                LocalDate.of(1993, 7, 1),
                                "Инженер (программист Fortran, C)",
                                null)
                )
        ));
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_NOT_EXISTING);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXISTING);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "New Name");
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume newResume = new Resume(UUID_NOT_EXISTING);
        storage.update(newResume);
    }
}