package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

public abstract class Functor<A, F extends Functor<?, ?>> {
    abstract public <B> Functor<B, F> fmap(Function.F1<A, B> f);
}