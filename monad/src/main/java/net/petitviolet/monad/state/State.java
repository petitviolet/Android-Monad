package net.petitviolet.monad.state;

import net.petitviolet.monad.Tuple;
import net.petitviolet.monad.func.Function;

/**
 * State Monad
 * @param <S>
 * @param <A>
 */
public class State<S, A> {
    private static final String TAG = State.class.getSimpleName();
    // get current state, then return new value and state
    final Function.F1<S, Tuple<A, S>> runState;

    /**
     * use unit or init Factory
     * @param runState
     */
    private State(Function.F1<S, Tuple<A, S>> runState) {
        this.runState = runState;
    }

    /**
     * unit factory given by first value
     * @param a
     * @param <S>
     * @param <A>
     * @return
     */
    public static <S, A> State<S, A> unit(final A a) {
        return State.init(new Function.F1<S, Tuple<A, S>>() {
            @Override
            public Tuple<A, S> invoke(S s) {
                return Tuple.of(a, s);
            }
        });
    }

    /**
     * init factory with runState function
     * @param runState
     * @param <S>
     * @param <A>
     * @return
     */
    public static <S, A> State<S, A> init(Function.F1<S, Tuple<A, S>> runState) {
        return new State<>(runState);
    }

    /**
     * put new state
     * @param newState
     * @return
     */
    public State<S, Void> put(final S newState) {
        return State.init(new Function.F1<S, Tuple<Void, S>>() {
            @Override
            public Tuple<Void, S> invoke(S s) {
                return Tuple.of(null, newState);
            }
        });
    }

    public Tuple<A, S> apply(S state) {
        return runState.invoke(state);
    }

    /**
     * get current value
     * @return
     */
    public State<S, S> get() {
        return State.init(new Function.F1<S, Tuple<S, S>>() {
            @Override
            public Tuple<S, S> invoke(S s) {
                return Tuple.of(s, s);
            }
        });
    }

    /**
     * monadic
     * @param f
     * @param <B>
     * @return
     */
    public <B> State<S, B> flatMap(final Function.F1<? super A, State<S, B>> f) {
        Function.F1<S, Tuple<B, S>> fb = new Function.F1<S, Tuple<B, S>>() {
            @Override
            public Tuple<B, S> invoke(S s) {
                Tuple<A, S> as = runState.invoke(s);
                State<S, ? extends B> sb = f.invoke(as._1);
                Tuple<B, S> bs = (Tuple<B, S>) sb.runState.invoke(as._2);
                return bs;
            }
        };
        return State.init(fb);
    }

    /**
     * map function to inner value
     * @param f
     * @param <B>
     * @return
     */
    public <B> State<S, B> map(final Function.F1<? super A, ? extends B> f) {
        return flatMap(new Function.F1<A, State<S, B>>() {
            @Override
            public State<S, B> invoke(A a) {
                return State.unit(f.invoke(a));
            }
        });
    }

}