package android.util;

import java.util.regex.Pattern;

/**
 * Stand-in for {@code android.util.Patterns}, carrying the framework
 * expressions used by the validators. They are the same expressions the library
 * already embeds inline as its pre API 8 fallbacks. Used by the benchmarks only.
 * See {@code benchmarks/src/framework/README.md}.
 */
public class Patterns {

    public static final Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static final Pattern IP_ADDRESS = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))"
    );

    public static final Pattern PHONE = Pattern.compile(          // sdd = space, dot, or dash
            "(\\+[0-9]+[\\- \\.]*)?"                              // +<digits><sdd>*
                    + "(\\([0-9]+\\)[\\- \\.]*)?"                 // (<digits>)<sdd>*
                    + "([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])"     // <digit><digit|sdd>+<digit>
    );

    private Patterns() {
    }
}
