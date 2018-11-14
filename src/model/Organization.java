package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private Link homePage;

    private List<Stage> stage = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String headline, String content) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(headline, "headline must not be null");
        this.homePage = new Link(name, url);
        this.stage.add(new Stage(startDate, endDate, headline, content));
    }

    public Organization(Link homePage, Stage... stage) {
        this.homePage = homePage;
        this.stage = Arrays.asList(stage);
    }

    public List<Stage> getStage() {
        return stage;
    }

    public void setStage(ArrayList<Stage> stage) {
        this.stage = stage;
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
        return stage != null ? stage.equals(that.stage) : that.stage == null;
    }


    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "\n" + "     Organization{" +
                "homePage=" + homePage +
                ", stage=" + stage +
                '}';
    }
}

