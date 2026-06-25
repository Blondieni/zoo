package Zoo.animal;

public sealed interface Mammal extends Animal permits Primate, Rodent, Cat, Mammal.Cow, Mammal.Pig {

  record Cow(String name) implements Mammal {}

  record Pig(String name) implements Mammal {}
}
