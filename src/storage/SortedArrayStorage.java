package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        int index = findResumeNumber(r.getUuid());
        if (pointerToFirstNull == STORAGE_LIMIT) {
            System.out.println("ERROR: хранилище резюме полностью заполнено");
        } else {
            if (index > -1) {
                System.out.println("ERROR: такое резюме УЖЕ существует");
            } else {
                index = (-(index + 1));
                System.arraycopy(storage, index, storage, index + 1, pointerToFirstNull - index);
                storage[index] = r;
            }
            pointerToFirstNull++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findResumeNumber(uuid);
        if (index < 0) {
            System.out.println("ERROR: такого резюме НЕ существует");
        } else {
            System.arraycopy(storage, index + 1, storage, index, pointerToFirstNull - index - 1);
            storage[pointerToFirstNull - 1] = null;
            pointerToFirstNull--;
        }
    }

    @Override
    protected int findResumeNumber(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, pointerToFirstNull, searchKey);
    }
}
