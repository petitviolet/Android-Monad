package net.petitviolet.monad.maybe;

import android.support.annotation.Nullable;

import net.petitviolet.monad.func.Function;

abstract public class Maybe<A> {
    abstract public A get();

    public A getOrElse(A defaultValue) {
        return isPresent() ? get() : defaultValue;
    }

    abstract public <B> Maybe<B> map(Function.F1<? super A, ? extends B> func);

    abstract public <B> Maybe<B> flatMap(Function.F1<? super A, ? extends Maybe<B>> func);

    abstract public boolean isPresent();

    abstract public Maybe<A> filter(final Function.F1<? super A, Boolean> func);

    public static <A> Maybe<A> of(@Nullable A value) {
        if (value != null) {
            return new Just<>(value);
        } else {
            return new None<>();
        }
    }
}