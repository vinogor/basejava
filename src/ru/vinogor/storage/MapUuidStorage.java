package ru.vinogor.storage;

import ru.vinogor.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    Resume doGet(String index) {
        return storage.get(index);
    }

    @Override
    void doDelete(String index) {
        storage.remove(index);
    }

    @Override
    public void doSave(Resume resume, String index) {
        storage.put(index, resume);
    }

    public void doUpdate(Resume resume, String index) {
        storage.put(index, resume);
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
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
    boolean isResumeExist(String index) {
        return storage.containsKey(index);
    }

    @Override
    String searchKey(String uuid) {
        return uuid;
    }
}