/**
 * Created by starling on 12/9/16.
 */

import SpRcFlags.ModulationFlags;
import SpRcFlags.ReturnCodeFlags;
import sprc.*;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Created by starling on 12/9/16.
 */
public class SpRcService {

    private String ip;
    private String port;

    private static SpRc spRc = new SpRc();

    private SpRcPortType spRcPortType;

    public SpRcService() {
        this.ip = "localhost";
        this.port = "9000";
        this.spRcPortType = spRc.getSpRc();
        setBindingProvider(this.ip, this.port);

    }

    public SpRcService(String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.spRcPortType = spRc.getSpRc();
        setBindingProvider(ip, port);
    }

    public SpRcService(String ip, String port, SpRcPortType spRcPortType) {
        this.ip = ip;
        this.port = port;
        this.spRcPortType = spRcPortType;
    }

    public void setBindingProvider(String ip, String port) {
        BindingProvider bindingProvider = (BindingProvider) spRcPortType;
        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                "http://" + ip + ":" + port);

    }


    public void getPorts() {
        Iterator itr = spRc.getPorts();
        while (itr.hasNext()) {
            QName element = (QName) itr.next();
            System.out.println(element + " ");
        }
    }


    public void openSession() throws SpRcCommunicationException {
        long result = this.spRcPortType.openSession();
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }
    }

    public void closeSession() throws SpRcCommunicationException, WebServiceException {
        long result = this.spRcPortType.closeSession();
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }
    }

    public List<PortDesc> scanPorts() throws SpRcCommunicationException {
        Holder<PortDescArray> portDescArrayHolder = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();
        spRcPortType.scanPorts(portDescArrayHolder, spRcResultHolder);
        System.out.println("spRcResultHolder.value = " + spRcResultHolder.value);
        if (spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue()) {
            if (portDescArrayHolder.value != null) {
                if (portDescArrayHolder.value.getItem() != null) {
                    return portDescArrayHolder.value.getItem();
                }
            }
        } else {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt(spRcResultHolder.value.intValue()));
        }
        return null;
    }


    public void setLoopFlagOnCurrentPort(int flag) throws SpRcCommunicationException {
        try {
            this.openSession();
            long result = spRcPortType.setLoopFlags(flag);
            if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
                throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
            }
        } finally {
            this.closeSession();
        }
    }

    public PortDesc getActivePort() throws SpRcCommunicationException, WebServiceException {
        Holder<PortDescArray> portDescArrayHolder = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();
        PortDesc activePort = null;
        try {
            this.openSession();
            spRcPortType.scanPorts(portDescArrayHolder, spRcResultHolder);
            if (spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue()) {
                for (PortDesc portDesc : portDescArrayHolder.value.getItem()) {
                    if (portDesc.getInUse() == 1) {
                        activePort = portDesc;
                    }
                }
            } else {
                throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt(spRcResultHolder.value.intValue()));
            }
        } finally {
            this.closeSession();
        }

        return activePort;
    }


    public void setWaitForCondition(int condition, int timeout) throws SpRcCommunicationException {
        long result = spRcPortType.waitForCondition(condition, timeout);
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }
    }

    public void setLoopFlags(String filename, int port, int flag) throws SpRcCommunicationException, WebServiceException {
        try {
            this.openSession();
            this.selectPort(port, ModulationFlags.NONE.getValue());
            this.openFileWithSessionAlreadyOpen(filename);
            long result = spRcPortType.setLoopFlags(flag);
            System.out.println("result = " + result);
            if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
                throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
            }
        } finally {
            this.closeSession();
        }

    }

    public void setPlayoutState(String filename, int port, int playoutState) throws SpRcCommunicationException, WebServiceException {
        try {
            this.openSession();

            this.selectPort(port, ModulationFlags.NONE.getValue());
            this.openFileWithSessionAlreadyOpen(filename);
            long result = spRcPortType.setPlayoutState(playoutState);

            if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
                throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
            }
        } finally {
            this.closeSession();
        }
    }


    //GetPlayoutStatus
    public PlayoutInfo getPlayoutInfo() throws SpRcCommunicationException, WebServiceException {

        Holder<PlayoutInfo> playoutInfo = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();

        try {
            this.openSession();
            spRcPortType.getPlayoutInfo(playoutInfo, spRcResultHolder);
            if (!(spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue())) {
                throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(spRcResultHolder.value))));
            }
        } finally {
            this.closeSession();
        }
        return playoutInfo.value;
    }

    public String getCurrentFile() throws SpRcCommunicationException {
        Holder<PlayoutInfo> playoutInfo = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();

        spRcPortType.getPlayoutInfo(playoutInfo, spRcResultHolder);
        if (!(spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(spRcResultHolder.value))));
        }
        return playoutInfo.value.getFilename().getValue().toString();

    }


    public void openFile(int port, String filename) throws SpRcCommunicationException, WebServiceException {
        try {
            this.openSession();
            this.selectPort(port, ModulationFlags.NONE.getValue());
            long result = spRcPortType.openFile(filename);
            if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
                throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
            }
        } finally {
            System.out.println("IN FINALLY");
            this.closeSession();
        }

    }


    public void openFileWithSessionAlreadyOpen(String filename) throws SpRcCommunicationException {
        long result = spRcPortType.openFile(filename);
        System.out.println("result in openFileWIthSessionOpen = " + result);
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }
    }


    public TsoipPars getTsoipPars() throws SpRcCommunicationException, WebServiceException {
        Holder<TsoipPars> tsoioPars = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();

        try {
            this.openSession();
            spRcPortType.getTsoipPars(tsoioPars, spRcResultHolder);
        } finally {
            this.closeSession();
        }
        return tsoioPars.value;
    }


    private long getSeriaNumber() throws SpRcCommunicationException {
        List<PortDesc> portDesc = this.scanPorts();
        if (portDesc == null)
            throw new SpRcCommunicationException("Can't scan ports for some reason");
        else {
            if (portDesc.get(0) != null)
                return portDesc.get(0).getSerial();
            else
                throw new SpRcCommunicationException("Can't get serial number");
        }
    }

    public void setTsoipPars(int port, String filename, String outputAddress, int outputPort) throws UnknownHostException, SpRcCommunicationException, WebServiceException {
        try {
            this.openSession();
            this.selectPort(port,
                    ModulationFlags.SPRC_OTYPE_TSOIP.getValue());
            long openFileResult = spRcPortType.openFile(filename);
            if (!(openFileResult == ReturnCodeFlags.SPRC_OK.getValue())) {
                throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(openFileResult))));
            }

            byte[] bytes = InetAddress.getByName(outputAddress).getAddress();
            TsoipPars tsoioPars = getDefaultTsoIpPars();
            tsoioPars.setIp(bytes);
            tsoioPars.setPort(outputPort);


            long result = spRcPortType.setTsoipPars(tsoioPars);
            if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
                throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
            }
        } finally {
            this.closeSession();
        }


    }


    public TsoipPars getDefaultTsoIpPars() throws UnknownHostException {
        TsoipPars tsoioPars = new TsoipPars();
        tsoioPars.setTxMode(17); //--> check for default
        tsoioPars.setPort(1050);
        tsoioPars.setPort2(1050);
        tsoioPars.setEnaFallover(false);
        tsoioPars.setTimeToLive(128);
        tsoioPars.setNumTpPerIp(7);
        tsoioPars.setProtocol(0);
        tsoioPars.setDiffServ(0);
        tsoioPars.setFecMode(0);
        tsoioPars.setFecNumCols(10);
        tsoioPars.setFecNumRows(10);
        byte[] bytes2 = InetAddress.getByName("127.0.0.1").getAddress();

        tsoioPars.setIp2(bytes2);

        return tsoioPars;
    }

    public void selectPort(int playoutPort, int modulation) throws SpRcCommunicationException {
        Holder<Long> spRcResultHolder = new Holder<>();
        Holder<Integer> status = new Holder<>();
        long serialNumber = this.getSeriaNumber();

        spRcPortType.selectPort(serialNumber, playoutPort, modulation, spRcResultHolder, status);
        if (spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue()) {
        } else {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt(spRcResultHolder.value.intValue()));
        }

    }


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}

/*

public class SpRcService {

    private String ip;
    private String port;
    private static SpRc spRc = new SpRc();
    private SpRcPortType spRcPortType;

    public SpRcService(String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.spRcPortType = spRc.getSpRc();
        BindingProvider bindingProvider = (BindingProvider) spRcPortType;
        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                "http://" + ip + ":" + port);
    }

    public SpRcPortType getSpRcPortType() {
        return spRcPortType;
    }

    public void setSpRcPortType(SpRcPortType spRcPortType) {
        this.spRcPortType = spRcPortType;
    }

    public void getPorts() {
        Iterator itr = spRc.getPorts();

        while (itr.hasNext()) {
            QName element = (QName) itr.next();
            System.out.println(element + " ");
        }
    }


    public void openSession() {
        this.spRcPortType.openSession();
    }

    public void closeSession() {
        this.spRcPortType.closeSession();
    }

    public void getPlayoutinfo() {

        //the play out state is a 0 if it is paused and 1 if it is playing
        //spRcPortType.openSession();
        Holder<PlayoutInfo> playoutInfoHolder = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();

        spRcPortType.getPlayoutInfo(playoutInfoHolder, spRcResultHolder);
        this.getTsoipPars();


        //spRcPortType.closeSession();

        System.out.println("vlah.value = " + spRcResultHolder.value);
        System.out.println("playoutinfo h older.value.getFilename() = " + playoutInfoHolder.value.getFilename().getValue());
        System.out.println("playoutinfo h older.value.getFileType() = " + playoutInfoHolder.value.getFileType());
        System.out.println("playoutinfo h older.value.getPlayoutState() = " + playoutInfoHolder.value.getPlayoutState());
    }


    public List<PortDesc> scanPorts() throws SpRcCommunicationException {
        Holder<PortDescArray> portDescArrayHolder = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();
        spRcPortType.scanPorts(portDescArrayHolder, spRcResultHolder);
        if (spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue()) {
            if (portDescArrayHolder.value != null) {
                if (portDescArrayHolder.value.getItem() != null) {
                    return portDescArrayHolder.value.getItem();
                }
            }
        } else {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt(spRcResultHolder.value.intValue()));
        }

        return null;
    }


    public void playFileOnce(String filename) {
        spRcPortType.openSession();
        spRcPortType.setLoopFlags(LoopingFlags.NONE.getValue());
        spRcPortType.openFile(filename);

        spRcPortType.setPlayoutState(1);
        spRcPortType.waitForCondition(0, -1);
        System.out.println("HERE ");
        spRcPortType.closeSession();

    }


    public void loopFile(String filename) {
        spRcPortType.openSession();
        spRcPortType.setLoopFlags(LoopingFlags.SPRC_LOOP_WRAP.getValue());
        spRcPortType.openFile(filename);
        spRcPortType.setPlayoutState(PlayoutStateFlags.SPRC_STATE_PLAY.getValue());
        spRcPortType.closeSession();
    }

    public void pausePlayout() {
        spRcPortType.openSession();
        spRcPortType.setPlayoutState(PlayoutStateFlags.SPRC_STATE_PAUSE.getValue());
        spRcPortType.closeSession();
    }

    public void stopPlayout() {
        spRcPortType.openSession();
        spRcPortType.setPlayoutState(PlayoutStateFlags.SPRC_STATE_STOP.getValue());
        spRcPortType.closeSession();
    }

    public void startPlayout() {
        spRcPortType.openSession();
        spRcPortType.setPlayoutState(PlayoutStateFlags.SPRC_STATE_PLAY.getValue());
        spRcPortType.closeSession();
    }


    public void setWaitForCondition(int condition, int timeout) throws SpRcCommunicationException {
        long result = spRcPortType.waitForCondition(condition, timeout);
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }
    }

    public void setLoopFlags(int flag) throws SpRcCommunicationException {
        long result = spRcPortType.setLoopFlags(flag);
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }

    }

    public void setPlayoutState(int playoutState) throws SpRcCommunicationException {
        long result = spRcPortType.setPlayoutState(playoutState);
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }
    }


    public void openFile(String filename) throws SpRcCommunicationException {
        long result = spRcPortType.openFile(filename);
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }
    }


    public void getTsoipPars() {
        //spRcPortType.openSession();

        Holder<TsoipPars> tsoioPars = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();
        spRcPortType.getTsoipPars(tsoioPars, spRcResultHolder);

        byte[] ip = tsoioPars.value.getIp();


        String test = convertSignedByteArrayToUnsignedIPString(ip);

        System.out.println("tsoioPars.value.getTxMode() = " + tsoioPars.value.getTxMode());
        System.out.println("test = " + test);
    }


    public long getSerialNumber() throws SpRcCommunicationException {
        List<PortDesc> portDesc = this.scanPorts();
        return portDesc.get(0).getSerial();
    }

    public void setTsoipPars(String filename, int playoutPort, String outputAddress) throws UnknownHostException, SpRcCommunicationException {
        List<PortDesc> portDesc = this.scanPorts();
        this.selectPort(portDesc.get(playoutPort - 1).getSerial(),
                playoutPort,
                ModulationFlags.SPRC_OTYPE_TSOIP.getValue());

        this.openFile(filename);

        byte[] bytes = InetAddress.getByName(outputAddress).getAddress();


        TsoipPars tsoioPars = getDefaultTsoIpPars();
        tsoioPars.setIp(bytes);

        System.out.println("tsoioPars = " + tsoioPars);

        long result = spRcPortType.setTsoipPars(tsoioPars);
        if (!(result == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(result))));
        }
    }


    public TsoipPars getDefaultTsoIpPars() throws UnknownHostException {
        TsoipPars tsoioPars = new TsoipPars();
        tsoioPars.setTxMode(17); //--> check for default
        tsoioPars.setPort(1050);
        tsoioPars.setPort2(5678);
        tsoioPars.setEnaFallover(false);
        tsoioPars.setTimeToLive(32);
        tsoioPars.setNumTpPerIp(7);
        tsoioPars.setProtocol(0);
        tsoioPars.setDiffServ(0);
        tsoioPars.setFecMode(0);
        tsoioPars.setFecNumCols(0);
        tsoioPars.setFecNumRows(0);


        byte[] bytes2 = InetAddress.getByName("127.0.0.1").getAddress();

        tsoioPars.setIp2(bytes2);

        return tsoioPars;
    }

    public void selectPort(long serialNumber, int playoutPort, int modulation) throws SpRcCommunicationException {
        Holder<Long> spRcResultHolder = new Holder<>();
        Holder<Integer> status = new Holder<>();

        spRcPortType.selectPort(serialNumber, playoutPort, modulation, spRcResultHolder, status);
        if (spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue()) {
        } else {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt(spRcResultHolder.value.intValue()));
        }
    }


    public void getCurrentFile() throws SpRcCommunicationException {
        Holder<PlayoutInfo> playoutInfo = new Holder<>();
        Holder<Long> spRcResultHolder = new Holder<>();

        spRcPortType.getPlayoutInfo(playoutInfo, spRcResultHolder);
        if (!(spRcResultHolder.value == ReturnCodeFlags.SPRC_OK.getValue())) {
            throw new SpRcCommunicationException(ReturnCodeFlags.getMessageValueFromInt((toIntExact(spRcResultHolder.value))));
        }

        System.out.println("spRcResultHolder.value = " + spRcResultHolder.value);
        System.out.println("playoutStatus.value = " + playoutInfo.value.getFilename().toString());
        System.out.println("playoutStatus.value2 = " + playoutInfo.value.getFilename().getValue().toString());

    }

    public void clearErrors() {
        spRcPortType.clearErrors();
    }


    public String convertSignedByteArrayToUnsignedIPString(byte[] byteArray) {
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

    public byte[] convertIpStringToUnsignedByteArray(String ipString) throws UnknownHostException {
        byte[] bytes = InetAddress.getByName(ipString).getAddress();

        return bytes;

    }


    public List<Path> getFiles(final String fileDirectory, final String fileExtenstionOfInterest) throws IOException {

        List<Path> filePaths = null;
        try (Stream<Path> filePathStream = Files.list(Paths.get(fileDirectory))) {
            filePaths = filePathStream
                    .filter(Files::isRegularFile)
                    .filter((p) -> p.getFileName().toString().endsWith(fileExtenstionOfInterest))
                    .collect(Collectors.toList());

        }

        return filePaths;
    }


}
*/