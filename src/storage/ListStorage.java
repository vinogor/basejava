package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    Resume doGet(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    void doDelete(int index, String uuid) {
        storage.remove(index);
    }

    @Override
    public void doSave(Resume resume, int index, String uuid) {
        storage.add(resume);
    }

    public void doUpdate(Resume resume, int index, String uuid) {
        storage.set(index, resume);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    int findResumeIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume r = storage.get(i);
            if (Objects.equals(r.getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }
}
