package uk.ac.kcl.inf.provoking.model;

public class AlternateOf extends Description {
    private Entity _alternateA;
    private Entity _alternateB;
    
    public AlternateOf (Entity alternateA, Entity alternateB) {
        _alternateA = alternateA;
        _alternateB = alternateB;
    }

    public Entity getAlternateA () {
        return _alternateA;
    }

    public void setAlternateA (Entity alternateA) {
        _alternateA = alternateA;
    }

    public Entity getAlternateB () {
        return _alternateB;
    }

    public void setAlternateB (Entity alternateB) {
        _alternateB = alternateB;
    }

    @Override
    public String toString () {
        return toString (this, null, new String[] {"alternate", "alternate"},
                         _alternateA, _alternateB);
    }
}
