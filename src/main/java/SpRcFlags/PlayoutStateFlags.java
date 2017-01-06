package SpRcFlags;

/**
 * Created by starling on 12/9/16.
 */
public enum PlayoutStateFlags {
    SPRC_STATE_PAUSE(0),
    SPRC_STATE_PLAY(1),
    SPRC_STATE_STOP(2);

    private final int value;

    PlayoutStateFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    /*
    // Playout state
#define SPRC_STATE_PAUSE         0  // Pause
#define SPRC_STATE_PLAY          1  // Playing
#define SPRC_STATE_STOP          2  // Stop
     */
}
