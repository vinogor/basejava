package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

abstract class AbstractStorage<SK> implements Storage {

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK index = getExistedIndex(uuid);
        return doGet(index);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK index = getExistedIndex(uuid);
        doDelete(index);
    }

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK index = getNotExistedIndex(resume.getUuid());
        doSave(resume, index);
    }

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK index = getExistedIndex(resume.getUuid());
        doUpdate(resume, index);
    }

    private SK getExistedIndex(String uuid) {
        SK index = searchKey(uuid);
        if(!isResumeExist(index)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private SK getNotExistedIndex(String uuid) {
        SK index = searchKey(uuid);
        if(isResumeExist(index)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }

//    protected final Logger log = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    abstract Resume doGet(SK index);
    abstract void doDelete(SK index);
    abstract void doSave(Resume resume, SK index);
    abstract void doUpdate(Resume resume, SK index);
    abstract List<Resume> doCopyAll();

    abstract boolean isResumeExist(SK index);
    abstract SK searchKey(String uuid);
}
