import sprc.PlayoutInfo;
import sprc.SpRc;
import sprc.SpRcPortType;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

class GetPlayoutInfoIssue implements Runnable {
    private Thread t;
    private String threadName;
    private String ip;
    private String port;

    public static final String FSGO = "C:\\Users\\Media6-PC\\Desktop\\FSGO_2880gop15.ts";

    GetPlayoutInfoIssue(String name, String ip, String port) {
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


        this.getPlayoutInfo(spRcPortType);
        long closeSessionResult = spRcPortType.closeSession();
        System.out.println("closeSessionResult = " + closeSessionResult + " :: ThreadName:: " + threadName);
    }


    public void getPlayoutInfo(SpRcPortType spRcPortType) {
        Holder<PlayoutInfo> playoutInfo = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();
        spRcPortType.getPlayoutInfo(playoutInfo, spRcResultHolder);


        System.out.println("playoutInfo.value = " + playoutInfo.value.getFilename().getValue().toString());
    }

    public void start() throws InterruptedException {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}

class TestThread2 {

    public static void main(String args[]) throws InterruptedException {
        //CHANGE THE IP HERE
        String ip = "10.10.30.30";
        String port = "9000";
        for (int i = 0; i < 10; i++) {
            GetPlayoutInfoIssue T1 = new GetPlayoutInfoIssue("Thread-" + i, ip, port);
            T1.start();
        }
    }
}



