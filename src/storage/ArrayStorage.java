package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public void doSaveForArray(Resume resume, int index) {
        storage[pointerToFirstNull] = resume;
    }

    public void doDeleteForArray(int index) {
        storage[index] = storage[pointerToFirstNull - 1];
    }

    protected int findResumeIndex(String uuid) {
        for (int i = 0; i < pointerToFirstNull; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}