package uk.ac.kcl.inf.provoking.model;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import uk.ac.kcl.inf.provoking.model.util.AttributeHolder;
import uk.ac.kcl.inf.provoking.model.util.Generator;
import uk.ac.kcl.inf.provoking.model.util.ValueGenerator;

abstract class TimestampedEdge extends AttributeHolder implements Description {
    private static ValueGenerator<Date> NO_TIMESTAMP = new ValueGenerator<> (null);
    private Generator<? extends Date> _timeGenerator;
    
    TimestampedEdge () {
        _timeGenerator = NO_TIMESTAMP;
    }

    TimestampedEdge (Date time) {
        _timeGenerator = new ValueGenerator<> (time);
    }

    public Date getTime (Map<String, Object> context) {
        return _timeGenerator.generateValue (context);
    }

    public Date getTime () {
        return getTime (Collections.EMPTY_MAP);
    }

    public void setTime (Date time) {
        _timeGenerator = new ValueGenerator<> (time);
    }
    
    public void setTimeGenerator (Generator<? extends Date> generator) {
        _timeGenerator = generator;
    }
}

