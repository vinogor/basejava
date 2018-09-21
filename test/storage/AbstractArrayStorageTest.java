package storage;

import exception.NotExistStorageException;
import exception.ExistStorageException;
import exception.StorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.Resume;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);
    private static final Resume resume5 = new Resume(UUID_5);


    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() {
        storage.save(resume1);
    }

    @Test
    public void saveOverflow() {
        storage.save(resume4);
        try {
            storage.save(resume5);
        } catch (StorageException e) {
        }
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("not_existing_uuid");
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(resume1, storage.get("uuid1"));
        assertEquals(resume2, storage.get("uuid2"));
        assertEquals(resume3, storage.get("uuid3"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("not_existing_uuid");
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        Resume[] arrayExpected = {resume1, resume2, resume3};
        assertArrayEquals(array, arrayExpected);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume newResume = new Resume("not_existing_uuid");
        storage.update(newResume);
    }
}