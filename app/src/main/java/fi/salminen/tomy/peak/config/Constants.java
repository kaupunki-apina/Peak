package fi.salminen.tomy.peak.config;


import fi.salminen.tomy.peak.BuildConfig;

public class Constants {

    // Private constructor to prevent initialisation.
    private Constants() {}

    public static class Common {
        public static final String CONTENT_PROVIDER_AUTHORITY = new StringBuilder(BuildConfig.APPLICATION_ID)
                .append(".provider")
                .toString();
    }

    public class API {
        public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    }
}
