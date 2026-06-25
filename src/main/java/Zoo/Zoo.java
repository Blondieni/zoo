package Zoo;

import Zoo.animal.*;
import Zoo.enclosure.Enclosure;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Zoo {

  // Logger für diese Klasse instanziieren
  private static final Logger LOG = Logger.getLogger(Zoo.class.getName());

  // Verwaltet alle Gehege dank Wildcard flexibel
  private final List<Enclosure<? extends Animal>> enclosures = new ArrayList<>();

  // 1. Gehege hinzufügen
  public void addEnclosure(Enclosure<? extends Animal> enclosure) {
    LOG.info(() -> "Aufruf: addEnclosure()");
    if (enclosure == null) {
      LOG.severe("Fehler: Es wurde versucht, ein null-Gehege hinzuzufügen!");
      return;
    }
    this.enclosures.add(enclosure);
    LOG.fine(
        () -> "Erfolg: Gehege hinzugefügt. Anzahl Gehege im Zoo jetzt: " + this.enclosures.size());
  }

  // 2. Liste aller Gehege (als unveränderliche Kopie)
  public List<Enclosure<? extends Animal>> getEnclosures() {
    LOG.info(() -> "Aufruf: getEnclosures()");
    List<Enclosure<? extends Animal>> copy = List.copyOf(this.enclosures);
    LOG.fine(() -> "Erfolg: " + copy.size() + " Gehege zurückgegeben.");
    return copy;
  }

  // 3. Gehege nach Namen suchen (Nutzt Optional statt null)
  public Optional<Enclosure<? extends Animal>> findEnclosureByName(String name) {
    LOG.info(() -> "Aufruf: findEnclosureByName(name='" + name + "')");
    Optional<Enclosure<? extends Animal>> result =
        this.enclosures.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst();

    if (result.isPresent()) {
      LOG.fine(() -> "Erfolg: Gehege '" + name + "' gefunden.");
    } else {
      LOG.warning(() -> "Warnung: Gehege mit dem Namen '" + name + "' wurde nicht gefunden.");
    }
    return result;
  }

  // 4. Alle Tiere aus allen Gehegen flachklopfen
  public List<Animal> getAllAnimals() {
    LOG.info(() -> "Aufruf: getAllAnimals()");
    List<Animal> animals =
        this.enclosures.stream()
            .flatMap(e -> e.getInhabitants().stream().map(Animal.class::cast))
            .toList();
    LOG.fine(() -> "Erfolg: Insgesamt " + animals.size() + " Tiere gefunden.");
    return animals;
  }

  // 5. Alle Säugetiere filtern und sicher casten
  public List<Mammal> getAllMammals() {
    LOG.info(() -> "Aufruf: getAllMammals()");
    List<Mammal> mammals =
        this.enclosures.stream()
            .flatMap(e -> e.getInhabitants().stream())
            .filter(Mammal.class::isInstance)
            .map(Mammal.class::cast)
            .toList();
    LOG.fine(() -> "Erfolg: " + mammals.size() + " Säugetiere gefunden.");
    return mammals;
  }

  // 6. Tiere filtern nach einem beliebigen Prädikat
  public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
    LOG.info(() -> "Aufruf: getAnimalsByPredicate()");
    List<Animal> animals =
        this.enclosures.stream()
            .flatMap(e -> e.getInhabitants().stream().map(Animal.class::cast))
            .filter(predicate)
            .toList();
    LOG.fine(() -> "Erfolg: " + animals.size() + " Tiere entsprechen dem Prädikat.");
    return animals;
  }

  // 7. Zählen pro konkreter Record-Klasse
  public Map<Class<? extends Animal>, Long> countAnimalsByType() {
    LOG.info(() -> "Aufruf: countAnimalsByType()");
    Map<Class<? extends Animal>, Long> counts =
        this.enclosures.stream()
            .flatMap(e -> e.getInhabitants().stream().map(Animal.class::cast))
            .collect(Collectors.groupingBy(Animal::getClass, Collectors.counting()));
    LOG.fine(
        () -> "Erfolg: Zählung für " + counts.size() + " verschiedene Tierarten durchgeführt.");
    return counts;
  }

  // 8. Überfüllte Gehege finden
  public List<Enclosure<? extends Animal>> getOvercrowdedEnclosures(int maxInhabitants) {
    LOG.info(() -> "Aufruf: getOvercrowdedEnclosures(max=" + maxInhabitants + ")");
    List<Enclosure<? extends Animal>> overcrowded =
        this.enclosures.stream().filter(e -> e.getInhabitants().size() > maxInhabitants).toList();
    LOG.fine(() -> "Erfolg: " + overcrowded.size() + " überfüllte Gehege gefunden.");
    return overcrowded;
  }

  // 9. Textuelle Zusammenfassung unter Verwendung von Switch-Expressions
  public String summary() {
    LOG.info(() -> "Aufruf: summary()");
    long totalAnimals = getAllAnimals().size();

    // Gruppierung nach den übergeordneten Interface-Kategorien
    Map<String, Long> categoryCounts =
        this.enclosures.stream()
            .flatMap(e -> e.getInhabitants().stream().map(Animal.class::cast))
            .collect(
                Collectors.groupingBy(
                    animal ->
                        switch (animal) {
                          case Mammal m -> "Mammals";
                          case Bird b -> "Birds";
                          case Fish f -> "Fish";
                          case Reptile r -> "Reptiles";
                          default -> "Other";
                        },
                    Collectors.counting()));

    long mammals = categoryCounts.getOrDefault("Mammals", 0L);
    long birds = categoryCounts.getOrDefault("Birds", 0L);
    long fish = categoryCounts.getOrDefault("Fish", 0L);
    long reptiles = categoryCounts.getOrDefault("Reptiles", 0L);

    String result =
        String.format(
            "Zoo mit %d Gehegen und %d Tieren: %d Mammals, %d Birds, %d Fish, %d Reptiles",
            this.enclosures.size(), totalAnimals, mammals, birds, fish, reptiles);

    LOG.fine(() -> "Erfolg: Zusammenfassung generiert.");
    return result;
  }
}
