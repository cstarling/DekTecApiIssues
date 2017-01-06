import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by starling on 12/12/16.
 */
public class App2 {

    public static final String GO_PRO_STAY_TUNED = "C:\\Users\\Media6-PC\\Desktop\\GoPro_StayTuned.ts";
    public static final String FSGO = "C:\\Users\\Media6-PC\\Desktop\\FSGO_2880gop15.ts";

    public static void main(String[] args) {
        App2 obj = new App2();

        String domainName = "google.com";

        //in mac oxs
        String command = "open -Fa textedit";

        //in windows
        //String command = "ping -n 3 " + domainName;

        String output = obj.executeCommand(command);

        System.out.println(output);


    }


    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}


