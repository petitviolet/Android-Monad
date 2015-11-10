package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

public interface Functor<A> {
    <B> Functor<B> map(Function.F1<? super A, ? extends B> func);
}