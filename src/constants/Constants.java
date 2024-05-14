package constants;

public final class Constants {
    public static final long millisInYear = 31556952000L;
    public static final long millisInDay = 86400000L;
    public static final long centsInEuro = 100L;
    private Constants() {
        throw new UnsupportedOperationException("This is a static utility class and cannot be instantiated");
    }
}
