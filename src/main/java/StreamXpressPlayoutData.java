
import SpRcFlags.LoopingFlags;
import SpRcFlags.PlayoutStateFlags;
import sprc.PlayoutInfo;
import sprc.PortDesc;
import sprc.TsoipPars;

/**
 * Created by starling on 12/18/16.
 */
public class StreamXpressPlayoutData {
    private String filename;
    private String outputIp;
    private int outputIpPort;
    private int physicalOutputPort;
    private String playoutState;
    private String loopingState;
    private String instanceNumber;
    private String instancePort;
    private boolean reachable;

    public StreamXpressPlayoutData() {
    }

    public StreamXpressPlayoutData(Instance instance) {
        this.instanceNumber = instance.getInstanceNumber();
        this.instancePort = instance.getInstancePort();
        this.filename = instance.getFileToStartUpWith();
        this.outputIp = instance.getInitalMulticastAddress();
        this.outputIpPort = instance.getInitalMulticastPort();

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOutputIp() {
        return outputIp;
    }

    public void setOutputIp(String outputIp) {
        this.outputIp = outputIp;
    }

    public int getOutputIpPort() {
        return outputIpPort;
    }

    public void setOutputIpPort(int outputIpPort) {
        this.outputIpPort = outputIpPort;
    }

    public int getPhysicalOutputPort() {
        return physicalOutputPort;
    }

    public void setPhysicalOutputPort(int physicalOutputPort) {
        this.physicalOutputPort = physicalOutputPort;
    }

    public String getPlayoutState() {
        return playoutState;
    }

    public void setPlayoutState(String playoutState) {
        this.playoutState = playoutState;
    }

    public String getLoopingState() {
        return loopingState;
    }

    public void setLoopingState(String loopingState) {
        this.loopingState = loopingState;
    }

    public String getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(String instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public String getInstancePort() {
        return instancePort;
    }

    public void setInstancePort(String instancePort) {
        this.instancePort = instancePort;
    }

    public void setActivePortFields(PortDesc activePort) {
        //physical output port
        setPhysicalOutputPort(activePort.getPort());
    }

    public void setPlayoutInfoFields(PlayoutInfo playoutInfo) {
        //file name
        //looping state
        // playout state
        String fullFilename = playoutInfo.getFilename().getValue().toString();
        String finalFilename = fullFilename.substring(fullFilename.lastIndexOf("\\") + 1, fullFilename.length());
        setFilename(finalFilename);

        if (playoutInfo.getLoopFlags() >= LoopingFlags.SPRC_LOOP_WRAP.getValue()) {
            setLoopingState("LOOPING");
        } else {
            setLoopingState("NOT LOOPING");
        }
        if (playoutInfo.getPlayoutState() == PlayoutStateFlags.SPRC_STATE_PAUSE.getValue() || playoutInfo.getPlayoutState() == PlayoutStateFlags.SPRC_STATE_STOP.getValue()) {
            setPlayoutState("STOPPED");
        } else if (playoutInfo.getPlayoutState() == PlayoutStateFlags.SPRC_STATE_PLAY.getValue()) {
            setPlayoutState("PLAYING");
        }
    }

    public void setTsipFields(TsoipPars tsipPars) {
        //port
        //ip
        setOutputIp(ConversionUtils.convertSignedByteArrayToUnsignedIPString(tsipPars.getIp()));
        setOutputIpPort(tsipPars.getPort());

    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }


    public void setUnreachableValues() {
        this.filename = "NOT REACHABLE";
        this.outputIp = "NOT REACHABLE";
        this.outputIpPort = 0;
        this.playoutState = "NOT REACHABLE";
    }

    @Override
    public String toString() {
        return "StreamXpressPlayoutData{" +
                "filename='" + filename + '\'' +
                ", outputIp='" + outputIp + '\'' +
                ", outputIpPort=" + outputIpPort +
                ", physicalOutputPort=" + physicalOutputPort +
                ", playoutState='" + playoutState + '\'' +
                ", loopingState='" + loopingState + '\'' +
                ", instanceNumber='" + instanceNumber + '\'' +
                ", instancePort='" + instancePort + '\'' +
                ", reachable=" + reachable +
                '}';
    }
}
