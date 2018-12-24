package ru.vinogor;

import ru.vinogor.storage.SqlStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("d:\\progerstvo\\TopJava\\basejava\\config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private File storageDir;

    private SqlStorage storage;
    private String dbUrl;
    private String dbUser;
    private String dbPass;

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPass = props.getProperty("db.password");
            storage = new SqlStorage(dbUrl, dbUser,dbPass);

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public SqlStorage getStorage() {
        return storage;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }
}