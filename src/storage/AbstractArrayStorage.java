package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    static final int STORAGE_LIMIT = 4;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int pointerToFirstNull = 0;

    Resume doGet(int index, String uuid) {
        return storage[index];
    }

    public void doDelete(int index, String uuid) {
        doDeleteForArray(index);
        storage[pointerToFirstNull - 1] = null;
        pointerToFirstNull--;
    }

    void doSave(Resume resume, int index, String uuid) {
        if (pointerToFirstNull == STORAGE_LIMIT) {
            throw new StorageException("Storage Overflow", uuid);
        }
        doSaveForArray(resume, index);
        pointerToFirstNull++;
    }

    public void doUpdate(Resume resume, int index, String uuid) {
        storage[index] = resume;
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

    protected abstract void doSaveForArray(Resume resume, int index);
    protected abstract void doDeleteForArray(int index);
    protected abstract int findResumeIndex(String uuid);
}