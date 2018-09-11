package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void reallySave(Resume r, int index) {
        index = (-(index + 1));
        System.arraycopy(storage, index, storage, index + 1, pointerToFirstNull - index);
        storage[index] = r;
    }

    @Override
    public void reallyDelete(String uuid, int index) {
        System.arraycopy(storage, index + 1, storage, index, pointerToFirstNull - index - 1);
    }

    @Override
    protected int findResumeNumber(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, pointerToFirstNull, searchKey);
    }
}
