package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.typeclass1.invariant.Invariant;

public interface InvariantUnionTypeable<F> extends Invariant<F> {
    <A> __<F,A> singleton(String tag, Maybe<Typeable<A>> type);
    <A,B> __<F,Either<A,B>> append(UnionTypeable<A> ta, UnionTypeable<B> tb);
}
