package Zoo.animal;

public sealed interface Primate extends Mammal permits Primate.Bonbo , Primate.Gibbon {

    record Bonbo (String name) implements Primate{}
    record Gibbon (String name) implements Primate{}
}
