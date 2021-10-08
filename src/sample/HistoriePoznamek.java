package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HistoriePoznamek extends PripojeniKDatabazi{

    // Historie poznamek nova scena a vzhled okna
    public Group vzhled_okna_historie_poznamek = new Group();
    public Scene scena_historie_poznamek = new Scene(vzhled_okna_historie_poznamek, Color.rgb(50, 52, 62));

    // Tlacitko nova poznamka pro prepnuti scen
    public Button tlacitko_nova_poznamka_historie_poznamek = new Button();

    // Tabulka se splněnýma poznámkama
    public TableView<SplnenePoznamky> tabulka_splnene_poznamky;

    // List se splněnýmá poznámkama
    public ObservableList<SplnenePoznamky> splnene_poznamky_a_datumy = FXCollections.observableArrayList();

    // Záložka pro jezdící lištu
    public TableColumn<SplnenePoznamky, String> postranni_jezdici_lista = new TableColumn<>("");

    // Přidání poznámky do listu se splněnýma poznámkama - SMAZANA
    public void pridani_poznamky_do_splnenych_poznamek_smazana(TableView<ZapsanePoznamky> aktivni_poznamky){

        // Formát datumu
        DateFormat format_datumu = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        // Údaje co se přidají do listu se splněnýma poznámkama
        String obsah_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getPoznamka();
        String datum_splneni_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getDatum();
        String string_datum_odebrani = format_datumu.format(Calendar.getInstance().getTime());
        String typ_poznamky = "Smazana";

        // Přidání poznámky do listu se splněnýma poznámkama
        splnene_poznamky_a_datumy.add(new SplnenePoznamky(obsah_poznamky, datum_splneni_poznamky, string_datum_odebrani, typ_poznamky));
    }

    // Přidání poznámky do listu se splněnýma poznámkama - SPLNENA
    public void pridani_poznamky_do_splnenych_poznamek_splnena(TableView<ZapsanePoznamky> aktivni_poznamky){

        // Formát datumu
        DateFormat format_datumu = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        // Údaje co se přidají do listu se splněnýma poznámkama
        String obsah_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getPoznamka();
        String datum_splneni_poznamky = aktivni_poznamky.getSelectionModel().getSelectedItem().getDatum();
        String string_datum_odebrani = format_datumu.format(Calendar.getInstance().getTime());
        String typ_poznamky = "Splnena";

        // Přidání poznámky do listu se splněnýma poznámkama
        splnene_poznamky_a_datumy.add(new SplnenePoznamky(obsah_poznamky, datum_splneni_poznamky, string_datum_odebrani, typ_poznamky));
    }

    public void trvale_smazat_poznamku(){
        try {
            // Obsah vybrané poznámky
            String obsah_vybrane_poznamky = tabulka_splnene_poznamky.getSelectionModel().getSelectedItem().getPoznamka();

            // Odstranění vybrané poznámky z tabulky splněných poznámek
            splnene_poznamky_a_datumy.remove(tabulka_splnene_poznamky.getSelectionModel().getSelectedItem());

            // Odstanění poznámky z databaze
            trvale_smazat_poznamku_z_databaze(obsah_vybrane_poznamky);
        }
        catch (Exception e) { System.out.println(e); }
    }

    public void nastaveniScenyHistoriePoznamek(){

        // Postranní panel s tlacitky nova poznamka a historie poznamek
        Rectangle postranni_panel = new Rectangle();
        postranni_panel.setFill(Color.rgb(40,41,46));
        postranni_panel.setWidth(180);
        postranni_panel.setHeight(500);
        postranni_panel.setX(0);
        postranni_panel.setY(0);
        vzhled_okna_historie_poznamek.getChildren().add(postranni_panel);

        // Tlacitko NOVA POZNÁMKA
        tlacitko_nova_poznamka_historie_poznamek.setLayoutX(0);
        tlacitko_nova_poznamka_historie_poznamek.setLayoutY(0);
        tlacitko_nova_poznamka_historie_poznamek.setFont(Font.font("Merriweather", 18));
        tlacitko_nova_poznamka_historie_poznamek.setTextAlignment(TextAlignment.CENTER);
        tlacitko_nova_poznamka_historie_poznamek.setText("Nová poznámka");
        tlacitko_nova_poznamka_historie_poznamek.setTextFill(Color.WHITE);
        tlacitko_nova_poznamka_historie_poznamek.setBackground(Background.EMPTY);
        tlacitko_nova_poznamka_historie_poznamek.setCursor(Cursor.HAND);
        tlacitko_nova_poznamka_historie_poznamek.setMinWidth(180);
        vzhled_okna_historie_poznamek.getChildren().add(tlacitko_nova_poznamka_historie_poznamek);

        // Tlacitko HISTORIE POZNÁMEK
        Button tlacitko_historie_poznamek_historie_poznamek = new Button();
        tlacitko_historie_poznamek_historie_poznamek.setLayoutX(0);
        tlacitko_historie_poznamek_historie_poznamek.setLayoutY(38);
        tlacitko_historie_poznamek_historie_poznamek.setFont(Font.font("Merriweather", 18));
        tlacitko_historie_poznamek_historie_poznamek.setTextAlignment(TextAlignment.CENTER);
        tlacitko_historie_poznamek_historie_poznamek.setText("Historie poznámek");
        tlacitko_historie_poznamek_historie_poznamek.setTextFill(Color.WHITE);
        tlacitko_historie_poznamek_historie_poznamek.setBackground(Background.EMPTY);
        tlacitko_historie_poznamek_historie_poznamek.setCursor(Cursor.HAND);
        tlacitko_historie_poznamek_historie_poznamek.setMinWidth(180);
        vzhled_okna_historie_poznamek.getChildren().add(tlacitko_historie_poznamek_historie_poznamek);

        // Panel se splněnýma poznámkama
        Rectangle panel_se_splnenyma_poznamkama = new Rectangle();
        panel_se_splnenyma_poznamkama.setFill(Color.rgb(43, 52, 62));
        panel_se_splnenyma_poznamkama.setWidth(820);
        panel_se_splnenyma_poznamkama.setHeight(500);
        panel_se_splnenyma_poznamkama.setX(180);
        panel_se_splnenyma_poznamkama.setY(0);
        vzhled_okna_historie_poznamek.getChildren().add(panel_se_splnenyma_poznamkama);

        // SPLNĚNÉ POZNÁMKY
        // Záložky v panelu splněných poznámek - POZNÁMKA
        TableColumn<SplnenePoznamky, String> zalozka_poznamka = new TableColumn<>("Poznámka");
        zalozka_poznamka.setMinWidth(340);
        zalozka_poznamka.setCellValueFactory(new PropertyValueFactory<>("poznamka"));

        // Záložky v panelu splněných poznámek - DATUM SPLNĚNÍ
        TableColumn<SplnenePoznamky, String> zalozka_datum_splneni = new TableColumn<>("Datum Splnění");
        zalozka_datum_splneni.setMinWidth(160);
        zalozka_datum_splneni.setCellValueFactory(new PropertyValueFactory<>("datum_splneni"));

        // Záložky v panelu splněných poznámek - DATUM ODEBRÁNÍ
        TableColumn<SplnenePoznamky, String> zalozka_datum_odebrani = new TableColumn<>("Datum Odebrání");
        zalozka_datum_odebrani.setMinWidth(160);
        zalozka_datum_odebrani.setCellValueFactory(new PropertyValueFactory<>("datum_odebrani"));

        // Záložky v panelu splněných poznámek - TYP POZNAMKY
        TableColumn<SplnenePoznamky, String> zalozka_typ_poznamky = new TableColumn<>("Typ Poznámky");
        zalozka_typ_poznamky.setMinWidth(160);
        zalozka_typ_poznamky.setCellValueFactory(new PropertyValueFactory<>("typ_poznamky"));

        // Záložky v panelu splněných poznámek - POSTRANNI JEZDICI LIŠTA
        postranni_jezdici_lista.setMinWidth(20);
        postranni_jezdici_lista.setVisible(false);

        // Vytvoření tabulky se splněnýma poznámkama
        tabulka_splnene_poznamky = new TableView<>();
        tabulka_splnene_poznamky.getColumns().addAll(zalozka_poznamka, zalozka_datum_splneni, zalozka_datum_odebrani, zalozka_typ_poznamky, postranni_jezdici_lista);
        tabulka_splnene_poznamky.setItems(null);
        tabulka_splnene_poznamky.setLayoutX(185);
        tabulka_splnene_poznamky.setLayoutY(2);
        tabulka_splnene_poznamky.setMinHeight(450);
        tabulka_splnene_poznamky.setMinWidth(840);
        vzhled_okna_historie_poznamek.getChildren().add(tabulka_splnene_poznamky);

        // Tlacitko TRVALE SMAZAT POZNÁMKU
        Button tlacitko_trvale_smazat_poznamku = new Button();
        tlacitko_trvale_smazat_poznamku.setLayoutX(0);
        tlacitko_trvale_smazat_poznamku.setLayoutY(400);
        tlacitko_trvale_smazat_poznamku.setFont(Font.font("Merriweather", 15));
        tlacitko_trvale_smazat_poznamku.setTextAlignment(TextAlignment.CENTER);
        tlacitko_trvale_smazat_poznamku.setText("Trvale Smazat Poznámku");
        tlacitko_trvale_smazat_poznamku.setTextFill(Color.WHITE);
        tlacitko_trvale_smazat_poznamku.setBackground(Background.EMPTY);
        tlacitko_trvale_smazat_poznamku.setCursor(Cursor.HAND);
        tlacitko_trvale_smazat_poznamku.setMinWidth(180);
        vzhled_okna_historie_poznamek.getChildren().add(tlacitko_trvale_smazat_poznamku);

        tlacitko_trvale_smazat_poznamku.setOnAction(event -> {
            trvale_smazat_poznamku();
        });
    }

}
