import nl.hu.dp.ovchip.dao.*;
import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;
    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }
    public static void main(String[] args) throws SQLException {
        testFetchAll();
        //Dao's
        AdresDAOHibernate       ADAO = new AdresDAOHibernate(getSession());
        ReizigerDAOHibernate    RDAO = new ReizigerDAOHibernate(getSession());
        OVChipkaartDAOHibernate ODAO = new OVChipkaartDAOHibernate(getSession());
        ProductDAOHibernate     PDAO = new ProductDAOHibernate(getSession());

        // Daos relations
        RDAO.setAdresDao(ADAO);
        RDAO.setOVCDao  (ODAO);
        ADAO.setRdao    (RDAO);
        ODAO.setRdao    (RDAO);
        ODAO.setPdao    (PDAO);
        PDAO.setOVCDao  (ODAO);

        //test
        testDAOHibernate(ADAO, RDAO, ODAO, PDAO);
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    public static void testDAOHibernate(AdresDAO adao, ReizigerDAO rdao, OVChipkaartDAO odao, ProductDAO pdao) throws SQLException {
        System.out.println("---------- Test ReizigerDAO -------------");
        System.out.println();

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(177, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Reiziger deleteTest = new Reiziger(207, "S", "", "Deleted", java.sql.Date.valueOf(gbdatum));
        rdao.delete(deleteTest);
        rdao.delete(sietske);
        System.out.println();
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, Before saving and deleting \n");
        System.out.println();
        rdao.delete(sietske);
        System.out.println("[Test] " + rdao.findAll().size() + " reizigers\n");
        rdao.save(sietske);
        System.out.println("[Test] " + rdao.findAll().size() + " reizigers\n");
        rdao.save(deleteTest);
        System.out.println("[Test] " + rdao.findAll().size() + " reizigers\n");
        rdao.delete(deleteTest);
        System.out.println("[Test] " + rdao.findAll().size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("[Test] update & find by ID\n");
        Reiziger updateTest    = new Reiziger(77, "G", "Van", "algemeen", java.sql.Date.valueOf(gbdatum));
        Reiziger beforeTheTest = rdao.findById(updateTest.getIdNummer());
        System.out.println("finding the reiziger with ID #" + updateTest.getIdNummer() + ":\n" + rdao.findById(updateTest.getIdNummer()));
        rdao.update(updateTest);
        System.out.println("\nupdated to:");
        System.out.println(rdao.findById(updateTest.getIdNummer()));
        System.out.println("reversing update by updating again");
        rdao.update(beforeTheTest);
        System.out.println("\n[Test] find by Gbdatum");
        List<Reiziger> reizGBD = rdao.findByGbdatum(gbdatum);
        for (Reiziger rgbd : reizGBD) {
            System.out.println(rgbd);
        }
        System.out.println(rdao.findAll().size() + " reizigers");

        Reiziger testReiziger = new Reiziger(777,"T","EST","connDELETE", java.sql.Date.valueOf("1999-03-14"));
        Adres    testResAdres = new Adres   (88,"7777XD", "77ABC", "cookieStraat","SESAME",testReiziger);
        testReiziger.setHuisadres(testResAdres);
//        dao.save(testReiziger);
//        dao.delete(dao.findById(777));

        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adresList = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres r : adresList) {
            System.out.println(r);
            System.out.println();
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        Reiziger testReiz   = new Reiziger(77, "S", "DE", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres    testadres  = new Adres   (888,"7777XD", "77ABC", "cookieStraat","SESAME",testReiz);
        System.out.print("[Test] Eerst " + adresList.size() + " adresList, na AdresDAO.save() ");
        adao.save(testadres);
        adresList = adao.findAll();
        System.out.println(adresList.size() + " adresList\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("update test");
//        dao.update(new Adres(888,"7888XD", "77ABC", "cookieStraat","SESAME",testReiz));
        System.out.println("update checking if it worked:");
        System.out.println("finding the Adres with ID #" + testadres.getAdresID() + ":\n" + adao.findById(testadres.getAdresID()));
        System.out.println();
        System.out.println("delete adres test:");
        List<Adres> woonplaatsSearchList = adao.findByWoonplaats(testadres.getWoonplaats());
        for (Adres r : woonplaatsSearchList) {
            System.out.println(r);
            System.out.println();
        }
        System.out.println("Deleting :\n" + adao.findByReiziger(testReiz));
        adao.delete(testadres);
        System.out.println();
        System.out.println(adresList.size() + " adresList\n");

        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle kaarten op uit de database
        List<OVChipkaart> kaartList = odao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:\n");
        for (OVChipkaart r : kaartList) {
            System.out.println(r);
            System.out.println();
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gtot = "2025-03-14";
        OVChipkaart testkaart   = new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 2, 100.50,testReiz);
        System.out.print("[Test] Eerst " + kaartList.size() + " kaartList, na ovChipkaartDAO.save() \n");
        odao.delete(testkaart);
        odao.save(testkaart);
        kaartList = odao.findAll();
        System.out.println(kaartList.size() + " kaarten in de List\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("update test");
        odao.update(new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 1, 155.50,testReiz));
        System.out.println("update checking if it worked:");
        System.out.println("finding the kaartnummer with ID #" + testkaart.getKaart_nummer() + ":\n" + odao.findById(testkaart.getKaart_nummer()));
        System.out.println();
        System.out.println("delete test:");
        System.out.println("alle kaarten van "+ testReiz.getNaam() + "\n:" + odao.findByReiziger(testReiz));
        System.out.println();
        System.out.println("Deleting :\n" + odao.findByReiziger(testReiz));


//        Reiziger testReiziger = new Reiziger(777,"T","EST","connDELETE", java.sql.Date.valueOf("1999-03-14"));
//        OVChipkaart testkaart1  = new OVChipkaart (344445555, java.sql.Date.valueOf("2025-05-10"), 2, 100.50,testReiziger);
//        OVChipkaart testkaart2  = new OVChipkaart (455556666, java.sql.Date.valueOf("2025-05-22"), 1, 44.50,testReiziger);
//        OVChipkaart testkaart3  = new OVChipkaart (566667777, java.sql.Date.valueOf("2025-05-28"), 2, 27.66,testReiziger);
//        odao.save(testkaart1);
//        odao.save(testkaart2);
//        odao.save(testkaart3);

        System.out.println("\n---------- Test ProductDAO -------------\n");

        // Haal alle kaarten op uit de database
        List<Product> productenlist = pdao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:\n");
        for (Product r : productenlist) {
            System.out.println(r);
            System.out.println();
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database

        Reiziger    testReizP     = new Reiziger(87, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        OVChipkaart testkaartP    = new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 2, 100.50,testReizP);
        Product     testproduct  = new Product(22,"peanutbutter", "snack on the train",2.50);
        Product     testproduct2 = new Product(66,"jelly","snack on the train",0.50);

        System.out.print("[Test] Eerst " + productenlist.size() + " productenlist, na ovChipkaartpdao.save() \n");

        pdao.delete(testproduct);
        pdao.delete(testproduct2);

        pdao.save(testproduct);
        pdao.save(testproduct2);

        testkaartP.addOVProduct(testproduct);
        testkaartP.addOVProduct(testproduct2);

//        Reiziger x = new Reiziger(777,"T","EST","connDELETE", java.sql.Date.valueOf(gbdatum));
//        OVChipkaart testkaart1  = new OVChipkaart (344445555, java.sql.Date.valueOf(gtot), 2, 100.50,x);
//        OVChipkaart testkaart2  = new OVChipkaart (455556666, java.sql.Date.valueOf(gtot), 1, 44.50,x);
//        OVChipkaart testkaart3  = new OVChipkaart (566667777, java.sql.Date.valueOf(gtot), 2, 27.66,x);

        pdao.addOVChipkaart(testkaart,  testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
        pdao.addOVChipkaart(testkaart,  testproduct2,  "actief",java.sql.Date.valueOf(gbdatum));
//        pdao.addOVChipkaart(testkaart1, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//        pdao.addOVChipkaart(testkaart2, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//        pdao.addOVChipkaart(testkaart3, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//        pdao.addOVChipkaart(testkaart2, testproduct2, "actief",java.sql.Date.valueOf(gbdatum));

        productenlist = pdao.findAll();
        System.out.println();
        System.out.println(productenlist.size() + " kaarten in de List\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("[Test] update test\n");

        System.out.println("ovID #" + testkaart.getKaart_nummer() + " products before the update:");
        for (Product r : pdao.findByOVChipKaart(testkaart.getKaart_nummer())) {
            System.out.println(r);
        }

        pdao.update(new Product(22,"chocola","snack after the train ride",1.25));
        pdao.addOVChipkaart(testkaart,  testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
        System.out.println("\nchecking if the update worked:\n");
        System.out.println("ovID #" + testkaart.getKaart_nummer() + " products after the update:");

        for (Product r : pdao.findByOVChipKaart(testkaart.getKaart_nummer())) {
            System.out.println(r);
        }
        System.out.println();
        System.out.println("delete test: " + testproduct2);
        pdao.delete(testproduct2);

        System.out.println("checking if product " + testproduct2.getNaam() + " that belongs to ovID #" + testkaart.getKaart_nummer() + "\nis still in the list:\n");

        for (Product r : pdao.findByOVChipKaart(testkaart.getKaart_nummer())) {
            System.out.println(r);
            System.out.println();
        }

        System.out.println("alle producten van "+ testkaart.getKaart_nummer() + ":");

        List<Product> productentestList = pdao.findByOVChipKaart(testkaart.getKaart_nummer());

        for (Product r : productentestList) {
            System.out.println(r);
        }
        System.out.println();
        System.out.println("Deleting test: " + testproduct);
        pdao.delete(testproduct);
    }
}
