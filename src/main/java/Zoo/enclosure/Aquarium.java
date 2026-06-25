package Zoo.enclosure;

import Zoo.animal.Fish;

public class Aquarium <T extends Fish> extends Enclosure{
    public Aquarium(String name){
        super(name);
    }
}
