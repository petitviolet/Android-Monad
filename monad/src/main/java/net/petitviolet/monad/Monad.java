package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

public interface Monad<A> {
    <B> Monad<B> map(Function.F1<? super A, B> func);

    <B> Monad<B> flatMap(Function.F1<? super A, Monad<B>> func);
}
