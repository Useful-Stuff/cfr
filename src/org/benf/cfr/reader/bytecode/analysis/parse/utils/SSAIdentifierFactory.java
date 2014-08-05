package org.benf.cfr.reader.bytecode.analysis.parse.utils;

import org.benf.cfr.reader.util.MapFactory;
import org.benf.cfr.reader.util.functors.UnaryFunction;

import java.util.Map;

public class SSAIdentifierFactory<KEYTYPE> {
    private final Map<KEYTYPE, Integer> nextIdentFor = MapFactory.newLazyMap(
            MapFactory.<KEYTYPE, Integer>newLinkedMap(),
            new UnaryFunction<KEYTYPE, Integer>() {
                @Override
                public Integer invoke(KEYTYPE ignore) {
                    return 0;
                }
            });

    private final UnaryFunction<KEYTYPE, Object> typeComparisonFunction;

    public SSAIdentifierFactory(UnaryFunction<KEYTYPE, Object> typeComparisonFunction) {
        this.typeComparisonFunction = typeComparisonFunction;
    }

    public SSAIdent getIdent(KEYTYPE lValue) {
        int val = nextIdentFor.get(lValue);
        nextIdentFor.put(lValue, val + 1);
        return new SSAIdent(val, typeComparisonFunction == null ? null : typeComparisonFunction.invoke(lValue));
    }
}
