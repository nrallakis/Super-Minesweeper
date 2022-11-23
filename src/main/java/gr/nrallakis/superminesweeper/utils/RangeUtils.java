package gr.nrallakis.superminesweeper.utils;

public class RangeUtils {
    /**
     * Simple inclusive range check
     *
     * @param value the value to check
     * @return whether value is in the inclusive range [min, max]
     */
    public static boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
