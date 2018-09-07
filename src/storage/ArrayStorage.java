package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, pointerToFirstNull, null);
        pointerToFirstNull = 0;
    }

    public void update(Resume r) {
        int index = findResumeNumber(r.getUuid());
        if (index == -1) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (pointerToFirstNull == STORAGE_LIMIT) {
            System.out.println("ERROR: хранилище резюме полностью заполнено");
        } else {
            if (findResumeNumber(r.getUuid()) != -1) {
                System.out.println("ERROR: такое резюме УЖЕ существует");
            } else {
                storage[pointerToFirstNull] = r;
                pointerToFirstNull++;
            }
        }
    }



    public void delete(String uuid) {
        int index = findResumeNumber(uuid);
        if (index == -1) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            storage[index] = storage[pointerToFirstNull-1];
            storage[pointerToFirstNull - 1] = null;
            pointerToFirstNull--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, pointerToFirstNull);
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