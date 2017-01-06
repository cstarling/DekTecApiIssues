import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by starling on 12/18/16.
 */
public class ConversionUtils {

    public static String convertSignedByteArrayToUnsignedIPString(byte[] byteArray) {
        String ipString = "";
        //loop throgh the byte arrray converting each element to an unsigned int.  this will turn -17 to 239 for example
        for (byte br : byteArray) {
            int unsignedInt = Byte.toUnsignedInt(br);
            //concatenate wit a . to make an IP like sytanx
            ipString += Integer.toString(unsignedInt) + ".";
        }
        //if it ends with a dot remove it
        if (ipString.endsWith(".")) {
            ipString = ipString.substring(0, ipString.length() - 1);
        }
        return ipString;
    }

    public static byte[] convertIpStringToUnsignedByteArray(String ipString) throws UnknownHostException {
        byte[] bytes = InetAddress.getByName(ipString).getAddress();

        return bytes;

    }
}
