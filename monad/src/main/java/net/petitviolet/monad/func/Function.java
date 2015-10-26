package net.petitviolet.monad.func;

public interface Function {
    interface F<T> extends Function {
        void invoke(T t);
    }

    interface F0<R> extends Function {
        R invoke();
    }

    interface F1<T, R> extends Function {
        R invoke(T t);
    }
}


