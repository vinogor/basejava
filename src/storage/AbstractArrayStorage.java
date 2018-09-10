package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int pointerToFirstNull = 0;

    public int size() {
        return pointerToFirstNull;
    }

    public Resume get(String uuid) {
        int index = findResumeNumber(uuid);
        if (index < 0) {
            System.out.println("ERROR: такого резюме НЕ существует");
            return null;
        } else {
            return storage[index];
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, pointerToFirstNull, null);
        pointerToFirstNull = 0;
    }

    public void update(Resume r) {
        int index = findResumeNumber(r.getUuid());
        if (index < 0) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            storage[index] = r;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, pointerToFirstNull);
    }

    protected abstract int findResumeNumber(String uuid);
    public abstract void save(Resume r);
    public abstract void delete(String uuid);
}