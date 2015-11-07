package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

public interface Monad<A, M extends Monad<?, ?>> extends Functor<A, M> {
    <B> M flatMap(Function.F1<? super A, ? extends M> f);
}
