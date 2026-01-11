package at.mavila.exercises_january_2026.components;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class MaskInput {

    private static final ThreadLocal<Pattern> PATTERN = ThreadLocal.withInitial(() -> Pattern.compile(".(?=.{4})"));

    public String maskify(final String str) {
        if (Objects.isNull(str)) {
            return "";
        }
        final Pattern pattern = PATTERN.get();
        final Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("#");
    }

}
