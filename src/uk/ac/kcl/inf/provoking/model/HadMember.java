package uk.ac.kcl.inf.provoking.model;

public class HadMember implements Description {
    private Entity _collection;
    private Entity _member;
    
    public HadMember (Entity collection, Entity member) {
        _collection = collection;
        _member = member;
    }

    public Entity getCollection () {
        return _collection;
    }

    public void setCollection (Entity alternateA) {
        _collection = alternateA;
    }

    public Entity getMember () {
        return _member;
    }

    public void setMember (Entity alternateB) {
        _member = alternateB;
    }
}
