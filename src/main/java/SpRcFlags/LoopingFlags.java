package SpRcFlags;

/**
 * Created by starling on 12/9/16.
 */
public enum LoopingFlags {
    NONE(0),
    SPRC_LOOP_CC(1),
    SPRC_LOOP_PCR(2),
    SPRC_LOOP_TDT(4),
    SPRC_LOOP_WRAP(8);

    private final int value;

    LoopingFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /*
    // Loop-adaptation flags
        #define SPRC_LOOP_CC             1  // Adapt continuity counters
        #define SPRC_LOOP_PCR            2  // Adapt PCR
        #define SPRC_LOOP_TDT            4  // Adapt TDT
        #define SPRC_LOOP_WRAP           8  // Auto wrap-around
     */
}
