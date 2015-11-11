package net.petitviolet.monad.maybe;

import net.petitviolet.monad.Monad;
import net.petitviolet.monad.func.Function;

class None<A> extends Maybe<A> {

    None() {
    }

    @Override
    public String toString() {
        return "None{}";
    }

    @Override
    public A get() {
        return null;
    }

    @Override
    public A getOrElse(A defaultValue) {
        return defaultValue;
    }

    @Override
    public void foreach(Function.F<? super A> func) {
    }

    @Override
    public Maybe<A> filter(Function.F1<? super A, Boolean> func) {
        return this;
    }

    @Override
    public <B> Monad<B, Maybe<?>> flatMap(Function.F1<A, ? extends Monad<B, Maybe<?>>> f) {
        return new None<>();
    }
}