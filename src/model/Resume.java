package model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
  //  private List<String> contacts = new ArrayList<>();

    private Map<String, Contact> contact = new HashMap<>(); // Ключ мапы = typeOfContact
    private Map<SectionType, SectionContent> section = new HashMap<>(); // Ключ мапы = тип секции


    public String getFullName() {
        return fullName;
    }

    public Map<String, Contact> getContact() {
        return contact;
    }

    public void setContact(Map<String, Contact> contact) {
        this.contact = contact;
    }

    public Map<SectionType, SectionContent> getSection() {
        return section;
    }

    public void setSection(Map<SectionType, SectionContent> section) {
        this.section = section;
    }


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}