package uk.ac.kcl.inf.provoking.model;

public class HadMember extends Description {
    private Entity _collection;
    private Entity _member;
    
    public HadMember (Entity collection, Entity member) {
        _collection = collection;
        _member = member;
    }

    public Entity getCollection () {
        return _collection;
    }

    public void setCollection (Entity collection) {
        _collection = collection;
    }

    public Entity getMember () {
        return _member;
    }

    public void setMember (Entity member) {
        _member = member;
    }

    @Override
    public String toString () {
        return toString (this, null, new String[] {"collection", "member"},
                         _collection, _member);
    }
}
