package sample;

public class SplnenePoznamky extends NovaPoznamka {

    private String poznamka;
    private String datum_splneni;
    private String datum_odebrani;
    private String typ_poznamky;

    public SplnenePoznamky(String poznamka, String datum_splneni, String datum_odebrani, String typ_poznamky){
        this.poznamka = poznamka;
        this.datum_splneni = datum_splneni;
        this.datum_odebrani = datum_odebrani;
        this.typ_poznamky = typ_poznamky;
    }

    public String getPoznamka() {
        return poznamka;
    }

    public void setPoznamka(String poznamka) {
        this.poznamka = poznamka;
    }

    public String getDatum_splneni() {
        return datum_splneni;
    }

    public void setDatum_splneni(String datum_splneni) {
        this.datum_splneni = datum_splneni;
    }

    public String getDatum_odebrani() {
        return datum_odebrani;
    }

    public void setDatum_odebrani(String datum_odebrani) {
        this.datum_odebrani = datum_odebrani;
    }

    public String getTyp_poznamky() {
        return typ_poznamky;
    }

    public void setTyp_poznamky(String typ_poznamky) {
        this.typ_poznamky = typ_poznamky;
    }
}
