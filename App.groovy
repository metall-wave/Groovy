class App{

    static void main(String[] args){
        def directoryPath = args[0];
        def textPattern = args[1];
        def replacementText = args[2];

        def logger;

        //create logger
        if(args.length > 3){
            def logPath = args[3];
            logger = new Logger(logPath);
        }

        //create replacer object
        def replacer = new Replacer(directoryPath, logger);

        //replace pattern with text
        replacer.replaceText(textPattern, replacementText);
    }

}