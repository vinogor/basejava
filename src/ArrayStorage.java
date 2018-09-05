import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int pointerToFirstNull = 0;

    void clear() {
        Arrays.fill(storage, 0, pointerToFirstNull, null);
        pointerToFirstNull = 0;
    }

    void update(Resume r) {
        if (findResumeNumber(r.uuid) == 10000) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            storage[findResumeNumber(r.uuid)] = r;
        }
    }

    void save(Resume r) {
        if (findResumeNumber(r.uuid) != 10000) {
            System.out.println("ERROR: такое резюме УЖЕ существует");
        } else {
            storage[pointerToFirstNull] = r;
            pointerToFirstNull++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < pointerToFirstNull; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (findResumeNumber(uuid) == 10000) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            System.arraycopy(storage, findResumeNumber(uuid) + 1, storage, findResumeNumber(uuid), pointerToFirstNull - findResumeNumber(uuid) + 1);
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
        return 10000;
    }
}