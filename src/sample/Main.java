package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    // Konstruktory
    NovaPoznamka nova_poznamka = new NovaPoznamka();
    HistoriePoznamek historie_poznamek = new HistoriePoznamek();
    PripojeniKDatabazi pripojeni_k_databazi = new PripojeniKDatabazi();

    // Parametry okna
    private int sirka_okna = 1020;

    @Override
    public void start(Stage hlavni_okno_aplikace) throws Exception{

        // Vlastnosti okna
        hlavni_okno_aplikace.setHeight(490);
        hlavni_okno_aplikace.setWidth(sirka_okna);
        hlavni_okno_aplikace.setTitle("Poznámky");
        hlavni_okno_aplikace.setResizable(false);
        hlavni_okno_aplikace.getIcons().add(new Image("ikona_okna.png"));

        // CSS soubor pro novou poznamku
        nova_poznamka.scena_nova_poznamka.getStylesheets().add("vzhled.css");

        // CSS soubor pro historii poznamek
        historie_poznamek.scena_historie_poznamek.getStylesheets().add("vzhled.css");

        // Nastavení scény nová poznámka
        nova_poznamka.nastaveniScenyNovaPoznamka();

        // Nastavení scény historie poznámek
        historie_poznamek.nastaveniScenyHistoriePoznamek();

        // Přidání sceny nová poznámka při spuštění aplikace
        hlavni_okno_aplikace.setScene(nova_poznamka.scena_nova_poznamka);

        // Načtení databaze
        pripojeni_k_databazi.pripojitSeKDatabazi(pripojeni_k_databazi.getAdresa_databaze(), pripojeni_k_databazi.getJmeno(), pripojeni_k_databazi.getHeslo());
        System.out.println("Připojení k databázi bylo úspěšné\nDatabáze byla úspěšně načtena");

        // Načtení aktivních poznámek z databaze
        nacteni_aktivních_poznámek_z_databaze();

        // Načtení historie poznámek z databaze
        nacteni_historie_poznamek_z_databaze();

        // Přepnutí na scenu historie poznámek
        nova_poznamka.tlacitko_historie_poznamek_nova_poznamka.setOnAction(event -> {
            hlavni_okno_aplikace.setScene(historie_poznamek.scena_historie_poznamek);
        });

        // Přepnutí na scenu nová poznámka
        historie_poznamek.tlacitko_nova_poznamka_historie_poznamek.setOnAction(event -> {
            hlavni_okno_aplikace.setScene(nova_poznamka.scena_nova_poznamka);
        });

        // Kontrola jestli obsah textu je menší než 41 znaků a odstranění textu když kliknu do textovyho pole
        Timer casovac = new Timer();
        casovac.schedule(new TimerTask() {
            public void run() {
                // Spuštění nového vlákna procesoru
                Platform.runLater(() -> {
                    // Mazání písmen přesahujících limit 41
                    if (nova_poznamka.obsah_poznamky.getText().length() > 40){
                        nova_poznamka.obsah_poznamky.deleteText(40, 41);
                    }
                    // Vraceni zakladniho textu když neni vybráno textový pole a když v něm není text
                    else if (!nova_poznamka.obsah_poznamky.isFocused() && nova_poznamka.obsah_poznamky.getText() == ""){
                        nova_poznamka.obsah_poznamky.setText("Zde bude obsah poznámky.");
                    }
                    // Ukončení celeho procesu když se okno nezobrazuje
                    if (!hlavni_okno_aplikace.isShowing()){
                        System.exit(0);
                    }
                });
            }
        }, 0, 1);

        // Odebrání poznámky po kliknutí na tlačíko odebrat poznámku
        nova_poznamka.tlacitko_odebrat_poznamku.setOnAction(event -> {
            odebrat_poznamku();
        });

        // Odebrání poznámky po kliknutí na tlačítko splnit poznámku
        nova_poznamka.tlacitko_splnit_poznamku.setOnAction(event -> {
            splnit_poznamku();
        });


        // Zobrazení okna aplikace
        hlavni_okno_aplikace.show();
    }

    // Odebrání poznámky - SMAZANA
    public void odebrat_poznamku() {
        try {
            // Odebrání poznámky z databáze
            pripojeni_k_databazi.odebrat_smazanou_poznamku_z_databaze(nova_poznamka.aktivni_poznamky, nova_poznamka.cislo_poznamky);

            // Přidání poznámky do listu se splněnýma poznámkama
            historie_poznamek.pridani_poznamky_do_splnenych_poznamek_smazana(nova_poznamka.aktivni_poznamky);

            // Aktualizování tabulky se splněnýma poznámkama
            historie_poznamek.tabulka_splnene_poznamky.setItems(historie_poznamek.splnene_poznamky_a_datumy);

            // Odebrání zakliknuté poznámky z listu aktivních poznámek
            nova_poznamka.poznamky_a_datumy.remove(nova_poznamka.aktivni_poznamky.getSelectionModel().getSelectedItem());

            nova_poznamka.pocet_poznamek--;
            nova_poznamka.cislo_poznamky--;


            // Postranní jezdící lišta - Nova poznámka
            if (nova_poznamka.pocet_poznamek >= 15) {
                nova_poznamka.postranni_jezdici_lista.setVisible(true);
                sirka_okna = 1040;
            }
            else if (nova_poznamka.pocet_poznamek <= 15) {
                nova_poznamka.postranni_jezdici_lista.setVisible(false);
                sirka_okna = 1020;
            }

            // Postranní jezdící lišta - Historie poznámek
            if (nova_poznamka.pocet_poznamek >= 15) {
                historie_poznamek.postranni_jezdici_lista.setVisible(true);
                sirka_okna = 1040;
            }
            else if (nova_poznamka.pocet_poznamek <= 15) {
                historie_poznamek.postranni_jezdici_lista.setVisible(false);
                sirka_okna = 1020;
            }
        }
        catch (Exception e) { System.out.println(e); }
    }

    // Odebrání poznámky - SPLNENA
    public void splnit_poznamku(){
        try {
            // Odebrání poznámky z databáze
            pripojeni_k_databazi.odebrat_splnenou_poznamku_z_databaze(nova_poznamka.aktivni_poznamky, nova_poznamka.cislo_poznamky);

            // Přidání poznámky do listu se splněnýma poznámkama
            historie_poznamek.pridani_poznamky_do_splnenych_poznamek_splnena(nova_poznamka.aktivni_poznamky);

            // Aktualizování tabulky se splněnýma poznámkama
            historie_poznamek.tabulka_splnene_poznamky.setItems(historie_poznamek.splnene_poznamky_a_datumy);

            // Odebrání zakliknuté poznámky z listu aktivních poznámek
            nova_poznamka.poznamky_a_datumy.remove(nova_poznamka.aktivni_poznamky.getSelectionModel().getSelectedItem());

            nova_poznamka.pocet_poznamek--;
            nova_poznamka.cislo_poznamky--;

            // Postranní jezdící lišta - Nova poznámka
            if (nova_poznamka.pocet_poznamek >= 15) {
                nova_poznamka.postranni_jezdici_lista.setVisible(true);
                sirka_okna = 1040;
            }
            else if (nova_poznamka.pocet_poznamek <= 15) {
                nova_poznamka.postranni_jezdici_lista.setVisible(false);
                sirka_okna = 1020;
            }

            // Postranní jezdící lišta - Historie poznámek
            if (nova_poznamka.pocet_poznamek >= 15) {
                historie_poznamek.postranni_jezdici_lista.setVisible(true);
                sirka_okna = 1040;
            }
            else if (nova_poznamka.pocet_poznamek <= 15) {
                historie_poznamek.postranni_jezdici_lista.setVisible(false);
                sirka_okna = 1020;
            }
        }
        catch (Exception e) { System.out.println(e); }
    }

    // Načtení aktivních poznámek z databaze
    public void nacteni_aktivních_poznámek_z_databaze(){
        try {
            // Vytvoření nové akce
            Statement provadeni_akce_v_databazi = pripojeni_k_databazi.pripojitSeKDatabazi(pripojeni_k_databazi.getAdresa_databaze(), pripojeni_k_databazi.getJmeno(), pripojeni_k_databazi.getHeslo()).createStatement();

            // Initializace všech poznamek co jsou v databazi
            ResultSet initializace_poznamek_v_databazi = provadeni_akce_v_databazi.executeQuery("SELECT * FROM poznamky");

            // Loop dokud je nějaká další poznámka v databázi
            while (initializace_poznamek_v_databazi.next()) {

                // Obsah poznamky v databazi
                String obsah_poznamky = initializace_poznamek_v_databazi.getString("ObsahPoznamky");

                // Datum splnění poznamky v databazi
                String datum_splneni = initializace_poznamek_v_databazi.getString("DatumSplneni");

                // Přidání poznámky do listu s aktivníma poznámkama
                nova_poznamka.poznamky_a_datumy.add(new ZapsanePoznamky(obsah_poznamky, datum_splneni));

                // Přičtení počtu již existujících poznámek v databázi
                nova_poznamka.cislo_poznamky++;
                nova_poznamka.pocet_poznamek++;
            }

            // Aktualizování tabulky aktivních poznámek aby se tam zobrazily nově přidané poznámky
            nova_poznamka.aktivni_poznamky.setItems(nova_poznamka.poznamky_a_datumy);

            // Předělání postranní lišty když načtených poznámek z databaze bude 15 a víc
            if (nova_poznamka.pocet_poznamek >= 15){
                nova_poznamka.postranni_jezdici_lista.setVisible(true);
            }
            else if (nova_poznamka.pocet_poznamek <= 15){
                nova_poznamka.postranni_jezdici_lista.setVisible(false);
            }
        }
        catch (Exception e){ System.out.println(e); }
    }

    // Načtení historie poznámek z databaze
    public void nacteni_historie_poznamek_z_databaze(){
        try {
            // Vytvoření nové akce
            Statement provadeni_akce_v_databazi = pripojeni_k_databazi.pripojitSeKDatabazi(pripojeni_k_databazi.getAdresa_databaze(), pripojeni_k_databazi.getJmeno(), pripojeni_k_databazi.getHeslo()).createStatement();

            // Initializace všech splněných poznamek co jsou v databazi
            ResultSet initializace_poznamek_v_databazi = provadeni_akce_v_databazi.executeQuery("SELECT * FROM splnenepoznamky");

            // Loop dokud je nějaká další poznámka v databázi
            while (initializace_poznamek_v_databazi.next()) {

                // Obsah poznamky v databazi
                String obsah_poznamky = initializace_poznamek_v_databazi.getString("ObsahPoznamky");

                // Datum splnění poznamky v databazi
                String datum_splneni = initializace_poznamek_v_databazi.getString("DatumSplneni");

                // Datum odebrání poznamky v databazi
                String datum_odebrani = initializace_poznamek_v_databazi.getString("DatumOdebrani");

                // Typ poznámky v databazi
                String typ_poznamky = initializace_poznamek_v_databazi.getString("TypPoznamky");

                // Přidání poznámky do listu se splněnýma poznámkama
                historie_poznamek.splnene_poznamky_a_datumy.add(new SplnenePoznamky(obsah_poznamky, datum_splneni, datum_odebrani, typ_poznamky));

                // Přičtení počtu již existujících poznámek v databázi
                nova_poznamka.cislo_poznamky++;
                nova_poznamka.pocet_poznamek++;
            }

            // Aktualizování tabulky splněných poznámek aby se tam zobrazily nově přidané poznámky
            historie_poznamek.tabulka_splnene_poznamky.setItems(historie_poznamek.splnene_poznamky_a_datumy);

            // Předělání postranní lišty když načtených poznámek z databaze bude 15 a víc
            if (nova_poznamka.pocet_poznamek >= 15) {
                historie_poznamek.postranni_jezdici_lista.setVisible(true);
                sirka_okna = 1040;
            }
            else if (nova_poznamka.pocet_poznamek <= 15) {
                historie_poznamek.postranni_jezdici_lista.setVisible(false);
                sirka_okna = 1020;
            }
        }
        catch (Exception e){ System.out.println(e); }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
