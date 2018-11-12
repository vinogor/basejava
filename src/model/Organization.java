package model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private Link homePage;
    private LocalDate start;
    private LocalDate end;
    private String headline;
    private String content;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String headline, String content) {
        Objects.requireNonNull(startDate, "startDate must not be null");
    //  Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(headline, "headline must not be null");
        this.homePage = new Link(name, url);
        this.start = startDate;
        this.end = endDate;
        this.headline = headline;
        this.content = content;
    }

    public Link getHomePage() {
        return homePage;
    }

    public void setHomePage(Link homePage) {
        this.homePage = homePage;
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

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        if (!start.equals(that.start)) return false;
        if (end != null ? !end.equals(that.end) : that.end != null) return false;
        if (!headline.equals(that.headline)) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + start.hashCode();
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + headline.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{ " +
                "homePage name=" + homePage.getName() +
                ", homePage url=" + homePage.getUrl() +
                ", start=" + start +
                ", end=" + end +
                ", headline='" + headline + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

