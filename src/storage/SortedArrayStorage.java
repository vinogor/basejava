package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void doSave(Resume r, int index) {
        int pointerToSave = (-(index + 1));
        System.arraycopy(storage, pointerToSave, storage, pointerToSave + 1, pointerToFirstNull - pointerToSave);
        storage[pointerToSave] = r;
    }

    @Override
    public void doDelete(int index) {
        System.arraycopy(storage, index + 1, storage, index, pointerToFirstNull - index - 1);
    }

    @Override
    protected int findResumeNumber(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, pointerToFirstNull, searchKey);
    }
}
