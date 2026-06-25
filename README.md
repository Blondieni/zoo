# Blatt 07:

Reflektion

1. Generics
* Generics sorgen dafür, dass wir in Gehegen nur erlaubte Typen speichern können. Der Compiler prüft die Typsicherheit, sodass manche Laufzeitfehler ausgeschlossen werden.
* In unserer Implementierung von CatHouse<T extends Cat>. Wenn wir ein CatHouse<Siamese> instanziieren, verhindert der Typchecker sofort, dass wir per .add einen anderen Typ in dieses Gehege stecken.

2. Logging
* Logger können ein und ausgeschaltet werden und werden nach ihrer wichtigkeit differenziert außerdem liefern sie Metadaten wie Klassennamen oder Zeitstempel und ist dann gegenüber Printouts wesentlich komfortabler.

* **Verwendung der Level in diesem System:**
    * INFO: Für den generellen Status und den Aufruf von wichtigen Geschäftslogik-Methoden (z. B. Einstieg in getAllAnimals()).
    * WARNING: Für nicht so kritische unerwartete Situationen, in denen das System weiterläuft, aber ein Problem auftrat (z. B. ein angefragtes Gehege existiert nicht in der Liste).
    * SEVERE: Für kritische Fehler, die zu schwerwiegenden Inkonsistenzen führen (z. B. wenn jemand probiert, null als Gehege in die Liste einzufügen).

3. Streams
* Besonders bei zählen von Tieren haben die Streams geholfen oder beim Filtern über alle Gehege schaffen Streams abhilfe gegenüber tiefverschachtelten Schleifen