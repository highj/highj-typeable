package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.invariant.Invariant;

public interface InvariantTypeable<F> extends Invariant<F> {
    __<F,T0> unit();
    __<F,Boolean> boolean_();
    __<F,Integer> int_();
    __<F,Long> long_();
    __<F,Double> double_();
    __<F,String> string();
    <A,B> __<F,T2<A,B>> t2(__<F,A> fa, __<F,B> fb);
    <A,B> __<F,Either<A,B>> either(__<F,A> fa, __<F,B> fb);
    <A> __<F,List<A>> list(__<F,A> fa);
}