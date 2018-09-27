package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
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
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage.set(index, resume);
        }
    }

    @Override
    public void save(Resume resume) {
        int index = findResumeIndex(resume.getUuid());
        if (index > -1) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            storage.add(resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage.get(index);
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            storage.remove(index);
        }
    }

    @Override
    public int size() {
        return storage.size();
    }

    private int findResumeIndex(String uuid) {
        int index = 0;
        for (Resume r : storage) {
            if (Objects.equals(r.getUuid(), uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
