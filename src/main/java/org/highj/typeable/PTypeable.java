package org.highj.typeable;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.tuple.T0;

import java.util.Arrays;
import java.util.function.Function;

public interface PTypeable<A,B> extends __2<PTypeable.Mu,A,B> {
    class Mu {}

    <F_> __<__<F_,A>,B> run(ProfunctorTypeable<F_> context);

    default <C,D> PTypeable<C,D> dimap(Function<C,A> f, Function<B,D> g) {
        return new PTypeable<C,D>() {
            @Override
            public <F_> __<__<F_,C>,D> run(ProfunctorTypeable<F_> context) {
                return context.dimap(f, g, PTypeable.this.run(context));
            }
        };
    }

    static PTypeable<Boolean,Boolean> boolean_() {
        return new PTypeable<Boolean,Boolean>() {
            @Override
            public <F_> __<__<F_,Boolean>,Boolean> run(ProfunctorTypeable<F_> context) {
                return context.boolean_();
            }
        };
    }

    static PTypeable<Integer,Integer> int_() {
        return new PTypeable<Integer,Integer>() {
            @Override
            public <F_> __<__<F_,Integer>,Integer> run(ProfunctorTypeable<F_> context) {
                return context.int_();
            }
        };
    }

    static PTypeable<Long,Long> long_() {
        return new PTypeable<Long,Long>() {
            @Override
            public <F_> __<__<F_,Long>,Long> run(ProfunctorTypeable<F_> context) {
                return context.long_();
            }
        };
    }

    static PTypeable<Double,Double> double_() {
        return new PTypeable<Double,Double>() {
            @Override
            public <F_> __<__<F_,Double>,Double> run(ProfunctorTypeable<F_> context) {
                return context.double_();
            }
        };
    }

    static PTypeable<String,String> string() {
        return new PTypeable<String,String>() {
            @Override
            public <F_> __<__<F_,String>,String> run(ProfunctorTypeable<F_> context) {
                return context.string();
            }
        };
    }

    static <A,B> PTypeable<A,B> record(PRecordTypeable<A,B> recordTypeable) {
        return new PTypeable<A,B>() {
            @Override
            public <F_> __<__<F_,A>,B> run(ProfunctorTypeable<F_> context) {
                return context.record(recordTypeable);
            }
        };
    }

    static <A,B> PTypeable<A,B> union(PUnionTypeable<A,B> unionTypeable) {
        return new PTypeable<A,B>() {
            @Override
            public <F_> __<__<F_,A>,B> run(ProfunctorTypeable<F_> context) {
                return context.union(unionTypeable);
            }
        };
    }

    static <A,B> PTypeable<List<A>,List<B>> list(PTypeable<A,B> typeable) {
        return new PTypeable<List<A>,List<B>>() {
            @Override
            public <F_> __<__<F_,List<A>>,List<B>> run(ProfunctorTypeable<F_> context) {
                return context.list(typeable.run(context));
            }
        };
    }

    static <A extends Enum<A>> PTypeable<A,A> enum_(java.lang.Class<A> klass) {
        class Util {
            PUnionTypeable<A,A> mk(A[] values) {
                if (values.length == 0) {
                    throw new IllegalStateException();
                } else if (values.length == 1) {
                    return PUnionTypeable.typelessSingleton(values[0].name()).dimap(unused -> T0.of(), unused -> values[0]);
                } else {
                    int n = values.length/2;
                    A[] values1 = Arrays.copyOfRange(values, 0, n);
                    A[] values2 = Arrays.copyOfRange(values, n, values.length);
                    return PUnionTypeable.append(
                        mk(values1),
                        mk(values2)
                    ).dimap(
                        (A x) -> {
                            for (A value : values1) {
                                if (value.equals(x)) {
                                    return Either.<A,A>Left(x);
                                }
                            }
                            return Either.<A,A>Right(x);
                        },
                        Either::unify
                    );
                }
            }
        }
        final Util util = new Util();
        A[] values = klass.getEnumConstants();
        if (values.length == 0) {
            throw new UnsupportedOperationException("Empty enum not supported.");
        }
        return union(util.mk(values));
    }

    static <A,B> PTypeable<Maybe<A>,Maybe<B>> maybe(PTypeable<A,B> typeable) {
        return union(PUnionTypeable.append(
            PUnionTypeable.typelessSingleton("none"),
            PUnionTypeable.singleton("some", typeable)
        )).dimap(
            (Maybe<A> x) -> x.<Either<T0,A>>cata(
                Either.<T0,A>Left(T0.of()),
                Either::<T0, A>Right
            ),
            (Either<T0, B> x) -> x.either(unused -> Maybe.<B>Nothing(), Maybe::<B>Just)
        );
    }
}