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
import org.highj.typeable.RecordTypeable;
import org.highj.typeable.UnionTypeable;

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
    public default <A> __<Ord.µ, A> record(RecordTypeable<A> ta) {
        return ta.run(OrdInvariantRecordTypeable.instance);
    }
    
    @Override
    public default <A> __<Ord.µ, A> union(UnionTypeable<A> ta) {
        return ta.run(OrdInvariantUnionTypeable.instance);
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
