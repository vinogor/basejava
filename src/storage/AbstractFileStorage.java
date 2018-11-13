package storage;

import exception.StorageException;
import model.Resume;

import java.io.File;
import java.io.IOException;
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
        return null;
        // юзать doRead - абстрактн метод, пока не реализовывать
    }

    @Override
    void doDelete(File file) {
        // удалить файл?
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

    protected abstract void doWrite(Resume resume, File file);

    @Override
    void doUpdate(Resume resume, File file) {
        // ???
    }

    @Override
    List<Resume> doCopyAll() {
        // ???
        return null;
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
        // удалить все файлы в папке?
    }

    @Override
    public int size() {
        // передать кол-во файлов в папке?
        return 0;
    }
}
