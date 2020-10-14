package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private final Session     currentSession;
    private final Transaction currentTransaction;
    private       ReizigerDAO reizD;
    public  AdresDAOHibernate(Session session) {
        this.currentSession = session;
        this.currentTransaction  = currentSession.beginTransaction();
    }

    public void   setRdao(ReizigerDAO dao) {
        this.reizD = dao;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    @Override
    public boolean save(Adres adres) {
        getCurrentSession().save(adres);
        return true;
    }

    @Override
    public boolean update(Adres adres) {
        getCurrentSession().update(adres);
        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        getCurrentSession().delete(adres);
        return true;
    }

    @Override
    public Adres findById(int id) {
        return getCurrentSession().get(Adres.class, id);
    }

    @Override
    public List<Adres> findByWoonplaats(String woonplaats) {
        return (List<Adres>) getCurrentSession().createQuery("from Adres where woonplaats = '" + woonplaats + "'").list();
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        return getCurrentSession().get(Adres.class, reiziger.getIdNummer());
    }

    @Override
    public List<Adres> findAll() {
        return (List<Adres>) getCurrentSession().createQuery("from Adres").list(); //adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id
    }
}
