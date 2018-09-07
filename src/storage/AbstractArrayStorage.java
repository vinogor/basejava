package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 3;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    // public Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int pointerToFirstNull = 0;

    public int size() {
        return pointerToFirstNull;
    }

    public Resume get(String uuid) {
        int index = findResumeNumber(uuid);
        if (index == -1) {
            System.out.println("ERROR: такого резюме НЕ существует");
            return null;
        } else {
            return storage[index];
        }
    }

    protected abstract int findResumeNumber(String uuid);
}