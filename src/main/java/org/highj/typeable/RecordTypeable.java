package org.highj.typeable;

import org.derive4j.hkt.__;
import org.highj.data.tuple.T2;
import org.highj.function.F1;

public interface RecordTypeable<A> extends __<RecordTypeable.µ,A> {
    enum µ {}
    
    <F> __<F,A> run(InvariantRecordTypeable<F> context);
    
    default <B> RecordTypeable<B> invmap(F1<A,B> f, F1<B,A> g) {
        return new RecordTypeable<B>() {
            @Override
            public <F> __<F, B> run(InvariantRecordTypeable<F> context) {
                return context.invmap(f, g, RecordTypeable.this.run(context));
            }
        };
    }

    static <A> RecordTypeable<A> singleton(String tag, Typeable<A> type) {
        return new RecordTypeable<A>() {
            @Override
            public <F> __<F, A> run(InvariantRecordTypeable<F> context) {
                return context.singleton(tag, type);
            }
        };
    }
    
    static <A,B> RecordTypeable<T2<A,B>> append(RecordTypeable<A> ta, RecordTypeable<B> tb) {
        return new RecordTypeable<T2<A,B>>() {
            @Override
            public <F> __<F, T2<A, B>> run(InvariantRecordTypeable<F> context) {
                return context.append(ta, tb);
            }
        };
    }
}
