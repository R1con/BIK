package com.telegram.bot.bik.service.factory;

import com.telegram.bot.bik.api.telegram.commands.HandleCommand;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandFactory {
    private static final Map<String, HandleCommand> COMMANDS = new HashMap<>();

    public CommandFactory(List<HandleCommand> commands) {
        commands.forEach(handler -> COMMANDS.put(handler.getSupportedCommand(), handler));
    }

    public HandleCommand getCommand(String keyCommand) {
        return COMMANDS.get(keyCommand);
    }
}
