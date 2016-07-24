package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Either;

public interface UnionTypeable<A> extends __<UnionTypeable.µ,A> {
    enum µ {}
    
    <F> __<F,A> run(InvariantUnionTypeable<F> context);
    
    static <A> UnionTypeable<A> singleton(String tag, Typeable<A> type) {
        return new UnionTypeable<A>() {
            @Override
            public <F> __<F, A> run(InvariantUnionTypeable<F> context) {
                return context.singleton(tag, type);
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
