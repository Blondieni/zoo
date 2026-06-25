package Zoo.animal;

public sealed interface Cat extends Mammal permits Cat.Siamese, Cat.Ragdoll {

  record Siamese(String name) implements Cat {}

  record Ragdoll(String name) implements Cat {}
}
