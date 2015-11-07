package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

public interface Functor<A, F extends Functor<?, ?>> {
    <B> F map(Function.F1<? super A, ? extends B> func);
}