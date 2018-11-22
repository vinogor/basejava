package ru.vinogor.storage;

import ru.vinogor.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}