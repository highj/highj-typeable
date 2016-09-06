package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Maybe;
import org.highj.data.tuple.T2;
import org.highj.typeclass2.profunctor.Profunctor;

public interface ProfunctorRecordTypeable<F_> extends Profunctor<F_> {

    <A,B> __<__<F_,A>,B> singleton(String tag, PTypeable<A,B> type);

    <A,B,C,D> __<__<F_,T2<A,C>>,T2<B,D>> append(PRecordTypeable<A,B> ta, PRecordTypeable<C,D> tb);

    <A,B> __<__<F_,A>,Maybe<B>> noneOnMissing(PRecordTypeable<A,B> ta);

    default <A,B> __<__<F_,A>,B> defaultValue(PRecordTypeable<A,B> ta, B defaultValue) {
        return rmap((Maybe<B> x) -> x.getOrElse(defaultValue), noneOnMissing(ta));
    }
}