package uk.ac.kcl.inf.provoking.visualise;

import uk.ac.kcl.inf.provoking.model.Description;

class Placement {
    int _x, _y;
    int _width, _height;
    final Description _description;
    final PlacementType _type;
    
    Placement (Description description, int x, int y, PlacementType type) {
        _description = description;
        _x = x;
        _y = y;
        _type = type;
        _width = 0;
        _height = 0;
    }
}
