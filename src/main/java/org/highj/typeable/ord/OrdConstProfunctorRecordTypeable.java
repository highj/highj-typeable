package org.highj.typeable.ord;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.T2;
import org.highj.typeable.OrdConst;
import org.highj.typeable.PRecordTypeable;
import org.highj.typeable.PTypeable;
import org.highj.typeable.ProfunctorRecordTypeable;

public interface OrdConstProfunctorRecordTypeable extends OrdConstProfunctor, ProfunctorRecordTypeable<OrdConst.Mu> {

    OrdConstProfunctorRecordTypeable instance = new OrdConstProfunctorRecordTypeable() {};

    @Override
    default <A,B> __<__<OrdConst.Mu,A>,B> singleton(String tag, PTypeable<A,B> type) {
        return type.run(OrdConstProfunctorTypeable.instance);
    }

    @Override
    default <A,B,C,D> __<__<OrdConst.Mu,T2<A,C>>,T2<B,D>> append(PRecordTypeable<A,B> ta, PRecordTypeable<C,D> tb) {
        OrdConst<A,B> ordA = OrdConst.narrow(ta.run(instance));
        OrdConst<C,D> ordB = OrdConst.narrow(tb.run(instance));
        Ord<T2<A,C>> ord = (a, b) -> {
            Ordering x = ordA.ord().cmp(a._1(), b._1());
            if (x == Ordering.EQ) {
                return ordB.ord().cmp(a._2(), b._2());
            } else {
                return x;
            }
        };
        return OrdConst.of(ord);
    }

    @Override
    default <A, B> __<__<OrdConst.Mu, A>, Maybe<B>> noneOnMissing(PRecordTypeable<A, B> ta) {
        return OrdConst.of(OrdConst.narrow(ta.run(instance)).ord());
    }
}
