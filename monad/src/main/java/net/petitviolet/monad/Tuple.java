package net.petitviolet.monad;

public class Tuple<A, B> {
    public final A fst;
    public final B snd;

    @Override
    public String toString() {
        return "Tuple(" + fst + snd + ')';
    }

    public Tuple(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }
}