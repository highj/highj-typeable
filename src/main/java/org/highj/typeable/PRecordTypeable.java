package org.highj.typeable;

import org.derive4j.hkt.__;
import org.derive4j.hkt.__2;
import org.highj.data.tuple.T2;
import org.highj.data.tuple.T3;
import org.highj.data.tuple.T4;

import java.util.function.Function;

public interface PRecordTypeable<A,B> extends __2<PRecordTypeable.Mu,A,B> {
    enum Mu {}

    <F_> __<__<F_,A>,B> run(ProfunctorRecordTypeable<F_> context);

    default <C,D> PRecordTypeable<C,D> dimap(Function<C,A> f, Function<B,D> g) {
        return new PRecordTypeable<C,D>() {
            @Override
            public <F_> __<__<F_,C>,D> run(ProfunctorRecordTypeable<F_> context) {
                return context.dimap(f, g, PRecordTypeable.this.run(context));
            }
        };
    }

    static <A,B> PRecordTypeable<A,B> singleton(String tag, PTypeable<A,B> type) {
        return new PRecordTypeable<A,B>() {
            @Override
            public <F_> __<__<F_,A>,B> run(ProfunctorRecordTypeable<F_> context) {
                return context.singleton(tag, type);
            }
        };
    }

    default PRecordTypeable<A,B> defaultValue(B a) {
        return new PRecordTypeable<A,B>() {
            @Override
            public <F_> __<__<F_,A>,B> run(ProfunctorRecordTypeable<F_> context) {
                return context.defaultValue(PRecordTypeable.this, a);
            }
        };
    }

    static <A,B,C,D> PRecordTypeable<T2<A,C>,T2<B,D>> append(PRecordTypeable<A,B> ta, PRecordTypeable<C,D> tb) {
        return new PRecordTypeable<T2<A,C>,T2<B,D>>() {
            @Override
            public <F_> __<__<F_,T2<A,C>>,T2<B,D>> run(ProfunctorRecordTypeable<F_> context) {
                return context.append(ta, tb);
            }
        };
    }

    static <A,B,C,D> PRecordTypeable<T2<A,C>,T2<B,D>> t2(PRecordTypeable<A,B> ta, PRecordTypeable<C,D> tb) {
        return append(ta, tb);
    }

    static <A,B,C,D,E,F> PRecordTypeable<T3<A,C,E>,T3<B,D,F>> t3(PRecordTypeable<A,B> ta, PRecordTypeable<C,D> tb, PRecordTypeable<E,F> tc) {
        return t2(ta, t2(tb, tc)).dimap(
            (T3<A,C,E> x) -> T2.of(x._1(), T2.of(x._2(), x._3())),
            (T2<B,T2<D,F>> x) -> T3.of(x._1(), x._2()._1(), x._2()._2())
        );
    }

    static <A,B,C,D,E,G,H,I> PRecordTypeable<T4<A,C,E,H>,T4<B,D,G,I>> t4(PRecordTypeable<A,B> ta, PRecordTypeable<C,D> tb, PRecordTypeable<E,G> tc, PRecordTypeable<H,I> td) {
        return t2(ta, t3(tb, tc, td)).dimap(
            (T4<A,C,E,H> x) -> T2.of(x._1(), T3.of(x._2(), x._3(), x._4())),
            (T2<B,T3<D,G,I>> x) -> T4.of(x._1(), x._2()._1(), x._2()._2(), x._2()._3())
        );
    }
}
