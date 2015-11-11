package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

public abstract class Applicative<A, AP extends Applicative<?, ?>> extends Functor<A, AP> {
    public abstract A get();

    protected abstract <B> Applicative<B, AP> pure(B value);

    protected final <B> Applicative<B, AP> apply(final Applicative<Function.F1<A, B>, AP> af) {
        Function.F1<A, B> fab = af.get();
        return pure(fab.invoke(get()));
    }
}