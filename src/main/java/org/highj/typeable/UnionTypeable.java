package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.Maybe;
import org.highj.function.F1;

public interface UnionTypeable<A> extends __<UnionTypeable.µ,A> {
    enum µ {}
    
    <F> __<F,A> run(InvariantUnionTypeable<F> context);
    
    default <B> UnionTypeable<B> invmap(F1<A,B> f, F1<B,A> g) {
        return new UnionTypeable<B>() {
            @Override
            public <F> __<F, B> run(InvariantUnionTypeable<F> context) {
                return context.invmap(f, g, UnionTypeable.this.run(context));
            }
        };
    }

    static <A> UnionTypeable<A> singleton(String tag, Maybe<Typeable<A>> typeOp) {
        return new UnionTypeable<A>() {
            @Override
            public <F> __<F, A> run(InvariantUnionTypeable<F> context) {
                return context.singleton(tag, typeOp);
            }
        };
    }
    
    static <A,B> UnionTypeable<Either<A,B>> append(UnionTypeable<A> ta, UnionTypeable<B> tb) {
        return new UnionTypeable<Either<A,B>>() {
            @Override
            public <F> __<F, Either<A, B>> run(InvariantUnionTypeable<F> context) {
                return context.append(ta, tb);
            }
        };
    }
}
