package org.highj.typeable;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.ord.Ord;
import org.highj.typeable.ord.OrdConstProfunctor;
import org.highj.typeclass2.profunctor.Profunctor;

public class OrdConst<A,B> implements __2<OrdConst.Mu,A,B> {
    public enum Mu {}

    private final Ord<A> _ord;

    private OrdConst(Ord<A> ord) {
        this._ord = ord;
    }

    public static <A,B> OrdConst<A,B> of(Ord<A> ord) {
        return new OrdConst<>(ord);
    }

    public static <A,B> OrdConst<A,B> narrow(__<__<Mu,A>,B> a) {
        return (OrdConst<A,B>)a;
    }

    public Ord<A> ord() {
        return _ord;
    }

    public static final Profunctor<Mu> profunctor = new OrdConstProfunctor() {};
}