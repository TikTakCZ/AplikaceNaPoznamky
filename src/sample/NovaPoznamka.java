package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NovaPoznamka extends PripojeniKDatabazi {

    // Vzhled okna a scena nova poznamka
    public Group vzhled_okna_nova_poznamka = new Group();
    public Scene scena_nova_poznamka = new Scene(vzhled_okna_nova_poznamka, Color.rgb(50, 52, 62));

    // Tlacitko historie poznamek pro prepnuti sceny
    public Button tlacitko_historie_poznamek_nova_poznamka = new Button();

    // Vybrany datum
    public DatePicker vybrany_datum_pro_pripomenuti = new DatePicker();

    // Obsah poznámky
    public TextField obsah_poznamky = new TextField();

    // Tlacitko pro odebrání nove vytvorene poznamky
    public Button tlacitko_odebrat_poznamku = new Button();

    // Tlacitko pro splneni nove vytvorene poznamky
    public Button tlacitko_splnit_poznamku = new Button();

    // Tabulka s aktivníma poznámkama
    public TableView<ZapsanePoznamky> aktivni_poznamky;

    // List s aktivníma poznámkama
    public ObservableList<ZapsanePoznamky> poznamky_a_datumy = FXCollections.observableArrayList();

    // Počet aktivních poznámek v listu
    public int pocet_poznamek = 0;

    // Cislo aktivní poznámky
    public int cislo_poznamky = 0;

    // Záložka pro jezdící lištu
    public TableColumn<ZapsanePoznamky, String> postranni_jezdici_lista = new TableColumn<>("");

    public String vybrany_datum(DatePicker vybrany_datum_pro_pripomenuti) {
        LocalDate vybrany_cas_poznamky = vybrany_datum_pro_pripomenuti.getValue();

        return vybrany_cas_poznamky.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void pridat_poznamku() {
        // Přidání poznámky do listu s poznámkama
        poznamky_a_datumy.add(new ZapsanePoznamky(obsah_poznamky.getText(), vybrany_datum(vybrany_datum_pro_pripomenuti)));
        // Aktualizování aktivních poznámek aby se tam zobrazila nově přidaná poznámka
        aktivni_poznamky.setItems(poznamky_a_datumy);

        // Přidání poznamky do databaze
        pridat_poznamku_do_databaze(obsah_poznamky.getText(), vybrany_datum(vybrany_datum_pro_pripomenuti), cislo_poznamky);

        pocet_poznamek++;
        cislo_poznamky++;

        if (pocet_poznamek >= 15){
            postranni_jezdici_lista.setVisible(true);
        }
        else if (pocet_poznamek <= 15){
            postranni_jezdici_lista.setVisible(false);
        }
    }

    public void upravit_poznamku(){
        try {
            // Upravení poznamky v databazi
            upravit_poznamku_v_databazi(aktivni_poznamky, obsah_poznamky.getText(), vybrany_datum(vybrany_datum_pro_pripomenuti));

            // Zjištění pozice zakliknuté poznámky
            int pozice_vybrane_poznamky = aktivni_poznamky.getSelectionModel().getSelectedIndex();

            // Odstranění zakliknuté poznámky z listu poznámek
            poznamky_a_datumy.remove(aktivni_poznamky.getSelectionModel().getSelectedItem());

            // Přidání nově aktualizované poznámky na stejné místo
            poznamky_a_datumy.add(pozice_vybrane_poznamky, new ZapsanePoznamky(obsah_poznamky.getText(), vybrany_datum(vybrany_datum_pro_pripomenuti)));
            // Aktualizování aktivních poznámek aby se tam zobrazila nově přidaná poznámka
            aktivni_poznamky.setItems(poznamky_a_datumy);
        }
        catch (Exception e){ System.out.println(e); }
    }

    public void nastaveniScenyNovaPoznamka() {

        // Postranní panel s tlacitky NOVA POZNAMKA a HISTORIE POZNAMEK
        Rectangle postranni_panel = new Rectangle();
        postranni_panel.setFill(Color.rgb(40, 41, 46));
        postranni_panel.setWidth(180);
        postranni_panel.setHeight(500);
        postranni_panel.setX(0);
        postranni_panel.setY(0);
        vzhled_okna_nova_poznamka.getChildren().add(postranni_panel);

        // Tlacitko NOVA POZNÁMKA
        Button tlacitko_nova_poznamka_nova_poznamka = new Button();
        tlacitko_nova_poznamka_nova_poznamka.setLayoutX(0);
        tlacitko_nova_poznamka_nova_poznamka.setLayoutY(0);
        tlacitko_nova_poznamka_nova_poznamka.setFont(Font.font("Merriweather", 18));
        tlacitko_nova_poznamka_nova_poznamka.setTextAlignment(TextAlignment.CENTER);
        tlacitko_nova_poznamka_nova_poznamka.setText("Nová poznámka");
        tlacitko_nova_poznamka_nova_poznamka.setTextFill(Color.WHITE);
        tlacitko_nova_poznamka_nova_poznamka.setBackground(Background.EMPTY);
        tlacitko_nova_poznamka_nova_poznamka.setCursor(Cursor.HAND);
        tlacitko_nova_poznamka_nova_poznamka.setMinWidth(180);
        vzhled_okna_nova_poznamka.getChildren().add(tlacitko_nova_poznamka_nova_poznamka);

        // Tlacitko HISTORIE POZNÁMEK
        tlacitko_historie_poznamek_nova_poznamka.setLayoutX(0);
        tlacitko_historie_poznamek_nova_poznamka.setLayoutY(38);
        tlacitko_historie_poznamek_nova_poznamka.setFont(Font.font("Merriweather", 18));
        tlacitko_historie_poznamek_nova_poznamka.setTextAlignment(TextAlignment.CENTER);
        tlacitko_historie_poznamek_nova_poznamka.setText("Historie poznámek");
        tlacitko_historie_poznamek_nova_poznamka.setTextFill(Color.WHITE);
        tlacitko_historie_poznamek_nova_poznamka.setBackground(Background.EMPTY);
        tlacitko_historie_poznamek_nova_poznamka.setCursor(Cursor.HAND);
        tlacitko_historie_poznamek_nova_poznamka.setMinWidth(180);
        vzhled_okna_nova_poznamka.getChildren().add(tlacitko_historie_poznamek_nova_poznamka);

        // Panel s aktivníma poznámkama
        Rectangle panel_s_aktivnima_poznamkama = new Rectangle();
        panel_s_aktivnima_poznamkama.setFill(Color.rgb(43, 52, 62));
        panel_s_aktivnima_poznamkama.setWidth(530);
        panel_s_aktivnima_poznamkama.setHeight(500);
        panel_s_aktivnima_poznamkama.setX(180);
        panel_s_aktivnima_poznamkama.setY(0);
        vzhled_okna_nova_poznamka.getChildren().add(panel_s_aktivnima_poznamkama);

        // AKTIVNÍ POZNÁMKY
        // Záložky v panelu aktivních poznámek - POZNÁMKA
        TableColumn<ZapsanePoznamky, String> zalozka_poznamka = new TableColumn<>("Poznámka");
        zalozka_poznamka.setMinWidth(450);
        zalozka_poznamka.setCellValueFactory(new PropertyValueFactory<>("poznamka"));

        // Záložky v panelu aktivních poznámek - DATUM
        TableColumn<ZapsanePoznamky, String> zalozka_datum = new TableColumn<>("Datum");
        zalozka_datum.setMinWidth(80);
        zalozka_datum.setCellValueFactory(new PropertyValueFactory<>("datum"));

        // Záložky v panelu aktivních poznámek - POSTRANNI JEZDICI LIŠTA
        postranni_jezdici_lista.setMinWidth(20);
        postranni_jezdici_lista.setVisible(false);
        postranni_jezdici_lista.setCellValueFactory(new PropertyValueFactory<>(""));

        // Vytvoření tabulky s poznámkama
        aktivni_poznamky = new TableView<>();
        aktivni_poznamky.getColumns().addAll(zalozka_poznamka, zalozka_datum, postranni_jezdici_lista);
        aktivni_poznamky.setLayoutX(185);
        aktivni_poznamky.setLayoutY(2);
        aktivni_poznamky.setMinHeight(450);
        aktivni_poznamky.setMaxWidth(550);
        vzhled_okna_nova_poznamka.getChildren().add(aktivni_poznamky);

        // Obsah poznámky
        obsah_poznamky.setLayoutX(735);
        obsah_poznamky.setLayoutY(5);
        obsah_poznamky.setMinWidth(265);
        obsah_poznamky.setMinHeight(150);
        obsah_poznamky.setAlignment(Pos.TOP_LEFT);
        obsah_poznamky.setText("Zde bude obsah poznámky.");
        vzhled_okna_nova_poznamka.getChildren().add(obsah_poznamky);

        // Datum poznámky
        vybrany_datum_pro_pripomenuti.setLayoutX(735);
        vybrany_datum_pro_pripomenuti.setLayoutY(158);
        vybrany_datum_pro_pripomenuti.setMinWidth(265);
        vybrany_datum_pro_pripomenuti.setCursor(Cursor.HAND);
        vybrany_datum_pro_pripomenuti.setValue(LocalDate.now());
        vzhled_okna_nova_poznamka.getChildren().add(vybrany_datum_pro_pripomenuti);

        // Tlacitko pro pridani nove poznamky do panelu s aktivnima poznamkama
        Button tlacitko_pridat_poznamku = new Button();
        tlacitko_pridat_poznamku.setLayoutX(735);
        tlacitko_pridat_poznamku.setLayoutY(188);
        tlacitko_pridat_poznamku.setFont(Font.font("Merriweather", 18));
        tlacitko_pridat_poznamku.setTextAlignment(TextAlignment.CENTER);
        tlacitko_pridat_poznamku.setText("Přidat poznámku");
        tlacitko_pridat_poznamku.setTextFill(Color.WHITE);
        tlacitko_pridat_poznamku.setBackground(Background.EMPTY);
        tlacitko_pridat_poznamku.setCursor(Cursor.HAND);
        tlacitko_pridat_poznamku.setMinWidth(265);
        vzhled_okna_nova_poznamka.getChildren().add(tlacitko_pridat_poznamku);

        tlacitko_pridat_poznamku.setOnAction(event -> {
            pridat_poznamku();
        });

        // Tlacitko pro odebrání nove vytvorene poznamky
        tlacitko_odebrat_poznamku.setLayoutX(735);
        tlacitko_odebrat_poznamku.setLayoutY(228);
        tlacitko_odebrat_poznamku.setFont(Font.font("Merriweather", 18));
        tlacitko_odebrat_poznamku.setTextAlignment(TextAlignment.CENTER);
        tlacitko_odebrat_poznamku.setText("Odebrat poznámku");
        tlacitko_odebrat_poznamku.setTextFill(Color.WHITE);
        tlacitko_odebrat_poznamku.setBackground(Background.EMPTY);
        tlacitko_odebrat_poznamku.setCursor(Cursor.HAND);
        tlacitko_odebrat_poznamku.setMinWidth(265);
        vzhled_okna_nova_poznamka.getChildren().add(tlacitko_odebrat_poznamku);

        // Tlacitko pro splneni nove vytvorene poznamky
        tlacitko_splnit_poznamku.setLayoutX(735);
        tlacitko_splnit_poznamku.setLayoutY(268);
        tlacitko_splnit_poznamku.setFont(Font.font("Merriweather", 18));
        tlacitko_splnit_poznamku.setTextAlignment(TextAlignment.CENTER);
        tlacitko_splnit_poznamku.setText("Splnit poznámku");
        tlacitko_splnit_poznamku.setTextFill(Color.WHITE);
        tlacitko_splnit_poznamku.setBackground(Background.EMPTY);
        tlacitko_splnit_poznamku.setCursor(Cursor.HAND);
        tlacitko_splnit_poznamku.setMinWidth(265);
        vzhled_okna_nova_poznamka.getChildren().add(tlacitko_splnit_poznamku);

        // Tlacitko pro upraveni nove vytvorene poznamky
        Button tlacitko_upravit_poznamku = new Button();
        tlacitko_upravit_poznamku.setLayoutX(735);
        tlacitko_upravit_poznamku.setLayoutY(308);
        tlacitko_upravit_poznamku.setFont(Font.font("Merriweather", 18));
        tlacitko_upravit_poznamku.setTextAlignment(TextAlignment.CENTER);
        tlacitko_upravit_poznamku.setText("Upravit poznámku");
        tlacitko_upravit_poznamku.setTextFill(Color.WHITE);
        tlacitko_upravit_poznamku.setBackground(Background.EMPTY);
        tlacitko_upravit_poznamku.setCursor(Cursor.HAND);
        tlacitko_upravit_poznamku.setMinWidth(265);
        vzhled_okna_nova_poznamka.getChildren().add(tlacitko_upravit_poznamku);

        tlacitko_upravit_poznamku.setOnAction(event -> {
            upravit_poznamku();
        });
    }

}
