package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    Resume doGet(Object index) {
        return (Resume) index;
    }

    @Override
    void doDelete(Object index) {
        storage.remove(((Resume) index).getUuid());
    }

    @Override
    public void doSave(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    public void doUpdate(Resume resume, Object index) {
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
    boolean isResumeExist(Object index) {
        return index != null;
    }

    @Override
    Resume searchKey(String uuid) {
        return storage.get(uuid);
    }
}