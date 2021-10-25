package se.wasp.scl.runner;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import se.wasp.scl.util.SyntacticEnum;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

@Command(name = "scl", mixinStandardHelpOptions = true, version = "1.0",
         description = "Locates instances of given syntactic constucts")
public class SCLCommand implements Callable<Integer> {
    @Parameters(index = "0", description = "The path to the .java file to analyze.")
    private String projectPath;
    @Option(names = {"-l", "--locate"}, fallbackValue = "loop", arity = "0..1", description = "Locate specified syntactic construct: loop (default), if, assertion, switch, synchronized, flow_break, try")
    private String inputSyntacticConstruct;
    private SyntacticEnum syntactic;

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

    @Override
    public Integer call() throws Exception {
        if (!projectPath.endsWith(".java")) {
            System.out.println("Please provide a Java source file (.java). Try --help for help.");
            return 1;
        }
        setSyntactic();
        if (inputSyntacticConstruct != null) {
            LOGGER.info(String.format("You've chosen to find all %s constructs that are in %s",
                    syntactic, projectPath));
        }

        ProjectLauncher launcher = new ProjectLauncher(projectPath, syntactic);
        List<String> constructsFound = launcher.processModel();
        LOGGER.info(String.format("Found %s result(s)", constructsFound.size()));
        System.out.println(constructsFound);

        return 0;
    }
    public static void main(String[] args) {
        int exitCode = new CommandLine(new SCLCommand()).execute(args);
        System.exit(exitCode);
    }
}
