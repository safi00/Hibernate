package nl.hu.dp.ovchip.domein;

import javax.naming.Name;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @Column(name = "product_nummer")
    private long                     product_nummer;
    private String                   naam;
    private String                   beschrijving;
    private Double                   prijs;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ov_chipkaart_product",
               joinColumns = {@JoinColumn(name = "product_nummer")},
               inverseJoinColumns = {@JoinColumn(name = "kaart_nummer")})
    private final List<OVChipkaart>  kaart_nummer   = new ArrayList<OVChipkaart>();

    public Product(long nummer, String productNaam, String productBeschrijving, Double productPrijs){
        product_nummer = nummer;
        naam           = productNaam;
        beschrijving   = productBeschrijving;
        prijs          = productPrijs;
    }

    public Product() {

    }

    public long getProduct_nummer() {
        return product_nummer;
    }
    public String getNaam() {
        return naam;
    }
    public String getBeschrijving() {
        return beschrijving;
    }
    public Double getPrijs() {
        return prijs;
    }
    public List<OVChipkaart> getOVs() {
        return kaart_nummer;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }
    public void addOV(OVChipkaart ov) {
        this.kaart_nummer.add(ov);
    }
    public void removeOV(OVChipkaart ov) {
        this.kaart_nummer.remove(ov);
    }

    public String toString() {
        return "Product{#" + product_nummer + ", " + naam + ", " + beschrijving +
                ", €" + prijs + "}";
    }
}
