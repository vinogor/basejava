package ru.vinogor.storage;

import ru.vinogor.exception.StorageException;
import ru.vinogor.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (Exception e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    void doDelete(File file) {
        // удалить файл
        try {
            file.delete();
        } catch (Exception e) {
            throw new StorageException("File delete error", file.getName(), e);
        }
    }

    @Override
    void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (Exception e) {
            throw new StorageException("File write error", file.getName() + resume.getUuid(), e);
        }
    }

    @Override
    List<Resume> doCopyAll() {
        File[] listOfFiles = directory.listFiles();
        if (listOfFiles == null) {
            throw new StorageException("Error - directory empty");
        }
        List<Resume> storage = new ArrayList<>();
        for (File file: listOfFiles) {
            storage.add(doRead(file));
        }
        return storage;
    }

    @Override
    boolean isResumeExist(File file) {
        return file.exists();
    }

    @Override
    File searchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void clear() {
        File[] listOfFiles = directory.listFiles();
        if (listOfFiles == null) {
            throw new StorageException("Error - directory already empty");
        }
        for (File file: listOfFiles) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        // передать кол-во файлов в папке
        File[] listOfFiles = directory.listFiles();
        return listOfFiles.length;  // а что такого если вернёт ноль ?
    }

    abstract void doWrite(Resume resume, File file);
    abstract Resume doRead(File file);
}
