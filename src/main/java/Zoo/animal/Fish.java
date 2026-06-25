package Zoo.animal;

public sealed interface Fish extends Animal permits Fish.Goldfish, Fish.Jellyfish {
  record Goldfish(String name) implements Fish {}

  record Jellyfish(String name) implements Fish {}
}
