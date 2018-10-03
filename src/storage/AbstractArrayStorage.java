package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    static final int STORAGE_LIMIT = 4;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int pointerToFirstNull = 0;

    Resume doGet(Object index) {
        return storage[(Integer)index];
    }

    @Override
    public void doDelete(Object index) {
        doDeleteForArray((Integer)index);
        storage[pointerToFirstNull - 1] = null;
        pointerToFirstNull--;
    }

    @Override
    void doSave(Resume resume, Object index) {
        if (pointerToFirstNull == STORAGE_LIMIT) {
            throw new StorageException("Storage Overflow", resume.getUuid());
        }
        doSaveForArray(resume, (Integer)index);
        pointerToFirstNull++;
    }

    @Override
    public void doUpdate(Resume resume, Object index) {
        storage[(Integer)index] = resume;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, pointerToFirstNull);
    }

    public int size() {
        return pointerToFirstNull;
    }

    public void clear() {
        Arrays.fill(storage, 0, pointerToFirstNull, null);
        pointerToFirstNull = 0;
    }

    @Override
    boolean isResumeExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void doSaveForArray(Resume resume, int index);
    protected abstract void doDeleteForArray(int index);
    protected abstract Integer findResumeIndex(String uuid);
}