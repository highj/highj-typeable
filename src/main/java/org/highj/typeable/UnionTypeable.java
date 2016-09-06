package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.tuple.T0;

import java.util.function.Function;

public class UnionTypeable<A> implements __<UnionTypeable.Mu,A> {
    private final PUnionTypeable<A,A> _pUnionTypeable;

    public enum Mu {}

    private UnionTypeable(PUnionTypeable<A,A> pUnionTypeable) {
        this._pUnionTypeable = pUnionTypeable;
    }

    public static <A> UnionTypeable<A> fromPUnionTypeable(PUnionTypeable<A,A> pUnionTypeable) {
        return new UnionTypeable<>(pUnionTypeable);
    }

    public static <A> UnionTypeable<A> narrow(__<UnionTypeable.Mu,A> a) {
        return (UnionTypeable<A>)a;
    }

    public PUnionTypeable<A,A> toPUnionTypeable() {
        return _pUnionTypeable;
    }

    public <B> UnionTypeable<B> invmap(Function<A,B> f, Function<B,A> g) {
        return fromPUnionTypeable(toPUnionTypeable().dimap(g, f));
    }

    public static UnionTypeable<T0> typelessSingleton(String tag) {
        return  fromPUnionTypeable(PUnionTypeable.typelessSingleton(tag));
    }

    public static <A> UnionTypeable<A> singleton(String tag, Typeable<A> type) {
        return UnionTypeable.fromPUnionTypeable(PUnionTypeable.singleton(tag, type.toPTypeable()));
    }

    public static <A,B> UnionTypeable<Either<A,B>> append(UnionTypeable<A> ta, UnionTypeable<B> tb) {
        return UnionTypeable.fromPUnionTypeable(PUnionTypeable.append(ta.toPUnionTypeable(), tb.toPUnionTypeable()));
    }
}