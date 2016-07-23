package org.highj.typeable.ord;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.typeable.InvariantTypeable;

public interface OrdInvariantTypeable extends InvariantTypeable<Ord.µ> {
    
    static OrdInvariantTypeable instance = new OrdInvariantTypeable() {};

    @Override
    default __<Ord.µ, T0> unit() {
        return (Ord<T0>)(a, b) -> Ordering.EQ;
    }

    @Override
    default __<Ord.µ, Boolean> boolean_() {
        return Ord.fromComparable();
    }

    @Override
    default __<Ord.µ, Integer> int_() {
        return Ord.fromComparable();
    }

    @Override
    default __<Ord.µ, Long> long_() {
        return Ord.fromComparable();
    }

    @Override
    default __<Ord.µ, Double> double_() {
        return Ord.fromComparable();
    }

    @Override
    default __<Ord.µ, String> string() {
        return Ord.fromComparable();
    }

    @Override
    default <A, B> __<Ord.µ, T2<A, B>> t2(__<Ord.µ, A> fa, __<Ord.µ, B> fb) {
        return (Ord<T2<A,B>>)(a, b) -> {
            Ordering x = Ord.narrow(fa).cmp(a._1(), b._1());
            switch (x) {
                case LT:
                case GT:
                    return x;
                case EQ:
                    return Ord.narrow(fb).cmp(a._2(), b._2());
                default:
                    throw new IllegalStateException();
            }
        };
    }

    @Override
    default <A, B> __<Ord.µ, Either<A, B>> either(__<Ord.µ, A> fa, __<Ord.µ, B> fb) {
        return (Ord<Either<A,B>>)(a, b) ->
            a.either(
                x -> b.either(
                    y -> Ord.narrow(fa).cmp(x, y),
                    y -> Ordering.LT
                ),
                x -> b.either(
                    y -> Ordering.GT,
                    y -> Ord.narrow(fb).cmp(x, y)
                )
            );
    }

    @Override
    default <A> __<Ord.µ, List<A>> list(__<Ord.µ, A> fa) {
        return (Ord<List<A>>)(a, b) -> {
            while (!a.isEmpty() && !b.isEmpty()) {
                Ordering x = Ord.narrow(fa).cmp(a.head(), b.head());
                switch (x) {
                    case LT:
                    case GT:
                        return x;
                    default:
                }
                a = a.tail();
                b = b.tail();
            }
            if (!a.isEmpty()) {
                return Ordering.GT;
            }
            if (!b.isEmpty()) {
                return Ordering.LT;
            }
            return Ordering.EQ;
        };
    }

    @Override
    default <A, B> __<Ord.µ, B> invmap(Function<A, B> fn, Function<B, A> nf, __<Ord.µ, A> nestedA) {
        return (Ord<B>)(a, b) -> Ord.narrow(nestedA).cmp(nf.apply(a), nf.apply(b));
    }
}
