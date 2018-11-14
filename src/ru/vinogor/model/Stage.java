package ru.vinogor.model;

import java.time.LocalDate;

public class Stage {
    private LocalDate start;
    private LocalDate end;
    private String headline;
    private String content;

    public Stage(LocalDate start, LocalDate end, String headline, String content) {
        this.start = start;
        this.end = end;
        this.headline = headline;
        this.content = content;
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

        Stage stage = (Stage) o;

        if (!start.equals(stage.start)) return false;
        if (end != null ? !end.equals(stage.end) : stage.end != null) return false;
        if (!headline.equals(stage.headline)) return false;
        return content != null ? content.equals(stage.content) : stage.content == null;
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + headline.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (end == null) {
            return "\n" + "         Stage{" +
                    "start=" + start +
                    ", end=сейчас" +
                    ", headline='" + headline + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        } else {

            return "\n" + "         Stage{" +
                    "start=" + start +
                    ", end=" + end +
                    ", headline='" + headline + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
