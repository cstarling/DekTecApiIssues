import sprc.*;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;
    public static final String FSGO = "C:\\Users\\Media6-PC\\Desktop\\FSGO_2880gop15.ts";

    RunnableDemo(String name) {
        threadName = name;
    }

    public void setBindingProvider(String ip, String port) {

    }

    public void run() {
        SpRc spRc = new SpRc();
        SpRcPortType spRcPortType = spRc.getSpRc();
        BindingProvider bindingProvider = (BindingProvider) spRcPortType;
        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                "http://10.10.30.30:9000");

        System.out.println("Session open: " + threadName);
        long openSessionResult = spRcPortType.openSession();
        System.out.println("openSessionResult = " + openSessionResult + " :: ThreadName:: " + threadName);
        long closeSessionResult = spRcPortType.closeSession();
        System.out.println("closeSessionResult = " + closeSessionResult + " :: ThreadName:: " + threadName);
    }


    public void scanPorts(SpRcPortType spRcPortType) {
        Holder<PortDescArray> portDescArrayHolder = new Holder<>();
        Holder<Long> spRcResultHolderScanPorts = new Holder<>();
        spRcPortType.scanPorts(portDescArrayHolder, spRcResultHolderScanPorts);
        System.out.println("spRcResultHolderScanPorts = " + spRcResultHolderScanPorts);
        System.out.println("portDescArrayHolder.value = " + portDescArrayHolder.value + "::Thread Name: " + threadName);

        try {
            for (PortDesc portDesc : portDescArrayHolder.value.getItem()) {
                System.out.println("portDesc = " + portDesc);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void getPlayoutInfo(SpRcPortType spRcPortType) {
        Holder<PlayoutInfo> playoutInfo = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();
        spRcPortType.getPlayoutInfo(playoutInfo, spRcResultHolder);


        System.out.println("playoutInfo.value = " + playoutInfo.value);
        playoutInfo.value.getFilename();
    }

    public void start() throws InterruptedException {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();

        }
    }
}

public class TestThread {

    public static void main(String args[]) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            RunnableDemo R1 = new RunnableDemo("Thread-" + i);
            R1.start();
            Thread.sleep(500);
        }


    }
}



/*
    public void run() {

        SpRcService spRcService1 = new SpRcService("10.10.30.30", "9000");
        StreamXpressService streamXpressService = new StreamXpressService();

        Instance instance = new Instance();
        instance.setIp("10.10.30.30");
        instance.setInstancePort("9000");
        instance.setInstanceNumber("1");
        instance.setSelectedPhysicalPort(2);
        instance.setInitalMulticastAddress("239.118.9.50");
        instance.setInitalMulticastPort(1050);
        instance.setFileToStartUpWith("FSGO_2880gop15.ts");
        instance.setSpRcService(spRcService);

        System.out.println("instance = " + instance);
        System.out.println("instance.getSpRcService() = " + instance.getSpRcService());

        StreamXpressPlayoutData streamXpressPlayoutData = streamXpressService.getInstanceData(instance);
        System.out.println("streamXpressPlayoutData = " + streamXpressPlayoutData);
    }
    */



/*

Holder<PortDescArray> portDescArrayHolder = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();
        PortDesc activePort = null;
        spRcPortType.scanPorts(portDescArrayHolder, spRcResultHolder);
        System.out.println("portDescArrayHolder = " + portDescArrayHolder.value + " :: ThreadName:: " + threadName);
        System.out.println("portDescArrayHolder.getItem() = " + portDescArrayHolder.value.getItem() + " :: ThreadName:: " + threadName);
        System.out.println("spRcResultHolder.value = " + spRcResultHolder.value + " :: ThreadName:: " + threadName);
        if (spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue()) {
            for (PortDesc portDesc : portDescArrayHolder.value.getItem()) {
                if (portDesc.getInUse() == 1) {
                    activePort = portDesc;
                }
            }
        }
 */