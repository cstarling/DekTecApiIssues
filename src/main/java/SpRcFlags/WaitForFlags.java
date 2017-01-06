package SpRcFlags;

/**
 * Created by starling on 12/11/16.
 */
public enum WaitForFlags {
    SPRC_COND_STOPPED(1);

    private final int value;

    WaitForFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
