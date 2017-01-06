import sprc.SpRc;
import sprc.SpRcPortType;

import javax.xml.ws.BindingProvider;

class CloseSessionIssue implements Runnable {
    private Thread t;
    private String threadName;
    private String ip;
    private String port;

    CloseSessionIssue(String name, String ip, String port) {
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
                "http://" + this.ip + ":" + this.port + "");

        System.out.println("Session open: " + threadName);
        long openSessionResult = spRcPortType.openSession();
        System.out.println("openSessionResult = " + openSessionResult + " :: ThreadName:: " + threadName);
        long closeSessionResult = spRcPortType.closeSession();
        System.out.println("closeSessionResult = " + closeSessionResult + " :: ThreadName:: " + threadName);
    }

    public void start() throws InterruptedException {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}

class TestThread {
    public static void main(String args[]) throws InterruptedException {
        //CHANGE THE IP HERE

        String ip = "10.10.30.30";
        String port = "9000";
        for (int i = 0; i < 10; i++) {
            CloseSessionIssue R1 = new CloseSessionIssue("Thread-" + i, ip, port);
            R1.start();
        }
    }
}



