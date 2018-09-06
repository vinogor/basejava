import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int pointerToFirstNull = 0;

    void clear() {
        Arrays.fill(storage, 0, pointerToFirstNull, null);
        pointerToFirstNull = 0;
    }

    void update(Resume r) {
        int index = findResumeNumber(r.uuid);
        if (index == -1) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            storage[index] = r;
        }
    }

    void save(Resume r) {
        if (pointerToFirstNull == STORAGE_LIMIT - 1) {
            System.out.println("ERROR: хранилище резюме полностью заполнено");
        } else {
            if (findResumeNumber(r.uuid) != -1) {
                System.out.println("ERROR: такое резюме УЖЕ существует");
            } else {
                storage[pointerToFirstNull] = r;
                pointerToFirstNull++;
            }
        }
    }

    Resume get(String uuid) {
        int index = findResumeNumber(uuid);
        if (index == -1) {
            System.out.println("ERROR: такого резюме НЕ существует");
            return null;
        } else {
            return storage[index];
        }
    }

    void delete(String uuid) {
        int index = findResumeNumber(uuid);
        if (index == -1) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            System.arraycopy(storage, index + 1, storage, index, pointerToFirstNull - index);
            storage[pointerToFirstNull - 1] = null;
            pointerToFirstNull--;
        }
    }

    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, pointerToFirstNull);
    }

    int size() {
        return pointerToFirstNull;
    }

    private int findResumeNumber(String uuid) {
        for (int i = 0; i < pointerToFirstNull; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}