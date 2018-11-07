package model;

import java.util.Objects;

class DiffItems {
    private String name;
    private String link;
    private String period;
    private String headline;
    private String content;


    public DiffItems(String name, String link, String period, String headline, String content) {
        this.name = name;
        this.link = link;
        this.period = period;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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
        DiffItems diffItems = (DiffItems) o;

        if (!name.equals(diffItems.name)) return false;
        if (!link.equals(diffItems.link)) return false;
        if (!period.equals(diffItems.period)) return false;
        if (!headline.equals(diffItems.headline)) return false;
        return content.equals(diffItems.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link, period, headline, content);
    }

    @Override
    public String toString() {
        return "DiffItems{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", period='" + period + '\'' +
                ", headline='" + headline + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

