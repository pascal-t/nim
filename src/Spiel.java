
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Pascal Tänzer
 */
public class Spiel {

private final static Scanner scan = new Scanner(System.in);
private final static Random  rand = new Random();
private final static int fensterBreite = 80; //standard anzahl von Zeichen in einem cmd Fenster
private final static int fensterHoehe = 25;  //standard anzahl von Zeichen in einem cmd Fenster

    public static void main(String[] args) {
        int auswahl = 0;
        String nochmal = "ja";
        
        while (nochmal.equalsIgnoreCase("ja") ||
               nochmal.equalsIgnoreCase("j") ||
               nochmal.equalsIgnoreCase("yes") ||
               nochmal.equalsIgnoreCase("y")) {
            //Auswahl
            System.out.print("Nim Spiel:"+
            "\nVariante 1:"+
            "\n  2 Spieler"+
            "\n  Jeder Spiler nimmt solange 1-3 Steine von einem Stapel, "+ 
            "\n  bis keine mehr übrig sind."+
            "\n  Wer den letzten Stein nimmt hat gewonnen."+
          "\n\nVariante 2"+
            "\n  Spieler 2 wird durch einen Bot ersetzt, der zufällig Steine weg nimmt."+
          "\n\nVariante 3"+
            "\n  Spieler 2 wird durch einen Bot ersetzt, der eine Gewinnstrategie verfolgt"+
          "\n\nVariante 4"+
            "\n  2 Spieler"+
            "\n  Es wird eine Anzahl von Reihen festgelegt, in denen unterschiedlich "+ 
            "\n  viele Steine sein können. Jeder Spiler nimmt solange beliebig viele "+
            "\n  Steine aus einer Reihe, bis keine mehr übrig sind."+
            "\n  Wer den letzten Stein nimmt hat gewonnen."+
          "\n\nGeben sie die Variante an, die sie Spielen möchten (0 beendet das Spiel): ");
            auswahl = getInt(0, 4);

            if( 1 == auswahl ) {
                zweiSpieler();
            }
            else if( 2 == auswahl ) {
                botZufall();
            }
            else if ( 3 == auswahl ) {
                botStrategie();
            }
            else if ( 4 == auswahl ) {
                zweiSpielerReihen();
            }
            else {
                System.exit(0);
            }
            
            System.out.print("Wollen sie noch einmal Spielen? ");
            nochmal = scan.nextLine();
            clear();
        }
    }
    
    
//<editor-fold defaultstate="collapsed" desc="2 Spieler Modus">
    private static void zweiSpieler() {
        clear();
        //Variablen initialisieren
        int steine = 0;
        String spieler1 = "";
        String spieler2 = ""; 
        String anDerReihe = "";
        
        //Spielernamen abfragen
        System.out.print("Spieler 1 wie ist dein Name? ");
        spieler1 = scan.nextLine();
        System.out.print("Spieler 2 wie ist dein Name? ");
        spieler2 = scan.nextLine();
        if( spieler1.equals(" ") ){
            spieler1 = "Spieler 1";
        }
        if( spieler2.equals(" ") ){
            spieler2 = "Spieler 2";
        }
        
        //Zahl Zwischen 0 und 10000 holen für die steine
        //10000 weil ich eine Obergrenze angeben muss
        System.out.print("Bitte geben sie die Anzahl der Steine an: ");
        steine = getInt(0,10000);
        
        //solange noch Steine übrig sind
        while (steine > 0) {
            clear();
            //Spielrtausch:
            //wenn spieler1 an der Reihe war
            if (anDerReihe.equals(spieler1) ) {
                //ist jetzt spieler2 an der Reihe
                anDerReihe = spieler2;
            }
            else {
                //sonst ist spieler1 an der Reihe
                anDerReihe = spieler1;
            }
            
            //der Zug ist in einer seperaten Methode
            steine = nimmEineReihe(anDerReihe, steine);
        }
        clear();
        //den Gewinner bekannt geben
        System.out.println(anDerReihe+" hat gewonnen!");
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Bot, der zufällig viele Steine nimmt">
    private static void botZufall() {
        clear();
        //vatriablen initialisieren
        int steine = 0;
        int bot = 0;
        String spieler = "";
        String anDerReihe = "";
        
        //Spielernamen abfragen
        System.out.print("Wie ist dein Name? ");
        spieler = scan.nextLine();
        if( spieler.equals(" ") ){
            spieler = "Spieler";
        }
        
        //Zahl Zwischen 0 und 10000 holen für die steine
        //10000 weil ich eine Obergrenze angeben muss
        System.out.print("Bitte geben sie die Anzahl der Steine an: ");
        steine = getInt(0,10000);
       
        clear();
        //solange noch Steine da sind
        while (steine > 0) { 
            //erst den Spieler
            anDerReihe = spieler;
            steine = nimmEineReihe(spieler, steine);
            clear();
            
            //und wenn dann noch Steine übrig sind, den Bot ziehen lassen.
            if ( steine > 0) {
                anDerReihe = "Der Bot";
                //der Bot nimmt eine zufällige Anzahl von Steinen zwischen 1 und 3
                bot = rand.nextInt(2) +1;
                steine = steine - bot;
                System.out.println("Der Bot hat "+bot+" Steine weg genommen.");
            }
        }
        clear();
        //Ausgabe des Gewinners
        System.out.println(anDerReihe+" hat gewonnen!");
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Bot der eine Gewinnstrategie verfolgt">
    private static void botStrategie() {
        clear();
        //Variablen initialisieren
        int steine = 0;
        int bot = 0;
        String spieler = "";
        String anDerReihe = "";
        
        //Spielernamen abfragen
        System.out.print("Wie ist dein Name? ");
        spieler = scan.nextLine();
        if( spieler.equals(" ") ){
            spieler = "Spieler";
        }
        
        //Zahl Zwischen 0 und 10000 holen für die steine
        //10000 weil ich eine Obergrenze angeben muss
        System.out.print("Bitte geben sie die Anzahl der Steine an: ");
        steine = getInt(0,10000);
       
        clear();
        //solange noch Steine übrig sind
        while (steine > 0) { 
            //nimmt erst spieler Steine weg
            anDerReihe = spieler;
            steine = nimmEineReihe(anDerReihe, steine);
            
            clear();
            
            //und sollten immer noch welche übrig bleiben ist der Bot an der Reihe
            if ( steine > 0) {
                anDerReihe = "Der Bot";
                
                /*
                die Gewinnstrategie ist, dass man immer so viele Steine weg nimmt, dass man
                auf ein vielfaches von 4 kommt.
                Erklärung:
                    wenn man 4 Steine übrig lässt kann der andere Spieler:
                        1 Stein  weg nehmen -> 3 für dich übrig -> du gewinnst
                        2 Steine weg nehmen -> 2 für dich übrig -> du gewinnst
                        3 Steine weg nehmen -> 1 für dich übrig -> du gewinnst
                    da es keine andere, als diese 3 Möglichkeiten gibt, gewinnt man auf 
                    jeden Fall, wenn man 4 Steine übrig lässt.
                
                    Um garantiert auf die möglichkeit Haben zu können 4 Steiene übrig zu
                    lassen, lässt man zu den letzen 4 noch weitere 4 liegen (4+4=8).
                
                    Um garantiert auf die 8 Steine zu kommen muss man weitere 4 Steine 
                    übrig lassen (8+4=12), und so wieter... also immer Vielfache von 4
                
                Um genau of ein Vielfaches von 4 zu kommen nimmt mal also den Rest von
                einer Division durch 4 weg (z.B. 23:4 = 5 Rest 3 -> man nimmt 3 Steine weg).
                Den Rest einer Division bekommt man in Java mit den Modulo (%) 
                (also z.B. 23%4 = 3).
                
                Wenn es aber genau auf geht (z.B. 20%4 = 0), soll der Bot das Spiel 
                herauszögern, und nur einen Stein weg nehmen.
                */
                
                //prüfen ob man die Gewinnstrategie verfolgen kann
                if ( 0 == steine%4 ) { //in diesem fall nicht
                    bot = 1; //nur einen Stein weg nehmen
                }
                else {
                    bot = steine%4; //ansonsten die Gewinnstrategie Verfolgen.
                }
                System.out.println(anDerReihe+" hat "+bot+" Steine weg genommen.");
            }
        }
        clear();
        //den Gewinner bekannt geben
        System.out.println(anDerReihe+" hat gewonnen!");
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Modus mit mehreren Reihen">
    private static void zweiSpielerReihen() {
        clear();
        //Variablen initialisieren
        ArrayList<Integer> steine = new ArrayList<Integer>();
        /*
        eine ArrayList ist eine Liste, zu der mit .add(eineZahl) ein Element hinzugefügt
        werden kann, und mit .remove(indexVomElemnt) etwas entfert werden kann.
        
        Das erste hinzugefügte Element bekommt den Index 0, das 2te den Index 1, ...
        Wenn ein Element entfert wird, rücken die nachfolgenden auf.
        Mit .size() kann man die Anzahl der Elemente abfragen und .isEmpty() gibt true
        zurück, wenn die ganze Liste leer ist und false wenn nicht.
        
        Außerdem bekommt man mit .get(indexVomElement) den Wert an dem Index, und mit
        .set(indexVomElement, eineZahl) den Wert an einem Index überschreiben
        */
        int eingabe = 0;
        int reihen = 0;
        int eingabeReihe = 0;
        String spieler1 = "";
        String spieler2 = ""; 
        String anDerReihe = "";
        String symbol = "* ";//hier kannst du dein eigenes Symbol sezen
        int maxWiederholungen = 0;
        
        //die maximale Anzahl von Steinen die ausgegeben werden können berechnet sich aus
        //der Fensterbreite - die Anzahl der buchstaben in "Reihe xx: " und "..." / länge des Symbols 
        maxWiederholungen = (fensterBreite-13)/symbol.length();
        
        //Spielernamen abfragen
        System.out.print("Spieler 1 wie ist dein Name? ");
        spieler1 = scan.nextLine();
        System.out.print("Spieler 2 wie ist dein Name? ");
        spieler2 = scan.nextLine();
        if( spieler1.equals(" ") ){
            spieler1 = "Spieler 1";
        }
        if( spieler2.equals(" ") ){
            spieler2 = "Spieler 2";
        }
        
        //die Anzahl der Reihen abfragen
        System.out.print("Bitte geben sie die Anzahl der Reihen an: ");
        reihen = getInt(1,50);
        
        //jetzt wird mit einer Zählschleife die Liste befüllt.
        int zaehler = 0;
        while ( zaehler < reihen ) {
            System.out.print("Bitte geben sie die Anzahl der Steine in Reihe "+(zaehler+1)+" an: ");
            //hier wird besagte .add(eineZahl) Methode aufgerufen, und als Zahl der 
            //Rückgabewert von getInt() übergeben.
            steine.add( getInt(0, 10000) );
            zaehler = zaehler + 1;
        }
        //jetzt speichert die Liste steine die anzahl der Steine in jeder Reihe. 
        //mit steine.get(0) bekommt man also die Anzahl von Steinen in der ersten Reihe,
        //und mit steine.set(0, eineZahl) überschreibt man sie.
        
        
        //solange die Liste steine NICHT (das !) leer ist
        while ( !steine.isEmpty() ) {
            //Spieler tauschen 
            if (anDerReihe.equals(spieler1) ) { //wenn spieler1 an der reihe war
                anDerReihe = spieler2; //ist spieler2 an der Reihe
            }
            else { //sonst
                anDerReihe = spieler1; //ist spieler1 an der Reihe
            }
            
            //Steine ausgeben
            //mit der ersten Zählschleife werden die Reihen durchlaufen.
            zaehler = 0;
            while ( zaehler < steine.size() ) {
                System.out.print("Reihe "+(zaehler+1)+": ");
                
                //mit der 2ten Zählschleife in der Reihen-Schleife werden die Steine ausgegeben
                int zaehler2 = 0;
                while ( zaehler2 < steine.get(zaehler) && zaehler2 <= maxWiederholungen) {
                    //wenn die maximale Anzahl der wiederholungen erreicht sind
                    // und man noch nciht fertig mit der ausgabe ist , wird "..." Ausgegeben
                    //um Zeilenumbrüche in der Konsole zu vermeiden.
                    if(zaehler2 == maxWiederholungen) {
                        System.out.print("...");
                    }
                    else {
                        System.out.print(symbol); // sonst gibt man das Symbol aus
                    }
                    
                    zaehler2 = zaehler2 + 1;
                }
                
                //am Ende jeder Reihe muss eine Leerzeile ausgegeben werden, weil
                //System.out.print(Text) den Text nur anhängt. 
                System.out.println("");
                
                zaehler = zaehler +1;
            }
            
            //während dem Zug wird zuerst die Reihe ausgewählt,
            System.out.println(anDerReihe + " ist an der Reihe.");
            eingabe = 0;
            while(eingabe == 0) {
                System.out.print("Aus welcher Reihe möchtest du Steine entfernen? ");
                eingabeReihe = getInt(1, steine.size());
                //und dann die Anzahl der Steine, die man weg nehmen möchte abgefragt.
                System.out.println("In der "+eingabeReihe+". Reihe sind noch "+steine.get(eingabeReihe-1)+" Steine übrig.");
                System.out.print("Wie viele Steine möchtest du weg nehmen? (0 = andere Reihe)");
                eingabe = getInt(0,steine.get(eingabeReihe-1));
            }
            //nach dem zug wird die Reihe mit der 
            //aktuellen Anzahl (steine.get(reihe)) abzüglich der eingabe
            //überschrieben.
            steine.set(eingabeReihe-1, steine.get(eingabeReihe-1) - eingabe);
            
            clear();
            
            //wenn die Reihe geleert wurde wird sie entfernt.
            if(steine.get(eingabeReihe-1) <= 0) {
                System.out.println("Reihe " + eingabeReihe + "ist leer und wurde entfernt.");
                steine.remove(eingabeReihe-1);
            }
        }
        clear();
        //den Gewinner bekannt geben
        System.out.println(anDerReihe+" hat gewonnen!");
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="tools">
    private static int getInt(int from, int to) {
        String input = scan.nextLine();
        /*
        überprüfen mit einem Regulären Ausdruck (regex), auch eine "Programmiersprache" für sich
        solange folgendes nicht zutrifft:
        Eingabe besteht NICHT (siehe ! vor der Bedingung) aus:
        [\\+\\-] ein + oder ein -
            ? das Vorherige ein mal oder kein mal
            (schaut im Grunde ob ein Vorzeichen vorhanden ist)
        
        \\d eine Zahl (0-9)
            + das Voherige mehr als ein mal (also eine mehrstellige Zahl)
        */
        while ( ! input.matches("[\\+\\-]?\\d+") ||
                /*
                Die folgenden Befehle würden eien fehler ausgeben, wenn du keine Zahl
                angegeben hättest.
                Logische oder Bedingungen (||) funktionieren aber so, dass wenn die 
                erste Bedingung richtig ist (es also keine Zahl ist, wie oben beschrieben)
                werden die anderen Bedingungen nicht überprüft, die Befehle nicht ausgeführt,
                und es kommt nicht zum fehler.
                */
                (Integer.parseInt(input) < from //String zu int
                || Integer.parseInt(input) > to) //String zu int
                ) {
            System.out.print("Bitte geben sie eine Zahl von "+from+" bis "+to+" an! ");
            input = scan.nextLine();
        }
        
        return Integer.parseInt(input); //String zu int
    }
    
    private static void clear() {
        //mit einer Zählschleife werden ganz viele Leerzeichen ausgegeben, um den Text wegzuschieben.
        int zaehler = 0;
        while (zaehler < fensterHoehe) {
            System.out.println("");
            zaehler = zaehler+1;
        }
    }
    
    private static int nimmEineReihe(String spieler, int steine) {
        int eingabe = 0;
        String symbol = "* "; //hier kannst du das Symbol selber festlegen
        int maxWiederholungen = 0;
        
        //die maximale Anzahl von Steinen die ausgegeben werden können berechnet sich aus
        //der Fensterbreite - die Anzahl der buchstaben in "Reihe xx: " und "..." / länge des Symbols 
        maxWiederholungen = ((fensterBreite-3)/symbol.length());
        
        //gibt die Steine "graphisch" aus
        //kann auch mit einer for-Schleife gelöst weden
        int zaehler = 0;
        while ( zaehler < steine && zaehler <= maxWiederholungen) {
            //wenn die maximale Anzahl der wiederholungen erreicht sind
            // und man noch nciht fertig mit der ausgabe ist , wird "..." Ausgegeben
            //um Zeilenumbrüche in der Konsole zu vermeiden.
            if(zaehler == maxWiederholungen) {
                System.out.print("...");
            }
            else {
                System.out.print(symbol); // sonst gibt man das Symbol aus
            }
            zaehler = zaehler + 1;
        }
        System.out.println("");

        System.out.println(spieler + " ist an der Reihe. Es sind noch "+steine+" Steine übrig.");
        System.out.print("Wie viele Steine möchtest du weg nehmen? ");
        eingabe = getInt(1,3);
        steine = steine - eingabe;
        return steine;
    }
//</editor-fold>
    
}
