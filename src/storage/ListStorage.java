package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    Resume doGet(Object index) {
        return storage.get((Integer)index);
    }

    @Override
    void doDelete(Object index) {
        storage.remove(((Integer)index).intValue());
    }

    @Override
    public void doSave(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    public void doUpdate(Resume resume, Object index) {
        storage.set((Integer) index, resume);
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
    boolean isResumeExist(Object index) {
        return (Integer)index >= 0;
    }

    @Override
    Integer searchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume r = storage.get(i);
            if (Objects.equals(r.getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }
}
