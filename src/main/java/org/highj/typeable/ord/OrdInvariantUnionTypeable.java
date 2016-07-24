package org.highj.typeable.ord;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.typeable.InvariantUnionTypeable;
import org.highj.typeable.Typeable;
import org.highj.typeable.UnionTypeable;

public interface OrdInvariantUnionTypeable extends InvariantUnionTypeable<Ord.µ> {
    
    static OrdInvariantUnionTypeable instance = new OrdInvariantUnionTypeable() {};

    @Override
    public default <A> __<Ord.µ, A> singleton(String tag, Typeable<A> type) {
        return type.run(OrdInvariantTypeable.instance);
    }

    @Override
    public default <A, B> __<Ord.µ, Either<A, B>> append(UnionTypeable<A> ta, UnionTypeable<B> tb) {
        Ord<A> ordA = Ord.narrow(ta.run(this));
        Ord<B> ordB = Ord.narrow(tb.run(this));
        return (Ord<Either<A,B>>)(a, b) ->
            a.either(
                x -> b.either(
                    y -> ordA.cmp(x, y),
                    y -> Ordering.LT
                ),
                x -> b.either(
                    y -> Ordering.GT,
                    y -> ordB.cmp(x, y)
                )
            );
    }

    @Override
    public default <A, B> __<Ord.µ, B> invmap(Function<A, B> fn, Function<B, A> nf, __<Ord.µ, A> nestedA) {
        return (Ord<B>)(a, b) -> Ord.narrow(nestedA).cmp(nf.apply(a), nf.apply(b));
    }
}
