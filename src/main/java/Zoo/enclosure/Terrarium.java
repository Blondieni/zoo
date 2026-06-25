package Zoo.enclosure;

import Zoo.animal.Reptile;

public class Terrarium<T extends Reptile> extends Enclosure {
  public Terrarium(String name) {
    super(name);
  }
}
