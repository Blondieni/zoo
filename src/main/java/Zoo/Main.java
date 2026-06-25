package Zoo;

import Zoo.animal.Cat.Siamese;
import Zoo.animal.Fish.Goldfish;
import Zoo.enclosure.Aquarium;
import Zoo.enclosure.CatHouse;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
  public static void main(String[] args) {
    // 1. Logger für die Klasse Zoo konfigurieren
    Logger zooLogger = Logger.getLogger(Zoo.class.getName());
    zooLogger.setUseParentHandlers(false); // Verhindert doppelte Ausgaben durch den Basis-Logger

    ConsoleHandler handler = new ConsoleHandler();
    handler.setFormatter(new SimpleFormatter());
    zooLogger.addHandler(handler);

    // --- HIER LOG-LEVEL UMSCHALTEN ---
    // Level.INFO zeigt keine FINE-Logs. Level.ALL zeigt absolut alles an.
    Level targetLevel = Level.ALL;
    zooLogger.setLevel(targetLevel);
    handler.setLevel(targetLevel);

    System.out.println("=== ZOO DEMO START (Log-Level: " + targetLevel.getName() + ") ===");

    // 2. Zoo-Instanz erstellen
    Zoo zoo = new Zoo();

    // Provoziert absichtlich einen SEVERE-Log (null-Gehege)
    zoo.addEnclosure(null);

    // Gehege erstellen und mit Tieren befüllen
    CatHouse<Siamese> katzengehege = new CatHouse<>("katzengehege");
    katzengehege.add(new Siamese("Simba"));
    katzengehege.add(new Siamese("Mufasa"));
    zoo.addEnclosure(katzengehege);

    Aquarium<Goldfish> goldfishglas = new Aquarium<>("goldfishglas");
    goldfishglas.add(new Goldfish("Goldi"));
    zoo.addEnclosure(goldfishglas);

    // 3. Methoden testen und Log-Verhalten beobachten
    zoo.findEnclosureByName("katzengehege"); // Findet das Gehege -> FINE Log
    zoo.findEnclosureByName("Vogelkäfig"); // Findet nichts -> WARNING Log

    System.out.println("\nZusammenfassung des Zoos:");
    System.out.println(zoo.summary());
  }
}
