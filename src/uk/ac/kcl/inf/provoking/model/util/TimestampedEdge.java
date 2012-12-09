package uk.ac.kcl.inf.provoking.model.util;

import java.util.Date;
import uk.ac.kcl.inf.provoking.model.Description;

public abstract class TimestampedEdge extends AttributeHolder implements Description {
    private static ValueGenerator<Date> NO_TIMESTAMP = new ValueGenerator<> (null);
    private Generator<? extends Date> _timeGenerator;
    
    protected TimestampedEdge () {
        _timeGenerator = NO_TIMESTAMP;
    }

    protected TimestampedEdge (Object identifier) {
        super (identifier);
        _timeGenerator = NO_TIMESTAMP;
    }

    public Date getTime () {
        return _timeGenerator.generateValue ();
    }

    public void setTime (Date time) {
        _timeGenerator = new ValueGenerator<> (time);
    }
    
    public void setTimeGenerator (Generator<? extends Date> generator) {
        _timeGenerator = generator;
    }
}

