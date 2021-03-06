package net.petitviolet.monad.maybe;

import android.support.annotation.Nullable;

import net.petitviolet.monad.func.Function;

/**
 * Maybe monad
 * null = None
 * some value = Just
 * @param <A>
 */
abstract public class Maybe<A> {
// abstract public class Maybe<A> { //implements Monad<A,Maybe<?>> {
    // if implements Monad interface, type inference does not work properly...

    @Override
    public boolean equals(Object object) {
        if (object instanceof Just && this instanceof Just) {
            return ((Maybe) object).get().equals(get());
        } else if (object instanceof None && this instanceof None) {
            return true;
        } else {
            return false;
        }
    }

    abstract public A get();

    public A getOrElse(A defaultValue) {
        return isPresent() ? get() : defaultValue;
    }

    abstract public void foreach(Function.F<? super A> func);

    public <B> Maybe<B> map(final Function.F1<? super A, ? extends B> func) {
        return flatMap(new Function.F1<A, Maybe<B>>() {
            @Override
            public Maybe<B> invoke(A a) {
                return Maybe.of(func.invoke(getOrElse(null)));
            }
        });
    }

    abstract public <B> Maybe<B> flatMap(Function.F1<? super A, ? extends Maybe<B>> func);

    public boolean isPresent() {
        return this instanceof Just;
    }

    abstract public Maybe<A> filter(final Function.F1<? super A, Boolean> func);

    public static <A> Maybe<A> of(@Nullable A value) {
        if (value != null) {
            return new Just<>(value);
        } else {
            return new None<>();
        }
    }

    public static <A> Maybe<A> none() {
        return new None<>();
    }
}