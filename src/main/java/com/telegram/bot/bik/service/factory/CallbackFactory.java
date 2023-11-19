package com.telegram.bot.bik.service.factory;

import com.telegram.bot.bik.service.callback.HandleCallback;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CallbackFactory {
    private static final Map<String, HandleCallback> CALLBACKS = new HashMap<>();

    public CallbackFactory(List<HandleCallback> callbacks) {
        callbacks.forEach(handler -> CALLBACKS.put(handler.getSupportedCallback().toString(), handler));
    }

    public HandleCallback getCallback(String keyCallback) {
        return CALLBACKS.get(keyCallback);
    }

}
