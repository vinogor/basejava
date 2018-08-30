import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int pointerToFirstEmptyCell = 0;

    void clear() {
        Arrays.fill(storage, 0, pointerToFirstEmptyCell, null);
        pointerToFirstEmptyCell = 0;
    }

    void save(Resume r) {
        storage[pointerToFirstEmptyCell] = r;
        pointerToFirstEmptyCell++;
    }

    Resume get(String uuid) {
        Resume forReturn = null;
        for (int i = 0; i < pointerToFirstEmptyCell; i++) {
            if (storage[i].uuid.equals(uuid)) {
                forReturn = storage[i];
                break;
            }
        }
        return forReturn;
    }

    void delete(String uuid) {
        for (int i = 0; i < pointerToFirstEmptyCell; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, pointerToFirstEmptyCell - i + 1);
                pointerToFirstEmptyCell--;
                break;
            }
        }
    }

    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, pointerToFirstEmptyCell);
    }

    int size() {
        return pointerToFirstEmptyCell;
    }
}