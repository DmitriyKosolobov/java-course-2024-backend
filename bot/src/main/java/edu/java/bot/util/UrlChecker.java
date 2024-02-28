package edu.java.bot.util;

import java.net.URL;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlChecker {
    public boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
