package net.petitviolet.monad.maybe;

import android.support.annotation.NonNull;

import net.petitviolet.monad.Monad;
import net.petitviolet.monad.func.Function;

class Just<A> extends Maybe<A> {
    private final A mTarget;

    Just(@NonNull A target) {
        mTarget = target;
    }

    @Override
    public String toString() {
        return "Just{" + mTarget + "}";
    }

    @Override
    public A get() {
        return mTarget;
    }

    @Override
    public A getOrElse(A defaultValue) {
        return get();
    }

    @Override
    public void foreach(Function.F<? super A> func) {
        func.invoke(mTarget);
    }

    @Override
    public Maybe<A> filter(Function.F1<? super A, Boolean> func) {
        if (func.invoke(mTarget)) {
            return this;
        } else {
            return new None<>();
        }
    }


    @Override
    public <B> Maybe<B> flatMap(Function.F1<A, ? extends Monad<B, Maybe<?>>> f) {
        return (Maybe<B>) f.invoke(mTarget);
    }
}