package sample;


import javafx.scene.control.TableView;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PripojeniKDatabazi{

    // Adresa databaze
    private final String adresa_databaze = "jdbc:mysql://127.0.0.1:3306/DatabazeNaPoznamky";
    // Jmeno od databaze
    private final String jmeno = "root";
    // Heslo od databaze
    private final String heslo = "KYkFM!vLIOO.NIh!VtO?";

    public Connection pripojitSeKDatabazi(String adresa_databaze, String jmeno, String heslo) {
        try {
            // Připojí se k databazi
            Connection pripojeni = DriverManager.getConnection(adresa_databaze, jmeno, heslo);

            // Vrátí přípojení
            return pripojeni;
        }

        catch (Exception e){
            System.out.println(e);
        }

        // Nevrátí žádnou hodnotu protože už nám to vrátilo připojení
        return null;
    }

    public void pridat_poznamku_do_databaze(String obsah_poznamky, String datum, int cislo_poznamky){
        try {
            // Vytvoření nové akce
            Statement provadeni_akce_v_databazi = pripojitSeKDatabazi(getAdresa_databaze(), getJmeno(), getHeslo()).createStatement();

            // Vložení do vybranýho table
            provadeni_akce_v_databazi.executeUpdate("INSERT INTO poznamky(CisloPoznamky, ObsahPoznamky, DatumSplneni) VALUES(" + "'" + cislo_poznamky + "'" + ", " + "'" + obsah_poznamky + "'" + ", " + "'" + datum + "'" + ")");
            System.out.println("Poznámka přidána do databáze");
        }

        catch (Exception e){
            System.out.println(e);
        }
    }

    public void odebrat_smazanou_poznamku_z_databaze(TableView<ZapsanePoznamky> aktivni_poznamky, int cislo_poznamky){
        try {
            // Obsah vybrané poznámky
            String vybrany_obsah_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getPoznamka();

            // Datum splnění vybrané poznámky
            String vybrany_datum_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getDatum();

            // Cislo vybrane poznamky
            int vybrane_cislo_poznamky = cislo_poznamky;

            // Datum odebrání poznámky
            DateFormat format_datumu = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String string_datum_odebrani = format_datumu.format(Calendar.getInstance().getTime());

            // Typ poznamky
            String typ_poznamky = "Smazana";

            // Vytvoření nové akce
            Statement provadeni_akce_v_databazi = pripojitSeKDatabazi(getAdresa_databaze(), getJmeno(), getHeslo()).createStatement();

            // Vymazání vybraného řádku v poznamkach
            provadeni_akce_v_databazi.executeUpdate("DELETE FROM poznamky WHERE ObsahPoznamky = " + "'" +  vybrany_obsah_poznamky + "'");
            System.out.println("Poznámka odstraněna z databáze");

            // Přidání odebrané poznámky do table splnenepoznamky
            provadeni_akce_v_databazi.executeUpdate("INSERT INTO splnenepoznamky VALUES(" + "'" + vybrane_cislo_poznamky + "'" + ", " + "'" + vybrany_obsah_poznamky + "'" + ", " + "'" + vybrany_datum_poznamky + "'" + ", " + "'" + string_datum_odebrani + "'" + ", " + "'" + typ_poznamky + "')");
            System.out.println("Odstraněná poznámka přidáná do table splnenepoznamky");
        }

        catch (Exception e) { System.out.println(e); }
    }

    public void odebrat_splnenou_poznamku_z_databaze(TableView<ZapsanePoznamky> aktivni_poznamky, int cislo_poznamky){
        try {
            // Obsah vybrané poznámky
            String vybrany_obsah_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getPoznamka();

            // Datum splnění vybrané poznámky
            String vybrany_datum_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getDatum();

            // Cislo vybrane poznamky
            int vybrane_cislo_poznamky = cislo_poznamky;

            // Datum odebrání poznámky
            DateFormat format_datumu = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String string_datum_odebrani = format_datumu.format(Calendar.getInstance().getTime());

            // Typ poznamky
            String typ_poznamky = "Splnena";

            // Vytvoření nové akce
            Statement provadeni_akce_v_databazi = pripojitSeKDatabazi(getAdresa_databaze(), getJmeno(), getHeslo()).createStatement();

            // Vymazání vybraného řádku v poznamkach
            provadeni_akce_v_databazi.executeUpdate("DELETE FROM poznamky WHERE ObsahPoznamky = " + "'" +  vybrany_obsah_poznamky + "'");
            System.out.println("Poznámka odstraněna z databáze");

            // Přidání odebrané poznámky do table splnenepoznamky
            provadeni_akce_v_databazi.executeUpdate("INSERT INTO splnenepoznamky VALUES(" + "'" + vybrane_cislo_poznamky + "'" + ", " + "'" + vybrany_obsah_poznamky + "'" + ", " + "'" + vybrany_datum_poznamky + "'" + ", " + "'" + string_datum_odebrani + "'" + ", " + "'" + typ_poznamky + "')");
            System.out.println("Splněná poznámka přidáná do table splnenepoznamky");
        }

        catch (Exception e) { System.out.println(e); }
    }

    public void upravit_poznamku_v_databazi(TableView<ZapsanePoznamky> aktivni_poznamky, String obsah_poznamky, String datum, int cislo_poznamky){
        try {
            // Vybrany obsah poznámky
            String vybrany_obsah_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getPoznamka();

            // Vytvoření nové akce
            Statement provadeni_akce_v_databazi = pripojitSeKDatabazi(getAdresa_databaze(), getJmeno(), getHeslo()).createStatement();

            // Upravení vybraného řádku
            provadeni_akce_v_databazi.executeUpdate("UPDATE poznamky SET ObsahPoznamky = " + "'" + obsah_poznamky + "'" + ", DatumSplneni = " + "'" + datum + "'" + " WHERE ObsahPoznamky = " + "'" + vybrany_obsah_poznamky + "'");
            System.out.println("Poznámka upravena v databázi");
        }

        catch (Exception e) { System.out.println(e); }
    }

    public void trvale_smazat_poznamku_z_databaze(String obsah_poznamky){
        try{
            // Obsah vybrane poznamky
            String obsah_vybrane_poznamky = obsah_poznamky;

            // Vytvoření nové akce
            Statement provadeni_akce_v_databazi = pripojitSeKDatabazi(getAdresa_databaze(), getJmeno(), getHeslo()).createStatement();

            provadeni_akce_v_databazi.executeUpdate("DELETE FROM splnenepoznamky WHERE ObsahPoznamky = " + "'" + obsah_vybrane_poznamky + "'");
            System.out.println("Poznámka trvale odstraněna z table splnenepoznamky");
        }
        catch (Exception e){ System.out.println(e); }
    }

    public String getAdresa_databaze() {
        return adresa_databaze;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getHeslo() {
        return heslo;
    }
}
