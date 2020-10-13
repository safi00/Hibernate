package nl.hu.dp.ovchip.domein;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name = "kaart_nummer")
    private long                kaart_nummer;
    private Date                geldig_tot;
    private int                 klasse;
    private Double              saldo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reiziger_id")
    private Reiziger            reiziger_id;

//    @ManyToMany
//    @JoinColumn(name = "product_nummer")
    @ManyToMany(mappedBy = "kaart_nummer", cascade = CascadeType.ALL)
    private final List<Product> product_nummer = new ArrayList<Product>();

    public OVChipkaart(long kNummer, Date gTot, int klas, Double sal, Reiziger reiz){
        kaart_nummer = kNummer;
        geldig_tot   = gTot;
        klasse       = klas;
        saldo        = sal;
        reiziger_id  = reiz;
    }

    public OVChipkaart() {

    }

    public long     getKaart_nummer(){return kaart_nummer;}
    public Date     getGeldig_tot(){return geldig_tot;}
    public int      getKlasse(){return klasse;}
    public Double   getSaldo(){return saldo;}
    public Reiziger getReiziger(){return reiziger_id;}

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }
    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void addproduct_nummer(Product pro){
        this.product_nummer.add(pro);
    }

    @Override
    public String toString() {
        String returnString1 = "OVChipkaart{" + "kaart #" + kaart_nummer + ", is geldig tot: " + geldig_tot + " ";
        StringBuilder returnString2 = new StringBuilder( ", met een saldo van €" + saldo + ", deze kaart is van reiziger: #" + reiziger_id.getIdNummer() + " " + reiziger_id.getNaam() + '}');
        if (klasse == 2){
            returnString1 = returnString1 + klasse + "de  klass ";
        }else {
            returnString1 = returnString1 + klasse + "ste klass ";
        }
        if (!product_nummer.isEmpty()){
            returnString2.append(" met ovProduct(en) :");
        for (Product pro : product_nummer) {
            returnString2.append("\n{#").append(pro.getProduct_nummer()).append(", ").append(pro.getNaam()).append(",  ").append(pro.getBeschrijving());
        }
    } else {
        returnString2.append("(deze reiziger heeft geen ovProducten.)");
    }
        return returnString1 + returnString2 + "\n";
    }
}
