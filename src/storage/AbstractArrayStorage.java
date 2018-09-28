package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    static final int STORAGE_LIMIT = 4;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int pointerToFirstNull = 0;

    public void save(Resume resume) {
        final int index = findResumeIndex(resume.getUuid());
        if (pointerToFirstNull == STORAGE_LIMIT) {
            throw new StorageException("Storage Overflow", resume.getUuid());
        } else {
            if (index > -1) {
                storageExist(resume.getUuid());
            }
            doSave(resume, index);
            pointerToFirstNull++;
        }
    }

    public void delete(String uuid) {
        final int index = findResumeIndex(uuid);
        if (index < 0) {
            storageNotExist(uuid);
        }
        doDelete(index);
        storage[pointerToFirstNull - 1] = null;
        pointerToFirstNull--;
    }


    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            storageNotExist(uuid);
        }
        return storage[index];
    }

    public int size() {
        return pointerToFirstNull;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, pointerToFirstNull);
    }

    public void clear() {
        Arrays.fill(storage, 0, pointerToFirstNull, null);
        pointerToFirstNull = 0;
    }

    public void update(Resume resume) {
        final int index = findResumeIndex(resume.getUuid());
        if (index < 0) {
            storageNotExist(resume.getUuid());
        }
        storage[index] = resume;
    }

    protected abstract int findResumeIndex(String uuid);

    protected abstract void doSave(Resume resume, int index);

    protected abstract void doDelete(int index);
}