package SpRcFlags;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by starling on 12/11/16.
 */
public enum ReturnCodeFlags {
    SPRC_UNKNOWN(-1, "Error not made yet or unknown error"),
    SPRC_OK(0, "No issue"),
    SPRC_TIME_OUT(1, "Timeout occured"),
    SPRC_E_COMMUNICATION(8193, "An error has occurred in the communication with the playout server"),
    SPRC_E_FILE_CANT_FIND(8195, "Can’t find a file with the specified filename"),
    SPRC_E_INV_CONDITION(8197, "An invalid condition has been specified"),
    SPRC_E_INV_PARS(8200, "Invaild parameters"),
    SPRC_E_INV_STATE(8201, "Invalid playout state"),
    SPRC_E_MOD_STANDARD(8202, "Initial modulation standard Modulation is not supported on the modulator port"),
    SPRC_E_NO_LICK(8203, "The port is not properly licensed for playout and remote control"),
    SPRC_E_NOT_FOUND(8206, "Cannot find the playout port identified by Serial and Port"),
    SPRC_E_NO_PORT(8204, "Invalid operation because no port is selected"),
    SPRC_E_NOT_TSOIP(8211, "Invalid operation because the port is not a TSoIP port"),
    SPRC_E_PORT_USED(8214, "The port could not be selected because it’s in use by another instance of the StreamXpress or another application");

    private final int value;
    private final String message;


    ReturnCodeFlags(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }


    private static Map<Integer, ReturnCodeFlags> map = new HashMap<Integer, ReturnCodeFlags>();

    static {
        for (ReturnCodeFlags legEnum : ReturnCodeFlags.values()) {
            map.put(legEnum.value, legEnum);
        }
    }

    public static String getMessageValueFromInt(int value) {
        ReturnCodeFlags re = map.get(value);
        if (re == null) {
            return SPRC_UNKNOWN.getMessage() + " | Code: " + value;
        }

        return map.get(value).getMessage() + " | Code: " + value;
    }


    /*

    Hello, World!
SPRC_E_FILE_CANT_FIND: 8195SPRC_OK: 0SPRC_OK: 0
rfrf: 1
SPRC_E: 8192
SPRC_E_CALLBACK_NOT_SET: 8192
SPRC_E_COMMUNICATION: 8193
SPRC_E_CONN_OPEN_ERROR: 8194
SPRC_E_FILE_CANT_FIND: 8195
SPRC_E_ITF_NOT_SUPPORTED: 8196
SPRC_E_INV_CONDITION: 8197
SPRC_E_INV_FREQ: 8198
SPRC_E_INV_LEVEL: 8199
SPRC_E_INV_PARS: 8200
SPRC_E_INV_STATE: 8201
SPRC_E_MOD_STANDARD: 8202
SPRC_E_NO_LICK: 8203
SPRC_E_NO_PORT: 8204
SPRC_E_NOT_ASI: 8205
SPRC_E_NOT_FOUND: 8206
SPRC_E_NOT_DVBT2: 8207
SPRC_E_NOT_ISDBT: 8208
SPRC_E_NOT_MOD: 8209
SPRC_E_NOT_SPI: 8210
SPRC_E_NOT_TSOIP: 8211
SPRC_E_OP_NOT_SUPPORTED: 8212
SPRC_E_POLARITY: 8213
SPRC_E_PORT_USED: 8214
SPRC_E_SERVER_NOT_FOUND: 8215
SPRC_E_SESSION_NOT_OPEN: 8216
SPRC_E_SESSION_OPEN: 8217
SPRC_E_TXMODE: 8218
SPRC_E_NOT_CMMB: 8219
SPRC_E_INV_DVBT2_GROUP: 8220
SPRC_E_FILE_SYNTAX_ERROR: 8221
SPRC_E_FILE_CANT_CREATE: 8222
SPRC_E_REGRTEST_ONLY: 8223
Program ended with exit code: 0
     */

}
