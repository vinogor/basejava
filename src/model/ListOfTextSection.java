package model;

import java.util.List;
import java.util.Objects;

public class ListOfTextSection extends AbstractSection {

    private List<String> listOfText;

    public ListOfTextSection(List<String> listOfText) {
        this.listOfText = listOfText;
    }

    public List<String> getListOfItems() {
        return listOfText;
    }

    public void setListOfItems(List<String> listOfText) {
        this.listOfText = listOfText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfTextSection that = (ListOfTextSection) o;
        return Objects.equals(listOfText, that.listOfText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfText);
    }

    @Override
    public String toString() {
        return listOfText.toString();
    }
}
