import groovy.io.FileType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

class Logger{

    def logFile;

    def Logger(logPath){
        Files.createDirectories(Paths.get(logPath));

        def logFilePath = "$logPath/log.txt";

        if (!Files.exists(Paths.get(logFilePath))) {
            Files.createFile(Paths.get(logFilePath))
        }

        logFile = new File(logFilePath);
    }

    def clearLog(){
        logFile.text = "";
    }

    def logMessage(message, logtype){
        
        // Get the current date and time in the user's timezone
        def currentDateTime = LocalDateTime.now(ZoneId.systemDefault());

        // Define the date and time format
        def formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the date and time
        def formattedDateTime = currentDateTime.format(formatter);

        // Append message to log file
        logFile.text += "$formattedDateTime [${logtype}] > ${message}\n";

    }
}