package edu.java.bot.commands;

import java.net.URL;

@SuppressWarnings("HideUtilityClassConstructor")
public class UrlChecker {
    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
