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
        int rn = findResumeNumber(r.uuid);
        if (rn == -1) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            storage[rn] = r;
        }
    }

    void save(Resume r) {
        if (findResumeNumber(r.uuid) != -1) {
            System.out.println("ERROR: такое резюме УЖЕ существует");
        } else {
            storage[pointerToFirstNull] = r;
            pointerToFirstNull++;
        }
    }

    Resume get(String uuid) {
        int rn = findResumeNumber(uuid);
        if (rn == -1) {
            return null;
        }
        else {
            return storage[rn];
        }
    }

    void delete(String uuid) {
        int rn = findResumeNumber(uuid);
        if (rn == -1) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            System.arraycopy(storage, rn + 1, storage, rn, pointerToFirstNull - rn);
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