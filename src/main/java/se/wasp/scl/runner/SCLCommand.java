package se.wasp.scl.runner;

import org.apache.commons.csv.CSVFormat;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import se.wasp.scl.processor.ASTFilter;
import se.wasp.scl.util.CSVHeadersEnum;
import se.wasp.scl.util.OutputType;
import se.wasp.scl.util.SyntacticEnum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

@Command(name = "scl", mixinStandardHelpOptions = true, version = "1.0",
         description = "Locates instances of given syntactic constucts")
public class SCLCommand implements Callable<Integer> {
    @Option(names = {"-i", "--input"}, defaultValue = "", arity = "0..1", description = "The path to the .csv file to analyze.")
    private static String parsedASTPath;

    private static String parsedAST;

    @Option(names = {"-l", "--locate"}, defaultValue = "loop", arity = "0..1", description = "Locate specified syntactic construct: loop (default), if, assertion, switch, synchronized, flow_break, try")
    private String inputSyntacticConstruct;
    private SyntacticEnum syntactic;

    @Option(names = {"-f", "--format"}, defaultValue = "table", arity = "0..1", description = "Locate specified syntactic construct: loop (default), if, assertion, switch, synchronized, flow_break, try")
    private String inputOutputType;
    private OutputType outputType;

    private final static Logger LOGGER = Logger.getLogger(SCLCommand.class.getName());

    private void setSyntactic() {
        if (inputSyntacticConstruct != null){
            switch (inputSyntacticConstruct.toLowerCase()){
                case "loop":
                    syntactic = SyntacticEnum.LOOP;
                    break;
                case "if":
                    syntactic = SyntacticEnum.IF;
                    break;
                case "assertion":
                    syntactic = SyntacticEnum.ASSERTION;
                    break;
                case "switch":
                    syntactic = SyntacticEnum.SWITCH;
                    break;
                case "synchronized":
                    syntactic = SyntacticEnum.SYNCHRONIZED;
                    break;
                case "flow_break":
                    syntactic = SyntacticEnum.FLOW_BREAK;
                    break;
                case "try":
                    syntactic = SyntacticEnum.TRY;
                    break;
                default:
                    syntactic = SyntacticEnum.NONE;
                    break;
            }
        } else {
            syntactic = SyntacticEnum.NONE;
        }

    }

    private void setOutputType(){
        outputType = inputOutputType.equalsIgnoreCase("table") ?
                OutputType.TABLE : OutputType.CSV;
    }

    @Override
    public Integer call() throws Exception {
        setSyntactic();
        setOutputType();
        if (parsedASTPath.length()> 0) {
            Reader in = new FileReader(parsedASTPath);
            BufferedReader br=new BufferedReader(in);
            StringBuffer sb=new StringBuffer();
            String line;
            while((line=br.readLine())!=null)
            {
                sb.append(line);      //appends line to string buffer
                sb.append("\n");     //line feed
            }
            in.close();
            parsedAST = sb.toString();
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            parsedAST = content.toString();
        }

        if (parsedAST.length() > 0) {
            LOGGER.info(String.format("Working with the parsed AST piped in from the CLI"));
            ASTFilter.filterCLIInput(parsedAST, syntactic, outputType);
        }
        return 0;
    }
    public static void main(String[] args) throws Exception {
        int exitCode = new CommandLine(new SCLCommand()).execute(args);
        System.exit(exitCode);
    }
}
