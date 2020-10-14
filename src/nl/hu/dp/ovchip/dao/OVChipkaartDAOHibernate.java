package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO  {
    private final Session     currentSession;
    private final Transaction currentTransaction;
    private       ReizigerDAO reizD;
    private       ProductDAO  prodD;

    public OVChipkaartDAOHibernate(Session session) {
        this.currentSession = session;
        this.currentTransaction  = currentSession.beginTransaction();
    }


    public void   setRdao(ReizigerDAO dao) {
        this.reizD = dao;
    }
    public void   setPdao(ProductDAO  dao) { this.prodD = dao;}

    public Session getCurrentSession() {
        return currentSession;
    }
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        getCurrentSession().save(ovChipkaart);
        return true;
    }

    @Override
    public boolean addProduct(OVChipkaart ovChipkaart, Product product, String status, Date lastUpdate) throws SQLException {
        getCurrentSession().createQuery("INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer, status, last_update) VALUES (" + ovChipkaart.getKaart_nummer() + "," + product.getProduct_nummer() + ",'" + status + "'," + lastUpdate +")");
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        getCurrentSession().update(ovChipkaart);
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        getCurrentSession().delete(ovChipkaart);
        return true;
    }

    @Override
    public OVChipkaart findById(long id) throws SQLException {
        return getCurrentSession().get(OVChipkaart.class, id);
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        return (List<OVChipkaart>) getCurrentSession().createQuery("from OVChipkaart WHERE reiziger_id = " + reiziger.getIdNummer()).list();
    }

    @Override
    public List<OVChipkaart> findByProduct(Product product) throws SQLException {
        return (List<OVChipkaart>) getCurrentSession().createQuery("from OVChipkaart ov LEFT JOIN ov_chipkaart_product ocp on ov.kaart_nummer = ocp.kaart_nummer where product_nummer = " + product.getProduct_nummer()).list();
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        return (List<OVChipkaart>) getCurrentSession().createQuery("from OVChipkaart").list();
    }
}
