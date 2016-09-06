package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.List;
import org.highj.data.Maybe;

import java.util.function.Function;

public class Typeable<A> implements __<Typeable.Mu,A> {
    private final PTypeable<A,A> _pTypeable;

    public enum Mu {}

    private Typeable(PTypeable<A,A> pTypeable) {
        this._pTypeable = pTypeable;
    }

    public static <A> Typeable<A> fromPTypeable(PTypeable<A,A> pTypeable) {
        return new Typeable<>(pTypeable);
    }

    public static <A> Typeable<A> narrow(__<Typeable.Mu,A> a) {
        return (Typeable<A>)a;
    }

    public PTypeable<A,A> toPTypeable() {
        return _pTypeable;
    }

    public <B> Typeable<B> invmap(Function<A,B> f, Function<B,A> g) {
        return fromPTypeable(toPTypeable().dimap(g, f));
    }

    public static Typeable<Boolean> boolean_() {
        return fromPTypeable(PTypeable.boolean_());
    }

    public static Typeable<Integer> int_() {
        return fromPTypeable(PTypeable.int_());
    }

    public static Typeable<Long> long_() {
        return fromPTypeable(PTypeable.long_());
    }

    public static Typeable<Double> double_() {
        return fromPTypeable(PTypeable.double_());
    }

    public static Typeable<String> string() {
        return fromPTypeable(PTypeable.string());
    }

    public static <A> Typeable<A> record(RecordTypeable<A> recordTypeable) {
        return fromPTypeable(PTypeable.record(recordTypeable.toPRecordTypeable()));
    }

    public static <A> Typeable<A> union(UnionTypeable<A> unionTypeable) {
        return Typeable.fromPTypeable(PTypeable.union(unionTypeable.toPUnionTypeable()));
    }

    public static <A> Typeable<List<A>> list(Typeable<A> typeable) {
        return fromPTypeable(PTypeable.list(typeable.toPTypeable()));
    }

    public static <A extends Enum<A>> Typeable<A> enum_(java.lang.Class<A> klass) {
        return fromPTypeable(PTypeable.enum_(klass));
    }

    public static <A> Typeable<Maybe<A>> maybe(Typeable<A> typeable) {
        return fromPTypeable(PTypeable.maybe(typeable.toPTypeable()));
    }
}