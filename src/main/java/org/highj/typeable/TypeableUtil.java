package org.highj.typeable;

import org.highj.data.ord.Ord;
import org.highj.typeable.ord.OrdInvariantTypeable;

public class TypeableUtil {
    
    public static <A> Ord<A> makeOrd(Typeable<A> typeable) {
        return Ord.narrow(typeable.run(OrdInvariantTypeable.instance));
    }
}
