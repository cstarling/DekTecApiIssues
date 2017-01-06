import java.util.List;

/**
 * Created by starling on 12/14/16.
 */
public class ListUtils {

    public static Instance containsHostName(List<Instance> list, String hostName) {
        return list.stream().filter(o -> o.getHostname().equals(hostName)).findFirst().orElse(null);
        //return list.stream().filter(o -> o.getHostname().equals(hostName)).findFirst().get();
    }

    public static Instance containsIp(List<Instance> list, String ip) {
        return list.stream().filter(o -> o.getIp().equals(ip)).findFirst().orElse(null);
    }

    public static Instance containsPort(List<Instance> list, String port) {
        return list.stream().filter(o -> o.getInstancePort().equals(port)).findFirst().orElse(null);
    }

    public static Instance containsInstanceNumber(List<Instance> list, String instanceNumber) {
        return list.stream().filter(o -> o.getInstanceNumber().equals(instanceNumber)).findFirst().orElse(null);
    }


}
