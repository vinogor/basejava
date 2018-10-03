package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    Resume doGet(Object index) {
        return storage.get(index.toString());
    }

    @Override
    void doDelete(Object index) {
        storage.remove(index.toString());
    }

    @Override
    public void doSave(Resume resume, Object index) {
        storage.put((String)index, resume);
    }

    public void doUpdate(Resume resume, Object index) {
        storage.put((String)index, resume);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
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
    boolean isResumeExist(Object index) {
        return storage.containsKey(index.toString());
    }

    @Override
    String findResumeIndex(String uuid) {
        return uuid;
    }
}