package net.petitviolet.monad.maybe;

import android.support.annotation.NonNull;

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

    public boolean isPresent() {
        return Boolean.TRUE;
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
        func.call(mTarget);
    }

    @Override
    public <B> Maybe<B> map(Function.F1<? super A, ? extends B> func) {
        return Maybe.of(func.call(mTarget));
    }

    @Override
    public <B> Maybe<B> flatMap(Function.F1<? super A, ? extends Maybe<B>> func) {
        return func.call(mTarget);
    }

    @Override
    public Maybe<A> filter(Function.F1<? super A, Boolean> func) {
        if (func.call(mTarget)) {
            return this;
        } else {
            return new None<>();
        }
    }
}