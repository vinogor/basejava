package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;

abstract class AbstractStorage implements Storage {

    void storageNotExist(String uuid) {
        throw new NotExistStorageException(uuid);
    }

    void storageExist(String uuid) {
        throw new ExistStorageException(uuid);
    }
}
