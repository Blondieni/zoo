package Zoo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import Zoo.animal.*;
import Zoo.enclosure.Enclosure;

public class Zoo {

    // Verwaltet alle Gehege dank Wildcard flexibel
    private final List<Enclosure<? extends Animal>> enclosures = new ArrayList<>();

    // 1. Gehege hinzufügen
    public void addEnclosure(Enclosure<? extends Animal> enclosure) {
        if (enclosure != null) {
            this.enclosures.add(enclosure);
        }
    }

    // 2. Liste aller Gehege (als unveränderliche Kopie)
    public List<Enclosure<? extends Animal>> getEnclosures() {
        return List.copyOf(this.enclosures);
    }

    // 3. Gehege nach Namen suchen (Nutzt Optional statt null)
    public Optional<Enclosure<? extends Animal>> findEnclosureByName(String name) {
        return this.enclosures.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst(); // Liefert automatisch ein Optional zurück
    }

    // 4. Alle Tiere aus allen Gehegen flachklopfen
    public List<Animal> getAllAnimals() {
        return this.enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream().map(Animal.class::cast)) // Aus Stream<List<T>> wird Stream<Animal>
                .toList();
    }

    // 5. Alle Säugetiere filtern und sicher casten
    public List<Mammal> getAllMammals() {
        return this.enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .filter(Mammal.class::isInstance) // Prüft, ob Untertyp von Mammal
                .map(Mammal.class::cast)         // Sicherer Cast zu Mammal
                .toList();
    }

    // 6. Tiere filtern nach einem beliebigen Prädikat
    public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
        return this.enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream().map(Animal.class::cast))
                .filter(predicate)
                .toList();
    }

    // 7. Zählen pro konkreter Record-Klasse
    public Map<Class<? extends Animal>, Long> countAnimalsByType() {
        return this.enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .collect(Collectors.groupingBy(Animal::getClass, Collectors.counting()));
    }

    // 8. Überfüllte Gehege finden
    public List<Enclosure<? extends Animal>> getOvercrowdedEnclosures(int maxInhabitants) {
        return this.enclosures.stream()
                .filter(e -> e.getInhabitants().size() > maxInhabitants)
                .toList();
    }

    // 9. Textuelle Zusammenfassung unter Verwendung von Switch-Expressions
    public String summary() {
        long totalAnimals = getAllAnimals().size();

        // Gruppierung nach den übergeordneten Interface-Kategorien
        Map<String, Long> categoryCounts = this.enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .collect(Collectors.groupingBy(animal -> switch (animal) {
                    case Mammal m  -> "Mammals";
                    case Bird b    -> "Birds";
                    case Fish f    -> "Fish";
                    case Reptile r -> "Reptiles";
                    default        -> "Other";
                }, Collectors.counting()));

        long mammals = categoryCounts.getOrDefault("Mammals", 0L);
        long birds = categoryCounts.getOrDefault("Birds", 0L);
        long fish = categoryCounts.getOrDefault("Fish", 0L);
        long reptiles = categoryCounts.getOrDefault("Reptiles", 0L);

        return String.format("Zoo mit %d Gehegen und %d Tieren: %d Mammals, %d Birds, %d Fish, %d Reptiles",
                this.enclosures.size(), totalAnimals, mammals, birds, fish, reptiles);
    }
}