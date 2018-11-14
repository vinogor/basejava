package ru.vinogor.storage;

import ru.vinogor.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    Resume doGet(Resume index) {
        return index;
    }

    @Override
    void doDelete(Resume index) {
        storage.remove((index).getUuid());
    }

    @Override
    public void doSave(Resume resume, Resume index) {
        storage.put(resume.getUuid(), resume);
    }

    public void doUpdate(Resume resume, Resume index) {
        storage.put(resume.getUuid(), resume);
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
    boolean isResumeExist(Resume index) {
        return index != null;
    }

    @Override
    Resume searchKey(String uuid) {
        return storage.get(uuid);
    }
}