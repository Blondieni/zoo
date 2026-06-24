package Zoo.animal;

public sealed interface Reptile extends Animal permits Reptile.Snake, Reptile.Dino {

    record Snake (String name) implements Reptile{}
    record Dino (String name) implements Reptile{}
}
