package org.highj.typeable.ord;

import org.derive4j.hkt.__;
import org.highj.data.ord.Ord;
import org.highj.typeable.OrdConst;
import org.highj.typeclass2.profunctor.Profunctor;

import java.util.function.Function;

public interface OrdConstProfunctor extends Profunctor<OrdConst.Mu> {
    @Override
    default <A, B, C, D> __<__<OrdConst.Mu, A>, D> dimap(Function<A, B> f, Function<C, D> g, __<__<OrdConst.Mu, B>, C> p) {
        return OrdConst.of(
            Ord.contravariant.contramap(f, OrdConst.narrow(p).ord())
        );
    }
}
