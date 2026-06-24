package Zoo.animal;

public sealed interface Rodent extends Mammal permits Rodent.Rabbit, Rodent.Hamster {

    record Rabbit (String name) implements Rodent{}
    record Hamster (String name) implements Rodent{}
}
