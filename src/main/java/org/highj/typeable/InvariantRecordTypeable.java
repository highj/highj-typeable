package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.typeclass1.invariant.Invariant;

public interface InvariantRecordTypeable<F> extends Invariant<F> {
    <A> __<F,A> singleton(String tag, Typeable<A> type);
    <A,B> __<F,T2<A,B>> append(RecordTypeable<A> ta, RecordTypeable<B> tb);
}
