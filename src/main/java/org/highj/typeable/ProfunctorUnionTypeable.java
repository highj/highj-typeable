package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.tuple.T0;
import org.highj.optic.PPrism;
import org.highj.typeclass2.profunctor.Profunctor;

public interface ProfunctorUnionTypeable<F_> extends Profunctor<F_> {
    __<__<F_,T0>,T0> typelessSingleton(String tag);
    <A,B> __<__<F_,A>,B> singleton(String tag, PTypeable<A,B> type);
    <A,B,C,D> __<__<F_,Either<A,C>>,Either<B,D>> append(PUnionTypeable<A,B> ta, PUnionTypeable<C,D> tb);
    <A,B> __<__<F_,A>,B> appendMany(List<UnionElement<A,B,?,?>> elements);

    class UnionElement<A,B,C,D> {
        private final String _tag;
        private final PPrism<A,B,C,D> _prism;
        private final PTypeable<C,D> _typeable;

        private UnionElement(String tag, PPrism<A,B,C,D> prism, PTypeable<C,D> typeable) {
            this._tag = tag;
            this._prism = prism;
            this._typeable = typeable;
        }

        public static <A,B,C,D> UnionElement<A,B,C,D> create(String tag, PPrism<A,B,C,D> prism, PTypeable<C,D> typeable) {
            return new UnionElement<>(tag, prism, typeable);
        }

        public String tag() {
            return _tag;
        }

        public PPrism<A, B, C, D> prism() {
            return _prism;
        }

        public PTypeable<C, D> typeable() {
            return _typeable;
        }
    }
}