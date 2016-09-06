package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.typeclass2.profunctor.Profunctor;

public interface ProfunctorTypeable<F_> extends Profunctor<F_> {
    __<__<F_,Boolean>,Boolean> boolean_();
    __<__<F_,Integer>,Integer> int_();
    __<__<F_,Long>,Long> long_();
    __<__<F_,Double>,Double> double_();
    __<__<F_,String>,String> string();
    <A,B> __<__<F_,A>,B> record(PRecordTypeable<A,B> ta);
    <A,B> __<__<F_,A>,B> union(PUnionTypeable<A,B> ta);
    <A,B> __<__<F_,List<A>>,List<B>> list(__<__<F_,A>,B> ta);
}
