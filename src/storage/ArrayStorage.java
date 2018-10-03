package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void doSaveForArray(Resume resume, int index) {
        storage[pointerToFirstNull] = resume;
    }

    @Override
    public void doDeleteForArray(int index) {
        storage[index] = storage[pointerToFirstNull - 1];
    }

    @Override
    protected Integer findResumeIndex(String uuid) {
        for (int i = 0; i < pointerToFirstNull; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}