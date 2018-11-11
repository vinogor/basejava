package model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private String name;
    private String link;
    private LocalDate start;
    private LocalDate end;
    private String headline;
    private String content;

    public Organization(String name, String link, LocalDate start, LocalDate end, String headline, String content) {
        this.name = name;
        this.link = link;
        this.start = start;
        this.end = end;
        this.headline = headline;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(link, that.link) &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end) &&
                Objects.equals(headline, that.headline) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link, start, end, headline, content);
    }

    @Override
    public String toString() {
        return "Organization{" +
                 name +
                 link +
                 start +
                 end +
                 headline +
                 content +
                '}';
    }
}

