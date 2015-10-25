package net.petitviolet.monad.maybe;

import android.support.annotation.NonNull;

import net.petitviolet.monad.func.Function;

public class Just<A> extends Maybe<A> {
    private final A mAarget;

    Just(@NonNull A target) {
        mAarget = target;
    }

    @Override
    public String toString() {
        return "Just{" + mAarget + "}";
    }

    public boolean isPresent() {
        return Boolean.TRUE;
    }

    @Override
    public A get() {
        return mAarget;
    }

    @Override
    public A getOrElse(A defaultValue) {
        return get();
    }

    @Override
    public void foreach(Function.F<? super A> func) {
        func.call(mAarget);
    }

    @Override
    public <B> Maybe<B> map(Function.F1<? super A, ? extends B> func) {
        return Maybe.of(func.call(mAarget));
    }

    @Override
    public <B> Maybe<B> flatMap(Function.F1<? super A, ? extends Maybe<B>> func) {
        return func.call(mAarget);
    }

    @Override
    public Maybe<A> filter(Function.F1<? super A, Boolean> func) {
        if (func.call(mAarget)) {
            return this;
        } else {
            return new None<>();
        }
    }
}