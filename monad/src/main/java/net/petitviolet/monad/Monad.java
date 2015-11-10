package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

import java.util.Collection;

public interface Monad<A> extends Functor<A> {
    <B> Monad<B> flatMap(Function.F1<? super A, ? extends Monad<B>> f);

    Collection<A> flatten();
}
