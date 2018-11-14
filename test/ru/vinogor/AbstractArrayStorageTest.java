package ru.vinogor;

import ru.vinogor.exception.StorageException;
import ru.vinogor.model.Resume;
import org.junit.Test;
import ru.vinogor.storage.Storage;
import ru.vinogor.storage.AbstractArrayStorage;


import static org.junit.Assert.fail;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i < AbstractArrayStorage.STORAGE_LIMIT + 1; i++) {
                storage.save(new Resume("Name" + i));
            }
        } catch (StorageException e) {
            fail();
        }
        storage.save(new Resume("Overflow"));
    }
}