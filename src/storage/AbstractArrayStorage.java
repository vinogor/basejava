package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int pointerToFirstNull = 0;

    public void save(Resume r) {
        final int index = findResumeNumber(r.getUuid());
        if (pointerToFirstNull == STORAGE_LIMIT) {
            System.out.println("ERROR: хранилище резюме полностью заполнено");
        } else {
            if (index > -1) {
                System.out.println("ERROR: такое резюме УЖЕ существует");
            } else {
                doSave(r, index);
                pointerToFirstNull++;
            }
        }
    }

    public void delete(String uuid) {
        final int index = findResumeNumber(uuid);
        if (index < 0) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            doDelete(index);
            storage[pointerToFirstNull - 1] = null;
            pointerToFirstNull--;
        }
    }

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

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, pointerToFirstNull);
    }

    public void clear() {
        Arrays.fill(storage, 0, pointerToFirstNull, null);
        pointerToFirstNull = 0;
    }

    public void update(Resume r) {
        final int index = findResumeNumber(r.getUuid());
        if (index < 0) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            storage[index] = r;
        }
    }

    protected abstract int findResumeNumber(String uuid);
    protected abstract void doSave(Resume r, int index);
    protected abstract void doDelete(int index);
}