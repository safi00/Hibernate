package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private final Session        currentSession;
    private final Transaction    currentTransaction;
    private       AdresDAO       adresD;
    private       OVChipkaartDAO ovChipD;

    public  ReizigerDAOHibernate(Session session) {
        this.currentSession = session;
        this.currentTransaction  = currentSession.beginTransaction();
    }


    public void   setAdresDao(AdresDAO dao) {
        this.adresD  = dao;
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
    public boolean save(Reiziger reiziger) throws SQLException {
        getCurrentSession().save(reiziger);
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        getCurrentSession().update(reiziger);
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        getCurrentSession().delete(reiziger);
        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        return getCurrentSession().get(Reiziger.class, id);
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        Date date = java.sql.Date.valueOf(datum);
        return (List<Reiziger>) getCurrentSession().createQuery("from Reiziger where geboortedatum  = " + date ).list();
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        return (List<Reiziger>) getCurrentSession().createQuery("from Reiziger").list();
    }
}
