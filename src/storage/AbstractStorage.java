package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(index, uuid);
    }

    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(index, uuid);
    }

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = findResumeIndex(uuid);
        if (index > -1) {
            throw new ExistStorageException(uuid);
        }
        doSave(resume, index, uuid);
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findResumeIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        doUpdate(resume, index, uuid);
    }

    abstract Resume doGet(int index, String uuid);
    abstract void doDelete(int index, String uuid);
    abstract void doSave(Resume resume, int index, String uuid);
    abstract void doUpdate(Resume resume, int index, String uuid);

    abstract int findResumeIndex(String uuid);
}
