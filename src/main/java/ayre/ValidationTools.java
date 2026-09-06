package ayre;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

/**
 * A helper class with some useful methods for validating String input against a certain pattern or format.
 */
public class ValidationTools {
    // Regex for int validation via Gemini
    private static final Pattern POSITIVE_INT_WITH_LEADING_ZERO = Pattern.compile("-?\\d+");
    // Strict version of default
    private static final DateTimeFormatter STRICT_ISO_LOCAL_DATE =
            DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.STRICT);

    public static boolean isInvalidTaskIndex(String str) {
        return !POSITIVE_INT_WITH_LEADING_ZERO.matcher(str).matches();
    }

    public static boolean isInvalidIsoDate(String str) {
        try {
            LocalDate.parse(str, STRICT_ISO_LOCAL_DATE);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }
}
