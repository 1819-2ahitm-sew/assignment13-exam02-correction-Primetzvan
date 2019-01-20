package at.htl.bank.business;

import at.htl.bank.model.BankKonto;
import at.htl.bank.model.GiroKonto;
import at.htl.bank.model.SparKonto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Legen Sie eine statische Liste "konten" an, in der Sie die einzelnen Konten speichern
 *
 */
public class Main {


  static ArrayList<BankKonto> konten = new ArrayList<>();
  // die Konstanten sind package-scoped wegen der Unit-Tests
  static final double GEBUEHR = 0.02;
  static final double ZINSSATZ = 3.0;

  static final String KONTENDATEI = "erstellung.csv";
  static final String BUCHUNGSDATEI = "buchungen.csv";
  static final String ERGEBNISDATEI = "ergebnis.csv";


  /**
   * Führen Sie die drei Methoden erstelleKonten, fuehreBuchungenDurch und
   * findKontoPerName aus
   *
   * @param args
   */
  public static void main(String[] args) {

    erstelleKonten(KONTENDATEI);
    fuehreBuchungenDurch(BUCHUNGSDATEI);
    schreibeKontostandInDatei(ERGEBNISDATEI);

  }

  /**
   * Lesen Sie aus der Datei (erstellung.csv) die Konten ein.
   * Je nach Kontentyp erstellen Sie ein Spar- oder Girokonto.
   * Gebühr und Zinsen sind als Konstanten angegeben.
   *
   * Nach dem Anlegen der Konten wird auf der Konsole folgendes ausgegeben:
   * Erstellung der Konten beendet
   *
   * @param datei KONTENDATEI
   */
  private static void erstelleKonten(String datei) {

    String neueskonto;
    //String neuekonten = new StringBuilder();

    try(Scanner sc = new Scanner(new FileReader(datei))) {
      sc.nextLine();
      while (sc.hasNextLine()){
        //neuekonten += sc.nextLine();
        neueskonto = sc.nextLine();
        String[] split = neueskonto.split(";");
        if(split[0].equals("Girokonto")){
          konten.add(new GiroKonto(split[1], Double.parseDouble(split[2]),GEBUEHR));
        }else if(split[0].equals("Sparkonto")){
          konten.add(new SparKonto(split[1], Double.parseDouble(split[2]), ZINSSATZ));
        }

      }

      System.out.println("Erstellung der Konten beendet");

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Die einzelnen Buchungen werden aus der Datei eingelesen.
   * Es wird aus der Liste "konten" jeweils das Bankkonto für
   * kontoVon und kontoNach gesucht.
   * Anschließend wird der Betrag vom kontoVon abgebucht und
   * der Betrag auf das kontoNach eingezahlt
   *
   * Nach dem Durchführen der Buchungen wird auf der Konsole folgendes ausgegeben:
   * Buchung der Beträge beendet
   *
   * Tipp: Verwenden Sie hier die Methode 'findeKontoPerName(String name)'
   *
   * @param datei BUCHUNGSDATEI
   */
  private static void fuehreBuchungenDurch(String datei) {

    String buchungen;


    try(Scanner sc = new Scanner(new FileReader(datei))) {
      sc.nextLine();
      while (sc.hasNextLine()){
        buchungen = sc.nextLine();
        String[] split = buchungen.split(";");
        findeKontoPerName(split[0]).auszahlen(Double.parseDouble(split[2]));
        findeKontoPerName(split[1]).einzahlen(Double.parseDouble(split[2]));
      }

      System.out.println("Buchung der Beträge beendet");

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  /**
   * Es werden die Kontostände sämtlicher Konten in die ERGEBNISDATEI
   * geschrieben. Davor werden bei Sparkonten noch die Zinsen dem Konto
   * gutgeschrieben
   *
   * Die Datei sieht so aus:
   *
   * name;kontotyp;kontostand
   * Susi;SparKonto;875.5
   * Mimi;GiroKonto;949.96
   * Hans;GiroKonto;1199.96
   *
   * Vergessen Sie nicht die Überschriftenzeile
   *
   * Nach dem Schreiben der Datei wird auf der Konsole folgendes ausgegeben:
   * Ausgabe in Ergebnisdatei beendet
   *
   * @param datei ERGEBNISDATEI
   */
  private static void schreibeKontostandInDatei(String datei) {

    try(PrintWriter writer = new PrintWriter(new FileWriter(datei))) {
          writer.println("name;kontotyp;kontostand");
      for (int i = 0; i < konten.size(); i++) {
        if (konten.get(i) instanceof SparKonto ){
          ((SparKonto) konten.get(i)).zinsenAnrechnen();
          writer.println(konten.get(i).getName() + ";SparKonto;" + konten.get(i).getKontoStand());
        } else if(konten.get(i) instanceof GiroKonto){
          writer.println(konten.get(i).getName() + ";GiroKonto;" + konten.get(i).getKontoStand());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

      System.out.println("Ausgabe in Ergebnisdatei beendet");

  }

  /**
   */
  /**
   * Durchsuchen Sie die Liste "konten" nach dem ersten Konto mit dem als Parameter
   * übergebenen Namen
   * @param name
   * @return Bankkonto mit dem gewünschten Namen oder NULL, falls der Namen
   *         nicht gefunden wird
   */
  public static BankKonto findeKontoPerName(String name) {
    for (BankKonto k : konten){

      if (name.equals(k.getName())){
        return k;
      }

    }

    return null;
  }

}

