package Zoo.animal;

public sealed interface Animal permits Mammal, Fish, Reptile, Bird, Animal.Ant, Animal.Butterfly {
  String name();

  record Ant(String name) implements Animal {}

  record Butterfly(String name) implements Animal {}
}
