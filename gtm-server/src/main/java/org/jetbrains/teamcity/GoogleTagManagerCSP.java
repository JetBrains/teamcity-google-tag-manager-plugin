package org.jetbrains.teamcity;

import jetbrains.buildServer.web.ContentSecurityPolicyConfig;

import java.util.Arrays;

/**
 * @author kir
 */
public class GoogleTagManagerCSP {
    public GoogleTagManagerCSP(ContentSecurityPolicyConfig config) {

        config.addDirectiveItems("script-src",
                "https://www.google-analytics.com",
                "https://www.googletagmanager.com",
                "https://tagmanager.google.com",
                "https://stats.g.doubleclick.net");

        config.addDirectiveItems("img-src",
                "https://www.google-analytics.com", "https://stats.g.doubleclick.net");
        Arrays.asList("com", "ru", "de", "cz", "fr", "it", "nl", "com.ua").forEach(s ->
                config.addDirectiveItems("img-src", "https://www.google." + s));

        config.addDirectiveItems("connect-src", "https://www.google-analytics.com");
        config.addDirectiveItems("frame-src", "https://fonts.gstatic.com");
        config.addDirectiveItems("font-src", "https://fonts.gstatic.com");
        config.addDirectiveItems("style-src",
                "https://fonts.googleapis.com", "https://tagmanager.google.com");
    }
}
