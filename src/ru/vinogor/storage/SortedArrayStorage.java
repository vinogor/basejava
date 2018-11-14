package ru.vinogor.storage;

import ru.vinogor.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

/*
    private static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
*/

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

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
    protected Integer searchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "blabla");
        return Arrays.binarySearch(storage, 0, pointerToFirstNull, searchKey, RESUME_COMPARATOR);
    }


}
