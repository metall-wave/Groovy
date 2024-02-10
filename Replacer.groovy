import groovy.io.FileType;
import java.nio.file.Files;
import java.nio.file.Paths;

class Replacer{

    def logger;

    //directory containing text files to modify
    def directoryPath;

    //directory containing backup files
    def backupDirectory = "./backup";

    def Replacer(directoryPath, logger){
        Files.createDirectories(Paths.get(backupDirectory));
        this.directoryPath = directoryPath;
        this.logger = logger;
    }

    //replaces all occurences of a pattern in a text with the specified replacement string
    def replaceText(textPattern, replacementText){
        logMessage("Starting...", LogType.Info);

        try{
            def files = [];

            def dir = new File(directoryPath);

            logMessage("Retrieving files from \"${this.directoryPath}\"", LogType.Info);

            //get all files in the text directory
            dir.eachFileRecurse (FileType.FILES) { file ->
                files << file
            }

            def logMessages = "";

            def modificationCounter = 0;

            logMessage("${files.size()} file(s) found.", LogType.Info);

            if(!files.isEmpty()){
                logMessage("Searching for pattern in files...", LogType.Info);

                for(file in files){
                    if(file.text.contains(textPattern)){
                        backupFile(file);

                        def newText = file.text.replace(textPattern, replacementText);
                        file.text = newText;

                        logMessage("Modified ${file}", LogType.Info);
                        modificationCounter++;
                    }
                }
            }

            if(modificationCounter <= 0){
                logMessage("No files matched the pattern \"${textPattern}\"", LogType.Info);
            }
        }catch(java.io.FileNotFoundException ex){
            def errorMessage = StringBuilder.newInstance();
            errorMessage << ex.toString();
            // errorMessage << ex.getMessage();
            // errorMessage << ex.getStackTrace();
            
            logMessage(errorMessage, LogType.Error);
        }

        logMessage("Finished running.", LogType.Info);
    }

    //create backup of a file in the backup directory
    def backupFile(file){
        def backupFilePath = "${backupDirectory}/${file.name}";
        
        logMessage("Backing up \"${file.name}\" to \"${backupFilePath}\".", LogType.Info);

        if (!Files.exists(Paths.get(backupFilePath))) {
            Files.createFile(Paths.get(backupFilePath))
        }

        def backupFile = new File(backupFilePath);

        backupFile.text = file.text;
    }

    //append log message to the log file
    def logMessage(message, logType){
        if(!logger){
            return;
        }

        logger.logMessage(message, logType);
    }
}