package net.petitviolet.monad.func;

public interface Function {
    interface F<T> extends Function {
        void call(T t);
    }

    interface F0<R> extends Function {
        R call();
    }

    interface F1<T, R> extends Function {
        R call(T t);
    }
}


