package random832.tooltipmod;

public enum DisplayMode {
    ALWAYS(true, false, false),
    ADVANCED(true, true, false),
    SHIFT(true, false, true),
    SHIFT_ADVANCED(true, true, true),
    NEVER(false, true, true)
    ;

    private final boolean isEnabled;
    private final boolean requireAdvanced;
    private final boolean requireShift;

    DisplayMode(boolean isEnabled, boolean requireAdvanced, boolean requireShift) {
        this.isEnabled = isEnabled;
        this.requireAdvanced = requireAdvanced;
        this.requireShift = requireShift;
    }

    public boolean check(boolean isAdvanced, boolean isShift) {
        return isEnabled && (!requireAdvanced || isAdvanced) && (!requireShift || isShift);
    }
}
