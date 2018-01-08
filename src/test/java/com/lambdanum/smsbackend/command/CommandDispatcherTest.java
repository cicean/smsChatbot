package com.lambdanum.smsbackend.command;

import com.lambdanum.smsbackend.command.tree.ReservedKeyWordConverter;
import com.lambdanum.smsbackend.nlp.TokenizerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CommandDispatcherTest {

    private CommandDispatcher commandDispatcher;

    @Mock
    private TokenizerService tokenizerService;

    @Mock
    private ApplicationContext applicationContext;

    CommandListenerMock commandListenerMock = new CommandListenerMock();

    ReservedKeyWordConverterMock tokenConverterMock = new ReservedKeyWordConverterMock();

    public static final String COMMAND_WITHOUT_PARAMETER = "hello foo bar";
    public static final String COMMAND_WITHOUT_PARAMETER_RESULT = "COMMANDWITHOUDPARAMETER";

    public static final String COMMAND_WITH_PARAMETER = "foo <token> bar";

    public static final String COMMAND_WITH_ARRAY_PARAMETER = "array <token> ... stop";

    public static final String COMMAND_WITH_CONTEXT_PARAMETER = "test <token> context";
    public static final String CONVERSATIONAL_COMMAND = "thanks";

    @Before
    public void initialize() {
        Map<String, Object> commandListeners = new HashMap<>();
        commandListeners.put("commandListenerMock",commandListenerMock);

        when(applicationContext.getBeansWithAnnotation(eq(CommandListener.class))).thenReturn(commandListeners);

        when(tokenizerService.tokenizeAndStem(any())).then(i -> Arrays.asList(((String)i.getArguments()[0]).split(" ")));

        List<ReservedKeyWordConverter> tokenConverters = Arrays.asList(tokenConverterMock);

        commandDispatcher = new CommandDispatcher(applicationContext, tokenConverters, tokenizerService);

    }

    @Test
    public void givenCommandWithoutParameters_whenExecutingCommand_thenReturnCommandResult() {

        Object result = commandDispatcher.executeCommand(COMMAND_WITHOUT_PARAMETER);

        assertEquals(COMMAND_WITHOUT_PARAMETER_RESULT, result);
    }

    @Test(expected = IllegalStateException.class)
    public void givenTwoIdenticalCommands_whenConstructingDecisionTree_thenThrowIllegalArgumentException() {
        Map<String, Object> duplicatedCommandListeners = new HashMap<>();
        duplicatedCommandListeners.put("1", commandListenerMock);
        duplicatedCommandListeners.put("2", commandListenerMock);
        when(applicationContext.getBeansWithAnnotation(eq(CommandListener.class))).thenReturn(duplicatedCommandListeners);

        commandDispatcher = new CommandDispatcher(applicationContext, Arrays.asList(tokenConverterMock), tokenizerService);
    }

    @Test
    public void givenCommandWithParameter_whenExecutingCommand_thenReturnMethodCallWithParameter() {

        Object result = commandDispatcher.executeCommand("foo token bar");

        assertEquals("token",result);
    }

    @Test
    public void givenCommandWithArrayParameter_whenExecutingCommand_thenReturnMethodCallWithParameter() {

        Object result = commandDispatcher.executeCommand("array token token token stop");

        assertEquals(Arrays.asList("token","token","token"),result);
    }

    @Test(expected = UnknownCommandException.class)
    public void givenUnknownCommand_whenExecutingCommand_thenThrowIllegalArgumentException() {

        commandDispatcher.executeCommand("unknowncommandisunknown");
    }

    @Test
    public void givenContextualCommand_whenExecutingCommand_thenReturnMethodCallWithCommandContext() {

        Object result = commandDispatcher.executeCommand("test token context");

        assertTrue(result instanceof CommandContext);
        assertTrue(((CommandContext)result).getArgs()[0].equals("token"));
    }

    @Test(expected = IllegalStateException.class)
    public void givenMultipleIdenticalTokenConverterDefinitions_whenConstructingDecisionTree_thenThrowIllegalStateException() {
        commandDispatcher = new CommandDispatcher(applicationContext, Arrays.asList(tokenConverterMock, tokenConverterMock), tokenizerService);
    }

}

@CommandListener
class CommandListenerMock {

    @CommandHandler(CommandDispatcherTest.COMMAND_WITHOUT_PARAMETER)
    public String commandWithoutParameters() {
        return CommandDispatcherTest.COMMAND_WITHOUT_PARAMETER_RESULT;
    }

    @CommandHandler(CommandDispatcherTest.COMMAND_WITH_PARAMETER)
    public String commandWithParameter(String value) {
        return value;
    }

    @CommandHandler(CommandDispatcherTest.COMMAND_WITH_ARRAY_PARAMETER)
    public List<String> commandWithArrayParameter(List<String> args) {
        return args;
    }

    @CommandHandler(CommandDispatcherTest.COMMAND_WITH_CONTEXT_PARAMETER)
    public CommandContext commandWithContext(CommandContext context, String token) {
        return context;
    }

    @Conversational(CommandListenerMock.class)
    @CommandHandler(CommandDispatcherTest.CONVERSATIONAL_COMMAND)
    public String conversationalCommand() {
        return "thanks";
    }

}

class ReservedKeyWordConverterMock implements ReservedKeyWordConverter {

    @Override
    public boolean isMatching(String value) {
        return value.equals("token");
    }

    @Override
    public Object getMatchingObject(String value) {
        return "token";
    }

    @Override
    public String getMatchedToken() {
        return "<token>";
    }
}


class TokenizerServiceMock extends TokenizerService {
    @Override
    public List<String> tokenizeAndStem(String command) {
        return Arrays.asList(command.split(" "));
    }
}
