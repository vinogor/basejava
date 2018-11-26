package ru.vinogor.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private Link homePage;

    private List<Stage> stages = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String headline, String content) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(headline, "headline must not be null");
        this.homePage = new Link(name, url);
        this.stages.add(new Stage(startDate, endDate, headline, content));
    }

    public Organization(Link homePage, Stage... stages) {
        this.homePage = homePage;
        this.stages = Arrays.asList(stages);
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public Link getHomePage() {
        return homePage;
    }

    public void setHomePage(Link homePage) {
        this.homePage = homePage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        return stages != null ? stages.equals(that.stages) : that.stages == null;
    }


    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (stages != null ? stages.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "\n" + "     Organization{" +
                "homePage=" + homePage +
                ", stages=" + stages +
                '}';
    }
}

