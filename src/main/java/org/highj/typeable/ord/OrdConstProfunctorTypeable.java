package org.highj.typeable.ord;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.typeable.OrdConst;
import org.highj.typeable.PRecordTypeable;
import org.highj.typeable.PUnionTypeable;
import org.highj.typeable.ProfunctorTypeable;

public interface OrdConstProfunctorTypeable extends OrdConstProfunctor, ProfunctorTypeable<OrdConst.Mu> {

    OrdConstProfunctorTypeable instance = new OrdConstProfunctorTypeable() {};

    @Override
    default __<__<OrdConst.Mu,Boolean>,Boolean> boolean_() {
        return OrdConst.of(Ord.<Boolean>fromComparable());
    }

    @Override
    default __<__<OrdConst.Mu,Integer>,Integer> int_() {
        return OrdConst.of(Ord.<Integer>fromComparable());
    }

    @Override
    default __<__<OrdConst.Mu,Long>,Long> long_() {
        return OrdConst.of(Ord.<Long>fromComparable());
    }

    @Override
    default __<__<OrdConst.Mu,Double>,Double> double_() {
        return OrdConst.of(Ord.<Double>fromComparable());
    }

    @Override
    default __<__<OrdConst.Mu,String>,String> string() {
        return OrdConst.of(Ord.<String>fromComparable());
    }

    @Override
    default <A,B> __<__<OrdConst.Mu,A>,B> record(PRecordTypeable<A,B> ta) {
        return ta.run(OrdConstProfunctorRecordTypeable.instance);
    }

    @Override
    default <A,B> __<__<OrdConst.Mu,A>,B> union(PUnionTypeable<A,B> ta) {
        return ta.run(OrdConstProfunctorUnionTypeable.instance);
    }

    @Override
    default <A,B> __<__<OrdConst.Mu,List<A>>,List<B>> list(__<__<OrdConst.Mu,A>,B> ta) {
        Ord<A> ordA = OrdConst.narrow(ta).ord();
        return OrdConst.of(
            (Ord<List<A>>)(a, b) -> {
                while (!a.isEmpty() && !b.isEmpty()) {
                    Ordering x = ordA.cmp(a.head(), b.head());
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
            }
        );
    }
}