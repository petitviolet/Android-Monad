package net.petitviolet.monad.func;

public interface Function {
    interface F<A> extends Function {
        void invoke(A a);
    }

    interface F0<A> extends Function {
        A invoke();
    }

    interface F1<A, B> extends Function {
        B invoke(A a);
    }
    interface F2<A, B, C> extends Function {
        C invoke(A a, B b);
    }
}


