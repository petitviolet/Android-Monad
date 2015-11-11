package net.petitviolet.monad;

import net.petitviolet.monad.func.Function;

@SuppressWarnings("unchecked")
public abstract class Monad<A, M extends Monad<?, ?>> extends Applicative<A, M> {
    @Override
    public <B> Functor<B, M> fmap(Function.F1<A, B> f) {
        return unit(f.invoke(get()));
    }

    abstract public <B> Monad<B, M> unit(B a);

    abstract public <B> Monad<B, M> flatMap(Function.F1<A, ? extends Monad<B, M>> f);

    public <B> Monad<B, M> bind(Function.F1<A, ? extends Monad<B, M>> f) {
        return flatMap(f);
    }

    public <B> Monad<B, M> join() {
        return flatMap(new Function.F1<A, Monad<B, M>>() {
            @Override
            public Monad<B, M> invoke(A a) {
                return (Monad<B, M>) unit(get());
            }
        });
    }

    public <B> Monad<B, M> map(Function.F1<A, ? extends B> f) {
        return (Monad<B, M>) fmap(f);
    }

    @Override
    protected <B> Applicative<B, M> pure(B value) {
        return unit(value);
    }
}
