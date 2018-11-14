package ru.vinogor;

import java.io.*;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();

        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

// =========================================

        System.out.println();

        class NameFilter implements FileFilter {
            private String mask;

            private NameFilter(String mask) {
                this.mask = mask;
            }

            public boolean accept(File file) {
                return file.getName().contains(mask);
            }
        }

        File pathFile = new File(".");
        String filterString = ".";

        try {
            FileFilter filter = new NameFilter(filterString);
            findFiles(pathFile, filter, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findFiles(File file, FileFilter filter, PrintStream output) throws IOException {
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (int i = Objects.requireNonNull(list, "list must not be null").length; --i >= 0; ) {
                findFiles(list[i], filter, output);
            }
        } else {
            if (filter.accept(file))
                output.println(file.getCanonicalPath());
        }
    }
}

