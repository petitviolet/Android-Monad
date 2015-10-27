package net.petitviolet.monad.type;

public interface Monoid<A> {
    Monoid<A> empty();

    Monoid<A> add(A a);

    A get();

    public static class TypeMismatchException extends Exception{
        public TypeMismatchException(String s) {
            super(s);
        }
    }
}
