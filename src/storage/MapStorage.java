package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        String key = resume.getUuid();
        if (storage.get(key) == null) {
            throw new NotExistStorageException(key);
        } else {
            storage.put(key, resume);
        }
    }

    @Override
    public void save(Resume resume) {
        String key = resume.getUuid();
        if (storage.get(key) != null) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            storage.put(key, resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (storage.get(uuid) == null) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage.get(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);  // не очень понял что тут происходит
    }

    @Override
    public void delete(String uuid) {
        if (storage.get(uuid) == null) {
            throw new NotExistStorageException(uuid);
        } else {
            storage.remove(uuid);
        }
    }

    @Override
    public int size() {
        return storage.size();
    }
}