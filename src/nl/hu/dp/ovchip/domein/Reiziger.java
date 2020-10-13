package nl.hu.dp.ovchip.domein;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reiziger {
    @Id
    @Column(name = "reiziger_id")
    private int                      reiziger_id;
    private String                   voorletters;
    private String                   tussenvoegsel;
    private String                   achternaam;
    private Date                     geboortedatum;

    @OneToOne(mappedBy = "reiziger_id", cascade = CascadeType.ALL)
    private Adres                    adres_id;

    @OneToMany(mappedBy = "reiziger_id", cascade = CascadeType.ALL)
    private final List<OVChipkaart>  oVKaarten = new ArrayList<OVChipkaart>();

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id   = reiziger_id;
        this.voorletters   = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam    = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger() {
    }


    public int               getIdNummer(){
        return reiziger_id;
    }
    public String            getVoorletters(){
        return voorletters;
    }
    public String            getTussenvoegsel(){
        return tussenvoegsel;
    }
    public String            getAchternaam(){
        return achternaam;
    }
    public Date              getGeboortedatum(){
        return geboortedatum;
    }
    public Adres             getHuisAdres(){
        return adres_id;
    }
    public List<OVChipkaart> getOVKaarten() {
        return oVKaarten;
    }

    public void setreiziger_id(int id){
        reiziger_id = id;
    }
    public void setVoorletters(String string){
        voorletters = string;
    }
    public void setTussenvoegsel(String string){
        tussenvoegsel = string;
    }
    public void setAchternaam(String string){
        achternaam = string;
    }
    public void setGeboortedatum(String datum){
        geboortedatum = Date.valueOf(datum);
    }
    public void setHuisadres(Adres ad) {
        adres_id = ad;
    }

    public void addOVKaart(OVChipkaart OVKaart) {
        oVKaarten.add(OVKaart);
    }
    public void removeOVKaart(OVChipkaart ov) {
       oVKaarten.remove(ov);
    }

    public String getNaam(){
        String naam = voorletters + " ";
        if (tussenvoegsel != null){
            naam = naam + tussenvoegsel + " ";
        }
        naam = naam + achternaam;
        return naam;
    }

    public String toString() {
        String returnString1 = "Reiziger{#"+ reiziger_id + " " + voorletters + " ";
        StringBuilder returnString2 = new StringBuilder(achternaam + ", geb." + geboortedatum + "}");
        if (tussenvoegsel != null){
            returnString1 = returnString1 + tussenvoegsel + " ";
        }
        if (adres_id != null){
            returnString2.append(", Adres {#").append(adres_id.getAdresID()).append(" ").append(adres_id.getPostcode()).append(" ").append(adres_id.getWoonplaats()).append("-").append(adres_id.getHuisnummer()).append("} ");
        }
        if (!oVKaarten.isEmpty()){
            returnString2.append(", met OVChipKaarten : ");
            for (OVChipkaart ov : oVKaarten) {
                returnString2.append("\n{#").append(ov.getKaart_nummer()).append(", expires on ").append(ov.getGeldig_tot()).append(", klasse ").append(ov.getKlasse()).append(", saldo van €").append(ov.getSaldo()).append("} ");
            }
        }
        else {
            returnString2.append(" ( deze reiziger heeft geen OVChipKaarten. )");
        }
        return  returnString1 + returnString2 + "\n";
    }
}
