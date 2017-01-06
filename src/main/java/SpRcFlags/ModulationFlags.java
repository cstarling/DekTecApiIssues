package SpRcFlags;

/**
 * Created by starling on 12/11/16.
 */
public enum ModulationFlags {
    NONE(0),
    SPRC_OTYPE_TSOIP(131072);

    private final int value;

    ModulationFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
