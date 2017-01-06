import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by starling on 12/17/16.
 */
public class OperatingSystemUtils {
    public static List<String> getFiles(final String fileDirectory, final String fileExtenstionOfInterest) throws IOException {

        List<String> filePaths = null;
        try (Stream<Path> filePathStream = Files.list(Paths.get(fileDirectory))) {
            filePaths = filePathStream
                    .filter(Files::isRegularFile)
                    .filter((p) -> p.getFileName().toString().endsWith(fileExtenstionOfInterest))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            /*
            .forEach(filePath -> {
                        System.out.println(filePath.getFileName());
                    })
             */
        }
        return filePaths;
    }


    public static void startInstance(Instance instance) {
        String line = "open -n -a TextEdit";
        //String line = "C:\\Program Files (x86)\\DekTec\\StreamXpress\\StreamXpress.exe";
        //String line = "C:\\Program Files (x86)\\DekTec\\StreamXpress\\StreamXpress.exe -rc " + instance.getInstancePort();

        CommandLine commandLine = CommandLine.parse(line);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(0);
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        //ExecuteWatchdog watchdog = new ExecuteWatchdog(5000);
        //executor.setWatchdog(watchdog);
        try {
            executor.execute(commandLine, resultHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
