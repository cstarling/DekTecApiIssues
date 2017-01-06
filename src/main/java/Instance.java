import javax.xml.bind.annotation.XmlTransient;

public class Instance {
    private String hostname;
    private String ip;
    private String instanceNumber;
    private int selectedPhysicalPort;
    private String instancePort;
    private String initalMulticastAddress;
    private int initalMulticastPort;
    private int startInstanceOnStartUp;
    private String fileToStartUpWith;

    @XmlTransient
    private SpRcService spRcService;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(String instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public SpRcService getSpRcService() {
        return spRcService;
    }

    public void setSpRcService(SpRcService spRcService) {
        this.spRcService = spRcService;
    }

    public void setSpRcService(String ip, String port) {
        this.spRcService = new SpRcService(ip, port);
    }

    public void setSpRcService() {
        this.spRcService = new SpRcService(this.ip, this.instancePort);
    }

    public int getSelectedPhysicalPort() {
        return selectedPhysicalPort;
    }

    public void setSelectedPhysicalPort(int selectedPhysicalPort) {
        this.selectedPhysicalPort = selectedPhysicalPort;
    }

    public String getInstancePort() {
        return instancePort;
    }

    public void setInstancePort(String instancePort) {
        this.instancePort = instancePort;
    }

    public String getInitalMulticastAddress() {
        return initalMulticastAddress;
    }

    public void setInitalMulticastAddress(String initalMulticastAddress) {
        this.initalMulticastAddress = initalMulticastAddress;
    }

    public int getInitalMulticastPort() {
        return initalMulticastPort;
    }

    public void setInitalMulticastPort(int initalMulticastPort) {
        this.initalMulticastPort = initalMulticastPort;
    }

    public int getStartInstanceOnStartUp() {
        return startInstanceOnStartUp;
    }

    public void setStartInstanceOnStartUp(int startInstanceOnStartUp) {
        this.startInstanceOnStartUp = startInstanceOnStartUp;
    }

    public String getFileToStartUpWith() {
        return fileToStartUpWith;
    }

    public void setFileToStartUpWith(String fileToStartUpWith) {
        this.fileToStartUpWith = fileToStartUpWith;
    }
/*
    @Override
    public String toString() {
        return "Instance{" +
                "hostname='" + hostname + '\'' +
                ", ip='" + ip + '\'' +
                ", instanceNumber='" + instanceNumber + '\'' +
                ", selectedPhysicalPort=" + selectedPhysicalPort +
                ", instancePort='" + instancePort + '\'' +
                ", initalMulticastAddress='" + initalMulticastAddress + '\'' +
                ", initalMulticastPort='" + initalMulticastPort + '\'' +
                ", startInstanceOnStartUp=" + startInstanceOnStartUp +
                ", fileToStartUpWith='" + fileToStartUpWith + '\'' +
                ", spRcService=" + spRcService +
                '}';
    }
    */
}