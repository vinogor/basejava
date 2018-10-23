package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.List;

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
        Object index = searchKey(uuid);
        if(!isResumeExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private Object getNotExistedIndex(String uuid) {
        Object index = searchKey(uuid);
        if(isResumeExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }

    abstract Resume doGet(Object index);
    abstract void doDelete(Object index);
    abstract void doSave(Resume resume, Object index);
    abstract void doUpdate(Resume resume, Object index);
    abstract List<Resume> doCopyAll();

    abstract boolean isResumeExist(Object index);
    abstract Object searchKey(String uuid);
}
