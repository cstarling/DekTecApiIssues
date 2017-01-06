/**
 * Created by starling on 12/11/16.
 */
public class SpRcCommunicationException extends Exception {


    public SpRcCommunicationException() {
        super("Error communitating with SpRc");
    }

    public SpRcCommunicationException(String message) {
        super(message);
    }
}
