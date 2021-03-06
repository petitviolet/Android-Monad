package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

public class Identity<A> implements Monad<A, Identity<?>> {
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
    public <B> Identity<B> flatMap(Function.F1<? super A, ? extends Identity<?>> f) {
        return (Identity<B>) f.invoke(value);
    }

    @Override
    public <B> Identity<B> map(Function.F1<? super A, ? extends B> func) {
        return Identity.id(func.invoke(value));
    }
}