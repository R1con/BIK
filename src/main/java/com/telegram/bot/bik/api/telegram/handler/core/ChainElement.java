package com.telegram.bot.bik.api.telegram.handler.core;

import java.util.List;

public interface ChainElement<T> {
    void setNext(T next);

    static <T extends ChainElement<T>> T buildChain(List<T> elements, T lastElement) {
        if (elements.isEmpty()) {
            return lastElement;
        } else {
            for(int i = 0; i < elements.size(); ++i) {
                T current = elements.get(i);
                T next = i < elements.size() - 1 ? elements.get(i + 1) : lastElement;
                current.setNext(next);
            }

            return elements.get(0);
        }
    }
}
