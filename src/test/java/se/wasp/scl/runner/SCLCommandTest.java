package se.wasp.scl.runner;

import org.junit.jupiter.api.Test;
import se.wasp.scl.util.SyntacticEnum;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class SCLCommandTest {

    static ProjectLauncher launcher;
    static String projectPath = Thread.currentThread().getContextClassLoader().getResource("NeuronString.java").getPath();

    @Test
    public void testThatLoopsAreFound() {
        launcher = new ProjectLauncher(projectPath, SyntacticEnum.LOOP);
        List<String> loopsFound = launcher.processModel();
        assertEquals(6, loopsFound.size(), "There are 6 loop constructs in NeuronString.java");
    }

    @Test
    public void testThatIfAreFound() {
        launcher = new ProjectLauncher(projectPath, SyntacticEnum.IF);
        List<String> ifFound = launcher.processModel();
        assertEquals(4, ifFound.size(), "There are 4 if constructs in NeuronString.java");
    }

    @Test
    public void testThatAssertAreFound() {
        launcher = new ProjectLauncher(projectPath, SyntacticEnum.ASSERTION);
        List<String> assertFound = launcher.processModel();
        assertEquals(1, assertFound.size(), "There are 1 assert construct in NeuronString.java");
    }

    @Test
    public void testThatSwitchAreFound() {
        launcher = new ProjectLauncher(projectPath, SyntacticEnum.SWITCH);
        List<String> switchFound = launcher.processModel();
        assertEquals(1, switchFound.size(), "There are 1 switch construct in NeuronString.java");
    }

    @Test
    public void testThatSynchronizedAreFound() {
        launcher = new ProjectLauncher(projectPath, SyntacticEnum.SYNCHRONIZED);
        List<String> synchronizedFound = launcher.processModel();
        assertEquals(1, synchronizedFound.size(), "There are 1 synchronized construct in NeuronString.java");
    }

    @Test
    public void testThatFlowBreakAreFound() {
        launcher = new ProjectLauncher(projectPath, SyntacticEnum.FLOW_BREAK);
        List<String> flowBreakFound = launcher.processModel();
        assertEquals(10, flowBreakFound.size(), "There are 10 flow break constructs in NeuronString.java");
    }

    @Test
    public void testThatTryAreFound() {
        launcher = new ProjectLauncher(projectPath, SyntacticEnum.TRY);
        List<String> tryFound = launcher.processModel();
        assertEquals(1, tryFound.size(), "There are 1 try constructs in NeuronString.java");
    }
}
