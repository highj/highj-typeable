package org.highj.typeable;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.tuple.T0;

import java.util.function.Function;

public interface PUnionTypeable<A,B> extends __2<PUnionTypeable.Mu,A,B> {
    enum Mu {}

    <F_> __<__<F_,A>,B> run(ProfunctorUnionTypeable<F_> context);

    default <C,D> PUnionTypeable<C,D> dimap(Function<C,A> f, Function<B,D> g) {
        return new PUnionTypeable<C,D>() {
            @Override
            public <F_> __<__<F_,C>,D> run(ProfunctorUnionTypeable<F_> context) {
                return context.dimap(f, g, PUnionTypeable.this.run(context));
            }
        };
    }

    static PUnionTypeable<T0,T0> typelessSingleton(String tag) {
        return new PUnionTypeable<T0,T0>() {
            @Override
            public <F_> __<__<F_,T0>,T0> run(ProfunctorUnionTypeable<F_> context) {
                return context.typelessSingleton(tag);
            }
        };
    }

    static <A,B> PUnionTypeable<A,B> singleton(String tag, PTypeable<A,B> type) {
        return new PUnionTypeable<A,B>() {
            @Override
            public <F_> __<__<F_,A>,B> run(ProfunctorUnionTypeable<F_> context) {
                return context.singleton(tag, type);
            }
        };
    }

    static <A,B,C,D> PUnionTypeable<Either<A,C>,Either<B,D>> append(PUnionTypeable<A,B> ta, PUnionTypeable<C,D> tb) {
        return new PUnionTypeable<Either<A,C>,Either<B,D>>() {
            @Override
            public <F_> __<__<F_,Either<A,C>>,Either<B,D>> run(ProfunctorUnionTypeable<F_> context) {
                return context.append(ta, tb);
            }
        };
    }

    static <A,B> PUnionTypeable<A,B> appendMany(List<ProfunctorUnionTypeable.UnionElement<A,B,?,?>> elements) {
        return new PUnionTypeable<A,B>() {
            @Override
            public <F_> __<__<F_,A>,B> run(ProfunctorUnionTypeable<F_> context) {
                return context.appendMany(elements);
            }
        };
    }
}