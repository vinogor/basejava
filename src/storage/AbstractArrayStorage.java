package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    static final int STORAGE_LIMIT = 4;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int pointerToFirstNull = 0;

    Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    public void doDelete(Integer index) {
        doDeleteForArray(index);
        storage[pointerToFirstNull - 1] = null;
        pointerToFirstNull--;
    }

    @Override
    void doSave(Resume resume, Integer index) {
        if (pointerToFirstNull == STORAGE_LIMIT) {
            throw new StorageException("Storage Overflow", resume.getUuid());
        }
        doSaveForArray(resume, index);
        pointerToFirstNull++;
    }

    @Override
    public void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, pointerToFirstNull));
    }

    public int size() {
        return pointerToFirstNull;
    }

    public void clear() {
        Arrays.fill(storage, 0, pointerToFirstNull, null);
        pointerToFirstNull = 0;
    }

    @Override
    boolean isResumeExist(Integer index) {
        return index >= 0;
    }

    protected abstract void doSaveForArray(Resume resume, int index);
    protected abstract void doDeleteForArray(int index);
    protected abstract Integer searchKey(String uuid);
}