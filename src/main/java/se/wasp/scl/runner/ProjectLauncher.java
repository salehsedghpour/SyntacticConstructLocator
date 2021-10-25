package se.wasp.scl.runner;

import se.wasp.scl.processor.*;
import se.wasp.scl.util.SyntacticEnum;
import spoon.Launcher;
import spoon.reflect.CtModel;

import java.util.List;
import java.util.logging.Logger;

public class ProjectLauncher {

    private Launcher launcher = new Launcher();
    private SyntacticEnum syntacticConstruct;
    private CtModel model;

    private final static Logger LOGGER = Logger.getLogger(ProjectLauncher.class.getName());

    ProjectLauncher(String path, SyntacticEnum syntacticConstruct) {
        LOGGER.info(String.format("Processing %s", path));
        this.syntacticConstruct = syntacticConstruct;
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.addInputResource(path);
        launcher.buildModel();
        model = launcher.getModel();
    }
    public List<String> processModel() {
        if (syntacticConstruct.equals(SyntacticEnum.ASSERTION)) {
            AssertionProcessor assertionProcessor = new AssertionProcessor();
            model.processWith(assertionProcessor);
            return assertionProcessor.getAssertionFound();
        } else if (syntacticConstruct.equals(SyntacticEnum.FLOW_BREAK)) {
            FlowBreakProcessor flowBreakProcessor = new FlowBreakProcessor();
            model.processWith(flowBreakProcessor);
            return flowBreakProcessor.getFlowBreakFound();
        } else if (syntacticConstruct.equals(SyntacticEnum.IF)) {
            IfProcessor ifProcessor = new IfProcessor();
            model.processWith(ifProcessor);
            return ifProcessor.getIfFound();
        } else if (syntacticConstruct.equals(SyntacticEnum.SWITCH)) {
            SwitchProcessor switchProcessor = new SwitchProcessor();
            model.processWith(switchProcessor);
            return switchProcessor.getSwitchFound();
        } else if (syntacticConstruct.equals(SyntacticEnum.SYNCHRONIZED)){
            SynchronizedProcessor synchronizedProcessor = new SynchronizedProcessor();
            model.processWith(synchronizedProcessor);
            return synchronizedProcessor.getSynchronizedFound();
        } else if (syntacticConstruct.equals(SyntacticEnum.TRY)) {
            TryProcessor tryProcessor = new TryProcessor();
            model.processWith(tryProcessor);
            return tryProcessor.getTryFound();
        } else {
            LoopProcessor loopProcessor = new LoopProcessor();
            model.processWith(loopProcessor);
            return loopProcessor.getLoopssFound();
        }
    }
}
