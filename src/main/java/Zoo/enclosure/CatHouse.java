package Zoo.enclosure;

import Zoo.animal.Cat;

public class CatHouse <T extends Cat> extends Enclosure{
    public CatHouse(String name){
        super(name);
    }
}
