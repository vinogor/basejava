package ru.vinogor.storage;

import ru.vinogor.Config;

public class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPass()));
    }
}