package ru.vinogor.storage;

import ru.vinogor.exception.StorageException;
import ru.vinogor.model.Resume;

import java.io.*;

public class ObjectSerialization implements StrategySerialization {

    @Override
    public Resume doRead(InputStream bufferedInputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(bufferedInputStream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream newOutputStream) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(newOutputStream)) {
            oos.writeObject(resume);
        }
    }
}
