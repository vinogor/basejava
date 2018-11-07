package model;

import java.util.Objects;

public class SectionText extends AbstractSectionContent {
    private String textContent;

    public SectionText(String textContent) {
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionText that = (SectionText) o;
        return Objects.equals(textContent, that.textContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textContent);
    }

    @Override
    public String toString() {
        return textContent;
    }
}
