package at.mavila.exercises_january_2026.domain.string;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * Domain service for masking sensitive string data.
 *
 * <p>
 * This component provides functionality to mask all but the last 4 characters
 * of a string, typically used for masking credit card numbers, PINs, or other
 * sensitive information.
 * </p>
 *
 * <h2>Algorithm</h2>
 * <p>
 * Uses a regex lookahead to replace each character that has at least 4
 * characters after it with a mask character (#).
 * </p>
 *
 * <h2>Examples</h2>
 * <ul>
 * <li>"4556364607935616" → "############5616"</li>
 * <li>"1234" → "1234"</li>
 * <li>"1" → "1"</li>
 * </ul>
 *
 * <h2>Complexity</h2>
 * <ul>
 * <li><b>Time:</b> O(n) where n is the string length</li>
 * <li><b>Space:</b> O(n) for the result string</li>
 * </ul>
 *
 * @author mavila
 * @since January 2026
 */
@Component
public class MaskService {

    private static final ThreadLocal<Pattern> PATTERN = ThreadLocal.withInitial(() -> Pattern.compile(".(?=.{4})"));

    /**
     * Masks all but the last 4 characters of the input string.
     *
     * @param str the string to mask
     * @return the masked string with '#' replacing characters, or empty string if
     *         null
     */
    public String maskify(final String str) {
        return isNull(str) ? EMPTY : applyMaskPattern(str);
    }

    private String applyMaskPattern(final String str) {
        return PATTERN.get().matcher(str).replaceAll("#");
    }

}
