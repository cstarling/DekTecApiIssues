import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebServiceException;
import java.util.List;

@XmlRootElement
public class StreamExpressInstances {

    private List<Instance> instances;

    private String streamXpressResourcefile;


    private String FILE_LOCATION;

    @XmlElement(name = "instances")
    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    @Override
    public String toString() {
        return "ClassPojo instances = " + instances + "]";
    }


    public void checkIfInstanceIsUp(Instance instance) throws SpRcCommunicationException, WebServiceException {
        try {
            instance.getSpRcService().openSession();
        } finally {
            instance.getSpRcService().closeSession();
        }
    }


}
