package ru.vinogor.storage;

import ru.vinogor.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StrategySerialization {
    Resume doRead(InputStream bufferedInputStream) throws IOException;
    void doWrite(Resume resume, OutputStream newOutputStream) throws IOException;
}