# kNN-Seminar
Gruppenmitglieder: Marcel Koriath, Lars Niederweis, Justin Emmerich

Updates vom 25.10.2023: <br>
- Switch Aktivierungsfunktion anpassen + Methodenname ändern sigmoid -> log <br>
- Aktivierungsfunktion an einzelne Neuronen hängen - nicht global <br>
- Bias-Neuronen (=1) implementieren -> Anpassen aller Methoden z.B. initNetz, RandomGewichte <br>
- getInputNeuronCount() und getHiddenNeuronCount() - -1 für Bias-Neuron je Layer -> Zählt nicht als InputNeuron - Hilfsmethode weg und dafür ersetzen durch Array-Ausdruck <br>
- RandomGewichte(double[][][] gewichte) Methode <br>
- ausgabeOutput() als Art von toString() definiert <br>
- Anhand der Excel-Tabelle und den angegeben Werten (Gewichtsdatei), Berechnungen unseres KNN mit Excel-Werte vergleichen (log = Aktivierungsfunktion) <br>
- Github-Dateien und Ordnerstrukturen aktualisiert <br>
- UML-Diagramm angepasst und v2 exportiert <br>
