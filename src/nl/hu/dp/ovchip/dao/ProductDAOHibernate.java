package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO{
    private final Session        currentSession;
    private final Transaction    currentTransaction;
    private       OVChipkaartDAO ovChipD;

    public ProductDAOHibernate(Session session) {
        this.currentSession = session;
        this.currentTransaction  = currentSession.beginTransaction();
    }


    public void   setOVCDao(OVChipkaartDAO dao) {
        this.ovChipD = dao;
    }

    public Session getCurrentSession() {
        return currentSession;
    }
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        getCurrentSession().save(product);
        return true;
    }

    @Override
    public boolean addOVChipkaart(OVChipkaart ovc, Product product, String status, Date lastUpdate) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        getCurrentSession().update(product);
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        getCurrentSession().delete(product);
        return true;
    }

    @Override
    public Product findById(long id) throws SQLException {
        return getCurrentSession().get(Product.class, id);
    }

    @Override
    public List<Product> findByOVChipKaart(long ov) throws SQLException {
        return (List<Product>) getCurrentSession().createQuery("from Product pro LEFT JOIN ov_chipkaart_product ocp on pro.product_nummer = ocp.product_nummer where kaart_nummer = " + ov ).list();
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return (List<Product>) getCurrentSession().createQuery("from Product").list();
    }
}
