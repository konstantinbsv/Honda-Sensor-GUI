import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataPatterns {
    private static final String designatorString = "\\((\\d{4})\\)";        // matched value designator
    private static final Pattern designatorPattern = Pattern.compile(designatorString);

    private static final String capacitanceValueString = ("(\\d.\\d+)");     // matches capacitance value after value designator
    private static final Pattern capacitanceValuePattern = Pattern.compile(capacitanceValueString);

    private static final String fullRowString = "\\((\\d{4})\\)(\\d.\\d+)"; // matches full row from serial output
    private static final Pattern fullRowPattern = Pattern.compile(fullRowString);

    public static int getDesignatorInt (String rawData) {
        Matcher floatMatcher;

        floatMatcher = fullRowPattern.matcher(rawData);

        if (floatMatcher.find()) {
            return Integer.parseInt(floatMatcher.group(1), 2); // parse binary to base 10 int
        } else {
            return -1;
        }
    }

    public static double getCapacitanceValueDouble (String rawData) {
        Matcher floatMatcher;

        floatMatcher = fullRowPattern.matcher(rawData);

        if (floatMatcher.find()) {
            return Double.parseDouble(floatMatcher.group(2));   // parse to int, already in base 10
        } else {
            return 0.0;
        }
    }

    static class Designator {
        public static final int PRESSURE1 = 0b0000;
        public static final int PRESSURE2 = 0b0001;
        public static final int PRESSURE3 = 0b0010;
        public static final int PRESSURE4 = 0b0011;
        public static final int PRESSURE5 = 0b0100;
        public static final int PRESSURE6 = 0b0101;
        public static final int PRESSURE7 = 0b0110;
        public static final int PRESSURE8 = 0b0111;
        public static final int PRESSURE_CENTER = 0b1000;
        public static final int PROXIMITY = 0b1001;
        public static final int SHEAR_X = 0b1010;
        public static final int SHEAR_Y = 0b1011;
    }
}
