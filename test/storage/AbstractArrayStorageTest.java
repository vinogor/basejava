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
import static org.junit.Assert.fail;
import static storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    AbstractArrayStorageTest(Storage storage) {
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
        RESUME_1 = new Resume(UUID_1);
        RESUME_2 = new Resume(UUID_2);
        RESUME_3 = new Resume(UUID_3);
        RESUME_4 = new Resume(UUID_4);
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

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i < STORAGE_LIMIT + 1; i++) {
                storage.save(new Resume(Integer.toString(i)));
            }
        } catch (StorageException e) {
            fail();
        }
        storage.save(new Resume(Integer.toString(STORAGE_LIMIT + 1)));
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
    public void getAll() {
        Resume[] arrayActual = storage.getAll();
        Resume[] arrayExpected = {RESUME_1, RESUME_2, RESUME_3};
        assertArrayEquals(arrayExpected, arrayActual);
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
        Assert.assertSame(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume newResume = new Resume(UUID_NOT_EXISTING);
        storage.update(newResume);
    }
}