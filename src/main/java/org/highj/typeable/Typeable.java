package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.tuple.T0;
import org.highj.data.tuple.T2;
import org.highj.function.F1;

public interface Typeable<A> extends __<Typeable.µ,A> {
    enum µ {}

    <F> __<F,A> run(InvariantTypeable<F> context);

    default <B> Typeable<B> invmap(F1<A,B> f, F1<B,A> g) {
        return new Typeable<B>() {
            @Override
            public <F> __<F, B> run(InvariantTypeable<F> context) {
                return context.invmap(f, g, Typeable.this.run(context));
            }
        };
    }

    static Typeable<T0> unit() {
        return new Typeable<T0>() {
            @Override
            public <F> __<F, T0> run(InvariantTypeable<F> context) {
                return context.unit();
            }
        };
    }

    static Typeable<Boolean> boolean_() {
        return new Typeable<Boolean>() {
            @Override
            public <F> __<F, Boolean> run(InvariantTypeable<F> context) {
                return context.boolean_();
            }
        };
    }

    static Typeable<Integer> int_() {
        return new Typeable<Integer>() {
            @Override
            public <F> __<F, Integer> run(InvariantTypeable<F> context) {
                return context.int_();
            }
        };
    }

    static Typeable<Long> long_() {
        return new Typeable<Long>() {
            @Override
            public <F> __<F, Long> run(InvariantTypeable<F> context) {
                return context.long_();
            }
        };
    }

    static Typeable<Double> double_() {
        return new Typeable<Double>() {
            @Override
            public <F> __<F, Double> run(InvariantTypeable<F> context) {
                return context.double_();
            }
        };
    }

    static Typeable<String> string_() {
        return new Typeable<String>() {
            @Override
            public <F> __<F, String> run(InvariantTypeable<F> context) {
                return context.string();
            }
        };
    }

    static <A> Typeable<A> record(RecordTypeable<A> ta) {
        return new Typeable<A>() {
            @Override
            public <F> __<F, A> run(InvariantTypeable<F> context) {
                return context.record(ta);
            }
        };
    }

    static <A> Typeable<A> union(UnionTypeable<A> ta) {
        return new Typeable<A>() {
            @Override
            public <F> __<F, A> run(InvariantTypeable<F> context) {
                return context.union(ta);
            }
        };
    }

    public static <A> Typeable<List<A>> list(Typeable<A> typeable) {
        return new Typeable<List<A>>() {
            @Override
            public <F> __<F, List<A>> run(InvariantTypeable<F> context) {
                return context.list(typeable.run(context));
            }
        };
    }
}
