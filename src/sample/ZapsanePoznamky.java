package sample;

public class ZapsanePoznamky extends NovaPoznamka {

    private String poznamka;
    private String datum;

    public ZapsanePoznamky(String poznamka, String datum){
        this.poznamka = poznamka;
        this.datum = datum;
    }

    public void setPoznamka(){
        this.poznamka = obsah_poznamky.getText();
    }

    public String getPoznamka(){
        return this.poznamka;
    }

    public void setDatum(){
        this.datum = vybrany_datum(vybrany_datum_pro_pripomenuti);
    }

    public String getDatum(){
        return this.datum;
    }

}
