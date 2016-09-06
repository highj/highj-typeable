package org.highj.typeable;

import org.highj.data.ord.Ord;
import org.highj.typeable.ord.OrdConstProfunctorTypeable;

public class TypeableUtil {
    
    public static <A> Ord<A> makeOrd(Typeable<A> typeable) {
        return OrdConst.narrow(typeable.toPTypeable().run(OrdConstProfunctorTypeable.instance)).ord();
    }
}
