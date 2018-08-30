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

    void save(Resume r) {
        storage[pointerToFirstNull] = r;
        pointerToFirstNull++;
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
        for (int i = 0; i < pointerToFirstNull; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, pointerToFirstNull - i + 1);
                pointerToFirstNull--;
                break;
            }
        }
    }

    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, pointerToFirstNull);
    }

    int size() {
        return pointerToFirstNull;
    }
}