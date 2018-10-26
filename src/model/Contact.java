package model;

class Contact {
    private String typeOfContact;
    private String valueOfContact;

    public Contact(String typeOfContact, String valueOfContact) {
        this.typeOfContact = typeOfContact;
        this.valueOfContact = valueOfContact;
    }

    public String getTypeOfContact() {
        return typeOfContact;
    }

    public void setTypeOfContact(String typeOfContact) {
        this.typeOfContact = typeOfContact;
    }

    public String getValueOfContact() {
        return valueOfContact;
    }

    public void setValueOfContact(String valueOfContact) {
        this.valueOfContact = valueOfContact;
    }
}
