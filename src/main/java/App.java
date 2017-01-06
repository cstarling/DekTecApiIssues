import sprc.PortDesc;

import java.io.IOException;
import java.util.List;

/**
 * Created by starling on 12/7/16.
 */

public class App {

    public static final String GO_PRO_STAY_TUNED = "C:\\Users\\Media6-PC\\Desktop\\GoPro_StayTuned.ts";
    public static final String FSGO = "C:\\Users\\Media6-PC\\Desktop\\FSGO_2880gop15.ts";
    public static final String TS_FILE_DIRECTORY = "/Users/starling/Documents";
    public static final String TS_FILE_SUFFIX = ".txt";

    public static void main(String[] args) throws IOException, InterruptedException, SpRcCommunicationException {
        SpRcService spRcService1 = new SpRcService("10.10.30.30", "9000");
        SpRcService spRcService2 = new SpRcService("10.10.30.30", "9000");

        try {
            spRcService1.openSession();
            List<PortDesc> tst = spRcService1.scanPorts();
            for (PortDesc portDesc : tst) {
                System.out.println("portDesc = " + portDesc);
            }

            //long serial = spRcService1.getSerialNumber();
            //spRcService1.selectPort(serial, 1, 0);
            //spRcService1.getPlayoutinfo();

            //spRcService1.setLoopFlags(8);
            //spRcService1.openFile(GO_PRO_STAY_TUNED);
            //spRcService1.setTsoipPars(FSGO, 2, "239.118.9.57");

            //spRcService1.setPlayoutState(1);
        } finally {
            spRcService1.closeSession();
        }
    }
}
