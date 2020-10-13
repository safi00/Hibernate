package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session currentSession;
    private Transaction currentTransaction;
    public  AdresDAOHibernate(Session session) {
    this.currentSession = session;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        getCurrentSession().save(adres);
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        getCurrentSession().update(adres);
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        getCurrentSession().delete(adres);
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        Adres adres = (Adres) getCurrentSession().get(Adres.class, id);
        return adres;
    }

    @Override
    public List<Adres> findByWoonplaats(String woonplaats) throws SQLException {
//        List<Adres> adres = (Adres) getCurrentSession().get(Adres.class, woonplaats);
        List<Adres> adres = new ArrayList<Adres>();
        return adres;
    }

    @Override
    public Adres findByReiziger(Reiziger entity) throws SQLException {
        Adres adres = (Adres) getCurrentSession().get(Adres.class, entity.getIdNummer());
        return adres;
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adreslist = (List<Adres>) getCurrentSession().createQuery("from Adres").list();
        return adreslist; //adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id
    }
}
