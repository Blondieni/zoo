package Zoo.enclosure;

import Zoo.animal.Mammal;

public class MammalHouse <T extends Mammal> extends Enclosure{
    public MammalHouse(String name){
        super(name);
    }
}
