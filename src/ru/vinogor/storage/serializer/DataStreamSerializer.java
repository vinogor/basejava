package ru.vinogor.storage.serializer;

import ru.vinogor.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();

            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = r.getSections();

            writeCollection(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType.name()) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        dos.writeUTF(((TextSection) entry.getValue()).getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> listOfStrings = ((ListOfTextSection) entry.getValue()).getListOfItems();
                        writeCollection(dos, listOfStrings, dos::writeUTF);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Organization> listOfDiffItems = ((ListOfOrganization) entry.getValue()).getListOfDiffItems();

                        writeCollection(dos, listOfDiffItems, subEntry -> {

                            dos.writeUTF(subEntry.getHomePage().getName());

                            if (subEntry.getHomePage().getUrl() != null) {
                                dos.writeUTF(subEntry.getHomePage().getUrl());
                            } else {
                                dos.writeUTF("");
                            }

                            List<Stage> stages = subEntry.getStages();

                            writeCollection(dos, stages, stage -> {
                                dos.writeInt(stage.getStart().getYear());
                                dos.writeInt(stage.getStart().getMonth().getValue());

                                dos.writeInt(stage.getEnd().getYear());
                                dos.writeInt(stage.getEnd().getMonth().getValue());

                                dos.writeUTF(stage.getHeadline());

                                if (stage.getContent() != null) {
                                    dos.writeUTF(stage.getContent());
                                } else {
                                    dos.writeUTF("");
                                }
                            });

                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readCollection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                readSection(sectionType, dis, resume);
            });
            return resume;
        }
    }

    private void readSection(SectionType sectionType, DataInputStream dis, Resume resume) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                resume.addSection(sectionType, new TextSection(dis.readUTF()));
                break;

            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> listOfItems = new ArrayList<>();
                readCollection(dis, () -> listOfItems.add(dis.readUTF()));
                resume.addSection(sectionType, new ListOfTextSection(listOfItems));
                break;

            case EXPERIENCE:
            case EDUCATION:
                List<Organization> listOfItems2 = new ArrayList<>();
                readCollection(dis, () -> {
                    Organization organization = new Organization(new Link(dis.readUTF(), dis.readUTF()));
                    List<Stage> listOfStages = new ArrayList<>();
                    readCollection(dis, () -> {
                        listOfStages.add(new Stage(
                                LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                LocalDate.of(dis.readInt(), dis.readInt(), 1),
                                dis.readUTF(),
                                dis.readUTF())
                        );
                        organization.setStages(listOfStages);
                    });
                    listOfItems2.add(organization);
                });
                resume.addSection(sectionType, new ListOfOrganization(listOfItems2));
                break;
        }
    }

    @FunctionalInterface
    private interface Writer<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface Action {
        void act() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writer<T> entry) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            entry.write(item);
        }
    }

    private void readCollection(DataInputStream dis, Action action) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            action.act();
        }
    }
}