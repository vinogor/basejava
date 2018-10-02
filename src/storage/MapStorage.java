package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    Resume doGet(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    void doDelete(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    public void doSave(Resume resume, int index, String uuid) {
        storage.put(uuid, resume);
    }

    public void doUpdate(Resume resume, int index, String uuid) {
        storage.put(uuid, resume);
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
    int findResumeIndex(String uuid) {
        if (storage.get(uuid) == null) {
            return -1;
        } else
        return 0;
    }
}