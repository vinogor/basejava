package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        final int index = findResumeIndex(resume.getUuid());
        if (index < 0) {
            storageNotExist(resume.getUuid());
        }
        storage.set(index, resume);
    }

    @Override
    public void save(Resume resume) {
        int index = findResumeIndex(resume.getUuid());
        if (index > -1) {
            storageExist(resume.getUuid());
        }
        storage.add(resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            storageNotExist(uuid);
        }
        return storage.get(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            storageNotExist(uuid);
        }
        storage.remove(index);
    }

    @Override
    public int size() {
        return storage.size();
    }

    private int findResumeIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            Resume r = storage.get(i);
            if (Objects.equals(r.getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }
}
