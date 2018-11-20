package ru.vinogor;

import java.io.*;

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

        System.out.println("=========================================");

        File directory = new File("../basejava/src");
        String indent = "";
        outputAllFiles(directory, indent);
    }


    private static void outputAllFiles(File directory, String indent) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(indent + "Dir: " + file.getName());
                    outputAllFiles(file, (indent + "\t"));
                } else {
                    System.out.println(indent + "File: " + file.getName());
                }
            }
        }
    }
}