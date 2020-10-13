package nl.hu.dp.ovchip.domein;

import javax.persistence.*;

@Entity
public class Adres {
    @Id
    @Column(name = "adres_id")
    private int      adres_id;
    private String   postcode;
    private String   huisnummer;
    private String   straat;
    private String   woonplaats;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger_id;

    public Adres(int adres, String pcode, String hNummer, String strt, String wPlaats, Reiziger reiz){
        adres_id    = adres;
        postcode   = pcode;
        huisnummer = hNummer;
        straat     = strt;
        woonplaats = wPlaats;
        reiziger_id= reiz;
    }

    public Adres() {

    }

    public int      getAdresID() {
        return adres_id;
    }
    public String   getPostcode() {
        return postcode;
    }
    public String   getHuisnummer() {
        return huisnummer;
    }
    public String   getStraat() {
        return straat;
    }
    public String   getWoonplaats() {
        return woonplaats;
    }
    public Reiziger getReiziger() {
        return reiziger_id;
    }

    public void setAdresID   (int adresID) {
        this.adres_id = adresID;
    }
    public void setPostcode  (String postcode) {
        this.postcode = postcode;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
    public void setStraat    (String straat) {
        this.straat = straat;
    }
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    public void setReizigerID(Reiziger reiziger) {
        this.reiziger_id = reiziger;
    }

    public String toString() {
        return  "Adres {" + "adresID: " + adres_id + ", " +
                postcode + ", " + straat + " #" + huisnummer +
                ", woonplaats: " + woonplaats +
                ", reiziger: #" + reiziger_id.getIdNummer() + '}';
    }
}
