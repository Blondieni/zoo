package Zoo.animal;

public sealed interface Bird extends Animal permits Bird.Parot, Bird.Eagle {

    record Parot (String name) implements Bird{}
    record Eagle (String name) implements Bird{}
}
