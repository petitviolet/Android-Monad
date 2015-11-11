package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

import java.util.Collection;
import java.util.Collections;

public class Identity<A> extends Monad<A, Identity<?>> {
    public final A value;

    public Identity(A value) {
        this.value = value;
    }

    public static <A> Identity<A> id(A value) {
        return new Identity<>(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Identity) {
            return ((Identity) o).value.equals(value);
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "Identity{" + "value=" + value + '}';
    }

    @Override
    public <B> Identity<B> unit(B a) {
        return id(a);
    }

    @Override
    public <B> Identity<B> flatMap(Function.F1<A, ? extends Monad<B, Identity<?>>> f) {
        return (Identity<B>) f.invoke(value);
    }

    @Override
    public A get() {
        return value;
    }

}