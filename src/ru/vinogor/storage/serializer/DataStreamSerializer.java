package ru.vinogor.storage.serializer;

import ru.vinogor.model.*;
import ru.vinogor.util.LocalDateAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    private LocalDateAdapter localDateAdapter = new LocalDateAdapter();

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                switch (section.getKey().name()) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        dos.writeUTF(section.getKey().name());
                        dos.writeUTF(((TextSection) section.getValue()).getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        dos.writeUTF(section.getKey().name());
                        List<String> listOfStrings = ((ListOfTextSection) section.getValue()).getListOfItems();
                        dos.writeInt(listOfStrings.size());
                        for (String contentOfTextSection : listOfStrings) {
                            dos.writeUTF(contentOfTextSection);
                        }
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        dos.writeUTF(section.getKey().name());
                        List<Organization> listOfDiffItems = ((ListOfOrganization) section.getValue()).getListOfDiffItems();
                        dos.writeInt(listOfDiffItems.size());

                        for (Organization organization : listOfDiffItems) {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl());
                            List<Stage> stages = organization.getStages();
                            dos.writeInt(stages.size());
                            for (Stage stage : stages) {

                                try {
                                    dos.writeUTF(localDateAdapter.marshal(stage.getStart()));
                                } catch (Exception e) {
                                    System.out.println("Ошибка записи: " + e);
                                }

                                try {
                                    dos.writeUTF(localDateAdapter.marshal(stage.getEnd()));
                                } catch (Exception e) {
                                    System.out.println("Ошибка записи: " + e);
                                }

                                dos.writeUTF(stage.getHeadline());

                                if (stage.getContent() != null) {
                                    dos.writeUTF(stage.getContent());
                                } else {
                                    dos.writeUTF("");
                                }
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                switch (dis.readUTF()) {
                    case "OBJECTIVE":
                        resume.addSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                        break;
                    case "PERSONAL":
                        resume.addSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                        int sizeOfAchiv = dis.readInt();
                        List<String> listOfAchiv = new ArrayList<>();
                        for (int j = 0; j < sizeOfAchiv; j++) {
                            listOfAchiv.add(dis.readUTF());
                        }
                        resume.addSection(SectionType.ACHIEVEMENT, new ListOfTextSection(listOfAchiv));
                        break;
                    case "QUALIFICATIONS":
                        int sizeOfQual = dis.readInt();
                        List<String> listOfQual = new ArrayList<>();
                        for (int j = 0; j < sizeOfQual; j++) {
                            listOfQual.add(dis.readUTF());
                        }
                        resume.addSection(SectionType.QUALIFICATIONS, new ListOfTextSection(listOfQual));
                        break;
                    case "EXPERIENCE":
                        int sizeOfOrgOfExp = dis.readInt();
                        List<Organization> listOfOrg = new ArrayList<>();
                        readOrganization(dis, localDateAdapter, sizeOfOrgOfExp, listOfOrg);
                        resume.addSection(SectionType.EXPERIENCE, new ListOfOrganization(listOfOrg));
                        break;

                    case "EDUCATION":
                        int sizeOfEdu = dis.readInt();
                        List<Organization> listOfEdu = new ArrayList<>();
                        readOrganization(dis, localDateAdapter, sizeOfEdu, listOfEdu);
                        resume.addSection(SectionType.EDUCATION, new ListOfOrganization(listOfEdu));
                        break;
                }
            }
            System.out.println(resume);
            return resume;
        }
    }

    private void readOrganization(DataInputStream dis, LocalDateAdapter localDateAdapter, int sizeOfOrgOfExp, List<Organization> listOfOrg) throws IOException {
        for (int j = 0; j < sizeOfOrgOfExp; j++) {
            Organization organization = new Organization(new Link(dis.readUTF(), dis.readUTF()));
            int sizeOfStages = dis.readInt();
            List<Stage> listOfStages = new ArrayList<>();
            for (int k = 0; k < sizeOfStages; k++) {
                try {
                    listOfStages.add(new Stage(
                            localDateAdapter.unmarshal(dis.readUTF()),
                            localDateAdapter.unmarshal(dis.readUTF()),
                            dis.readUTF(),
                            dis.readUTF())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
                organization.setStages(listOfStages);
            }
            listOfOrg.add(organization);
        }
    }
}