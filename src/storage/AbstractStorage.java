package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
        Object index = getExistedIndex(uuid);
        return doGet(index);
    }

    public void delete(String uuid) {
        Object index = getExistedIndex(uuid);
        doDelete(index);
    }

    public void save(Resume resume) {
        Object index = getNotExistedIndex(resume.getUuid());
        doSave(resume, index);
    }

    public void update(Resume resume) {
        Object index = getExistedIndex(resume.getUuid());
        doUpdate(resume, index);
    }

    private Object getExistedIndex(String uuid) {
        Object index = findResumeIndex(uuid);
        if(!isResumeExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private Object getNotExistedIndex(String uuid) {
        Object index = findResumeIndex(uuid);
        if(isResumeExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    abstract Resume doGet(Object index);
    abstract void doDelete(Object index);
    abstract void doSave(Resume resume, Object index);
    abstract void doUpdate(Resume resume, Object index);

    abstract boolean isResumeExist(Object index);
    abstract Object findResumeIndex(String uuid);
}
