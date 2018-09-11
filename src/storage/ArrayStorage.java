package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public void reallySave(Resume r, int index) {
        storage[pointerToFirstNull] = r;
    }

    public void reallyDelete(String uuid, int index) {
        storage[index] = storage[pointerToFirstNull - 1];
    }

    protected int findResumeNumber(String uuid) {
        for (int i = 0; i < pointerToFirstNull; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}