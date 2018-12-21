package ru.vinogor.storage;

import org.junit.Before;
import org.junit.Test;
import ru.vinogor.Config;
import ru.vinogor.exception.ExistStorageException;
import ru.vinogor.exception.NotExistStorageException;
import ru.vinogor.model.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.vinogor.ResumeTestData.fillOutResume;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    Storage storage;

    public AbstractStorageTest(Storage storage) {
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

        fillOutResume(RESUME_1);
        fillOutResume(RESUME_2);
        fillOutResume(RESUME_3);
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
        newResume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        newResume.addContact(ContactType.SKYPE, "grigory.kislin");
        newResume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");

        newResume.addSection(SectionType.OBJECTIVE, new TextSection(
                "objective new"
        ));
        newResume.addSection(SectionType.PERSONAL, new TextSection(
                "personal new"
        ));

        newResume.addSection(SectionType.ACHIEVEMENT, new ListOfTextSection(
                "new text1",
                "new text2",
                "new text3"
        ));
        newResume.addSection(SectionType.QUALIFICATIONS, new ListOfTextSection(
                "new text11",
                "new text22",
                "new text33"
        ));

        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(UUID_1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume newResume = new Resume(UUID_NOT_EXISTING);
        storage.update(newResume);
    }
}