package SpRcFlags;

/**
 * Created by starling on 12/9/16.
 */
public enum PortFlags {
    SPRC_PORT_UNUSED(0),
    SPRC_PORT_CURR_IN_USE(1),
    SPRC_PORT_USED(2);

    private final int value;

    PortFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /*
    // Port-in-use values
#define SPRC_PORT_UNUSED         0  // Port is not used
#define SPRC_PORT_CURR           1  // Port is currently selected play-out port
#define SPRC_PORT_USED           2  // Port is used by another application
     */
}
