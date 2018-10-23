package storage;


import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.fail;
import static storage.AbstractArrayStorage.STORAGE_LIMIT;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i < STORAGE_LIMIT + 1; i++) {
                storage.save(new Resume("Name" + i));
            }
        } catch (StorageException e) {
            fail();
        }
        storage.save(new Resume("Overflow"));
    }
}