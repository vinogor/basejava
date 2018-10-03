package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void doSaveForArray(Resume resume, int index) {
        int pointerToSave = (-(index + 1));
        System.arraycopy(storage, pointerToSave, storage, pointerToSave + 1, pointerToFirstNull - pointerToSave);
        storage[pointerToSave] = resume;
    }

    @Override
    public void doDeleteForArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, pointerToFirstNull - index - 1);
    }

    @Override
    protected Integer findResumeIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, pointerToFirstNull, searchKey);
    }
}
