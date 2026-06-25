package Zoo.enclosure;

import Zoo.animal.Animal;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Enclosure<T extends Animal> {

    private final String name;
    private final Set inhabitants;

    public Enclosure(String name){
        this.name = name;
        this.inhabitants = new LinkedHashSet<>();

    }
    public boolean add(T tier){
        if (tier == null){
            return false;
        }
        else {
            return this.inhabitants.add(tier);
        }
    }

    public boolean remove (T tier){
        return  this.inhabitants.remove(tier);
    }

    public List<T> getInhabitants(){
        return List.copyOf(this.inhabitants);
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Gehege name: " + this.name + " Anzahl: " + inhabitants.size();
    }
}
