import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public class ValidationTools {
    // Demonic regex for int validation via Gemini
    private static final Pattern POSITIVE_INT_WITH_LEADING_ZERO = Pattern.compile("(?=.*[1-9])\\d+");
    // Strict version of default
    private static final DateTimeFormatter STRICT_ISO_LOCAL_DATE =
            DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.STRICT);

    public static boolean isValidTaskIndex(String str) {
        return POSITIVE_INT_WITH_LEADING_ZERO.matcher(str).matches();
    }

    public static boolean isValidIsoDate(String str) {
        try {
            LocalDate.parse(str, STRICT_ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
