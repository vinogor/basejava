package model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListOfOrganization extends AbstractSection {
    private List<Organization> listOfDiffItems;

    public ListOfOrganization(List<Organization> listOfDiffItems) {
        Objects.requireNonNull(listOfDiffItems, "listOfDiffItems must not be null");
        this.listOfDiffItems = listOfDiffItems;
    }

    public ListOfOrganization(Organization... listOfDiffItems) {
        Objects.requireNonNull(listOfDiffItems, "listOfDiffItems must not be null");
        this.listOfDiffItems = Arrays.asList(listOfDiffItems);
    }

    public List<Organization> getListOfDiffItems() {
        return listOfDiffItems;
    }

    public void setListOfDiffItems(List<Organization> listOfDiffItems) {
        this.listOfDiffItems = listOfDiffItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfOrganization that = (ListOfOrganization) o;
        return Objects.equals(listOfDiffItems, that.listOfDiffItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfDiffItems);
    }

    @Override
    public String toString() {
        return listOfDiffItems.toString();
    }
}
