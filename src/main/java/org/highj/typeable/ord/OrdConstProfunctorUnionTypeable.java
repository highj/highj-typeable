package org.highj.typeable.ord;

import org.derive4j.hkt.__;
import org.highj.data.Either;
import org.highj.data.List;
import org.highj.data.Maybe;
import org.highj.data.ord.Ord;
import org.highj.data.ord.Ordering;
import org.highj.data.tuple.T0;
import org.highj.typeable.OrdConst;
import org.highj.typeable.PTypeable;
import org.highj.typeable.PUnionTypeable;
import org.highj.typeable.ProfunctorUnionTypeable;

public interface OrdConstProfunctorUnionTypeable extends OrdConstProfunctor, ProfunctorUnionTypeable<OrdConst.Mu> {

    OrdConstProfunctorUnionTypeable instance = new OrdConstProfunctorUnionTypeable() {};

    @Override
    default __<__<OrdConst.Mu,T0>,T0> typelessSingleton(String tag) {
        return OrdConst.of((unused1, unused2) -> Ordering.EQ);
    }

    @Override
    default <A,B> __<__<OrdConst.Mu,A>,B> singleton(String tag, PTypeable<A,B> type) {
        return type.run(OrdConstProfunctorTypeable.instance);
    }

    @Override
    default <A,B,C,D> __<__<OrdConst.Mu,Either<A,C>>,Either<B,D>> append(PUnionTypeable<A,B> ta, PUnionTypeable<C,D> tb) {
        OrdConst<A,B> ordA = OrdConst.narrow(ta.run(this));
        OrdConst<C,D> ordB = OrdConst.narrow(tb.run(this));
        Ord<Either<A,C>> ord = (a, b) ->
            a.<Ordering>either(
                (A x1) ->
                    b.<Ordering>either(
                        (A x2) -> ordA.ord().cmp(x1, x2),
                        unused -> Ordering.LT
                    ),
                (C x1) ->
                    b.<Ordering>either(
                        unused -> Ordering.GT,
                        (C x2) -> ordB.ord().cmp(x1, x2)
                    )
            );
        return OrdConst.of(ord);
    }

    @Override
    default <A,B> __<__<OrdConst.Mu,A>,B> appendMany(List<UnionElement<A,B,?,?>> unionElements) {
        if (unionElements.isEmpty()) {
            throw new RuntimeException("unionElements can not be empty");
        }
        class Util {
            <C,D> Maybe<Ordering> getOrderingOp(UnionElement<A,B,C,D> unionElement, A a, A b) {
                Maybe<C> aOp = unionElement.prism().getMaybe(a);
                Maybe<C> bOp = unionElement.prism().getMaybe(b);
                if (aOp.isNothing()) {
                    if (bOp.isNothing()) {
                        return Maybe.Nothing();
                    } else {
                        return Maybe.Just(Ordering.LT);
                    }
                } else {
                    if (bOp.isNothing()) {
                        return Maybe.Just(Ordering.GT);
                    } else {
                        return Maybe.Just(OrdConst.narrow(unionElement.typeable().run(OrdConstProfunctorTypeable.instance)).ord().cmp(aOp.get(), bOp.get()));
                    }
                }
            }
        }
        final Util util = new Util();
        return OrdConst.of(
            (A a, A b) -> {
                for (UnionElement<A,B,?,?> unionElement : unionElements) {
                    for (Ordering ordering : util.getOrderingOp(unionElement, a, b)) {
                        return ordering;
                    }
                }
                throw new RuntimeException("Partern match incomplete");
            }
        );
    }
}