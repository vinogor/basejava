package ru.vinogor.util;

import ru.vinogor.model.Stage;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Stage stage) {
        return DateUtil.format(stage.getStart()) + " - " + DateUtil.format(stage.getEnd());
    }

}