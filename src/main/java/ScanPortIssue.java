import sprc.PortDesc;
import sprc.PortDescArray;
import sprc.SpRc;
import sprc.SpRcPortType;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

class ScanPortIssue implements Runnable {
    private Thread t;
    private String threadName;
    private String ip;
    private String port;

    public static final String FSGO = "C:\\Users\\Media6-PC\\Desktop\\FSGO_2880gop15.ts";

    ScanPortIssue(String name, String ip, String port) {
        this.threadName = name;
        this.ip = ip;
        this.port = port;
    }

    public void run() {
        SpRc spRc = new SpRc();
        SpRcPortType spRcPortType = spRc.getSpRc();
        BindingProvider bindingProvider = (BindingProvider) spRcPortType;
        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                "http://" + ip + ":" + port + "");

        System.out.println("Session open: " + threadName);
        long openSessionResult = spRcPortType.openSession();
        System.out.println("openSessionResult = " + openSessionResult + " :: ThreadName:: " + threadName);


        this.scanPorts(spRcPortType);
        long closeSessionResult = spRcPortType.closeSession();
        System.out.println("closeSessionResult = " + closeSessionResult + " :: ThreadName:: " + threadName);
    }


    public void scanPorts(SpRcPortType spRcPortType) {
        Holder<PortDescArray> portDescArrayHolder = new Holder<>();
        Holder<Long> spRcResultHolderScanPorts = new Holder<>();
        spRcPortType.scanPorts(portDescArrayHolder, spRcResultHolderScanPorts);
        System.out.println("spRcResultHolderScanPorts = " + spRcResultHolderScanPorts.value);
        System.out.println("portDescArrayHolder.value = " + portDescArrayHolder.value + "::Thread Name: " + threadName);

        try {
            for (PortDesc portDesc : portDescArrayHolder.value.getItem()) {
                System.out.println("portDesc = " + portDesc);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void start() throws InterruptedException {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}

class TestThread1 {

    public static void main(String args[]) throws InterruptedException {
        //CHANGE THE IP HERE
        String ip = ;
        String port = ;
        for (int i = 0; i < 10; i++) {
            ScanPortIssue T1 = new ScanPortIssue("Thread-" + i, ip, port);
            T1.start();
        }
    }
}



