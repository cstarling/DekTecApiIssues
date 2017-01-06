import SpRcFlags.ModulationFlags;
import sprc.PlayoutInfo;
import sprc.PortDesc;
import sprc.TsoipPars;

import javax.xml.ws.WebServiceException;
import java.net.ConnectException;

/**
 * Created by starling on 1/4/17.
 */

public class StreamXpressService {


    public StreamXpressPlayoutData getInstanceData(Instance instance) {
        PortDesc activePort = null;
        PlayoutInfo playoutInfo = null;
        StreamXpressPlayoutData streamXpressPlayoutData = new StreamXpressPlayoutData();
        try {
            activePort = instance.getSpRcService().getActivePort();
            System.out.println("activePort = " + activePort);
            //streamXpressPlayoutData.setActivePortFields(activePort);
            playoutInfo = instance.getSpRcService().getPlayoutInfo();
            streamXpressPlayoutData.setPlayoutInfoFields(playoutInfo);
            if (activePort.getOutputType() == ModulationFlags.SPRC_OTYPE_TSOIP.getValue()) {
                //then we have an TS IP ouutput
                TsoipPars tsiopars = instance.getSpRcService().getTsoipPars();
                streamXpressPlayoutData.setTsipFields(tsiopars);
            }
            streamXpressPlayoutData.setReachable(true);
        } catch (SpRcCommunicationException e) {
            e.printStackTrace();
        } catch (WebServiceException e) {
            if (e.getCause() instanceof ConnectException) {
                //log someething here
            }
            streamXpressPlayoutData.setReachable(false);
            streamXpressPlayoutData.setUnreachableValues();
        }
        streamXpressPlayoutData.setPhysicalOutputPort(instance.getSelectedPhysicalPort());
        streamXpressPlayoutData.setInstanceNumber(instance.getInstanceNumber());
        streamXpressPlayoutData.setInstancePort(instance.getInstancePort());
        return streamXpressPlayoutData;
    }
}
