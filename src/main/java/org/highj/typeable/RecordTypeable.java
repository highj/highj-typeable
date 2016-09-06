package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;

import java.util.function.Function;

public class RecordTypeable<A> implements __<RecordTypeable.Mu,A> {
    private final PRecordTypeable<A,A> _pRecordTypeable;

    public enum Mu {}

    private RecordTypeable(PRecordTypeable<A,A> pRecordTypeable) {
        this._pRecordTypeable = pRecordTypeable;
    }

    public static <A> RecordTypeable<A> fromPRecordTypeable(PRecordTypeable<A,A> pRecordTypeable) {
        return new RecordTypeable<>(pRecordTypeable);
    }

    public static <A> RecordTypeable<A> narrow(__<RecordTypeable.Mu,A> a) {
        return (RecordTypeable<A>)a;
    }

    public PRecordTypeable<A,A> toPRecordTypeable() {
        return _pRecordTypeable;
    }

    public <B> RecordTypeable<B> invmap(Function<A,B> f, Function<B,A> g) {
        return fromPRecordTypeable(toPRecordTypeable().dimap(g, f));
    }

    public static <A> RecordTypeable<A> singleton(String tag, Typeable<A> type) {
        return fromPRecordTypeable(PRecordTypeable.singleton(tag, type.toPTypeable()));
    }

    public RecordTypeable<A> defaultValue(A a) {
        return fromPRecordTypeable(toPRecordTypeable().defaultValue(a));
    }

    public static <A,B> RecordTypeable<T2<A,B>> append(RecordTypeable<A> ta, RecordTypeable<B> tb) {
        return fromPRecordTypeable(PRecordTypeable.append(ta.toPRecordTypeable(), tb.toPRecordTypeable()));
    }

    public static <A,B> RecordTypeable<T2<A,B>> t2(RecordTypeable<A> ta, RecordTypeable<B> tb) {
        return append(ta, tb);
    }

    public static <A,B,C> RecordTypeable<T3<A,B,C>> t3(RecordTypeable<A> ta, RecordTypeable<B> tb, RecordTypeable<C> tc) {
        return fromPRecordTypeable(PRecordTypeable.t3(
            ta.toPRecordTypeable(),
            tb.toPRecordTypeable(),
            tc.toPRecordTypeable()
        ));
    }

    public static <A,B,C,D> RecordTypeable<T4<A,B,C,D>> t4(RecordTypeable<A> ta, RecordTypeable<B> tb, RecordTypeable<C> tc, RecordTypeable<D> td) {
        return fromPRecordTypeable(PRecordTypeable.t4(
            ta.toPRecordTypeable(),
            tb.toPRecordTypeable(),
            tc.toPRecordTypeable(),
            td.toPRecordTypeable()
        ));
    }
}