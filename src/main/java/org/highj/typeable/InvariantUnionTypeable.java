package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.typeclass1.invariant.Invariant;

public interface InvariantUnionTypeable<F> extends Invariant<F> {
    <A> __<F,A> singleton(String tag, Typeable<A> type);
    <A,B> __<F,Either<A,B>> append(UnionTypeable<A> ta, UnionTypeable<B> tb);
}
