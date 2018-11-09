package model;

import java.util.List;
import java.util.Objects;

public class ListOfDiffItemsSection extends AbstractSection {
    private List<DiffItems> listOfDiffItems;

    public ListOfDiffItemsSection(List<DiffItems> listOfDiffItems) {
        this.listOfDiffItems = listOfDiffItems;
    }

    public List<DiffItems> getListOfDiffItems() {
        return listOfDiffItems;
    }

    public void setListOfDiffItems(List<DiffItems> listOfDiffItems) {
        this.listOfDiffItems = listOfDiffItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfDiffItemsSection that = (ListOfDiffItemsSection) o;
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
