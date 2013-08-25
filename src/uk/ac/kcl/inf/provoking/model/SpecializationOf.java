package uk.ac.kcl.inf.provoking.model;

public class SpecializationOf extends Description {
    private Entity _specificEntity;
    private Entity _generalEntity;
    
    public SpecializationOf (Entity specificEntity, Entity generalEntity) {
        _specificEntity = specificEntity;
        _generalEntity = generalEntity;
    }

    public Entity getSpecificEntity () {
        return _specificEntity;
    }

    public void setSpecificEntity (Entity specificEntity) {
        _specificEntity = specificEntity;
    }

    public Entity getGeneralEntity () {
        return _generalEntity;
    }

    public void setGeneralEntity (Entity generalEntity) {
        _generalEntity = generalEntity;
    }

    @Override
    public String toString () {
        return toString (this, null, new String[] {"general entity", "specific entity"},
                         _generalEntity, _specificEntity);
    }
}
