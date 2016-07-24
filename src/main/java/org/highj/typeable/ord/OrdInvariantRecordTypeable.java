package org.highj.typeable.ord;

import java.util.function.Function;
import org.derive4j.hkt.__;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.T2;
import org.highj.typeable.InvariantRecordTypeable;
import org.highj.typeable.RecordTypeable;
import org.highj.typeable.Typeable;

public interface OrdInvariantRecordTypeable extends InvariantRecordTypeable<Ord.µ> {
    
    static OrdInvariantRecordTypeable instance = new OrdInvariantRecordTypeable() {};

    @Override
    default <A> __<Ord.µ, A> singleton(String tag, Typeable<A> type) {
        return type.run(OrdInvariantTypeable.instance);
    }

    @Override
    default <A, B> __<Ord.µ, T2<A, B>> append(RecordTypeable<A> ta, RecordTypeable<B> tb) {
        Ord<A> ordA = Ord.narrow(ta.run(this));
        Ord<B> ordB = Ord.narrow(tb.run(this));
        return (Ord<T2<A,B>>)(a, b) -> {
            Ordering x = ordA.cmp(a._1(), b._1());
            switch (x) {
                case LT:
                case GT:
                    return x;
                case EQ:
                    return ordB.cmp(a._2(), b._2());
                default:
                    throw new IllegalStateException();
            }
        };
    }

    @Override
    default <A, B> __<Ord.µ, B> invmap(Function<A, B> fn, Function<B, A> nf, __<Ord.µ, A> nestedA) {
        return (Ord<B>)(a, b) -> Ord.narrow(nestedA).cmp(nf.apply(a), nf.apply(b));
    }
}
