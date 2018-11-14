package ru.vinogor.storage;

import ru.vinogor.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    Resume doGet(Integer index) {
        return storage.get(index);
    }

    @Override
    void doDelete(Integer index) {
        storage.remove((index).intValue());
    }

    @Override
    public void doSave(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    public void doUpdate(Resume resume, Integer index) {
        storage.set(index, resume);
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    boolean isResumeExist(Integer index) {
        return index >= 0;
    }

    @Override
    Integer searchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
