package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.Adres;
import nl.hu.dp.ovchip.domein.Reiziger;
import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres) throws SQLException;
    boolean update(Adres adres) throws SQLException;
    boolean delete(Adres adres) throws SQLException;
    Adres findById(int id) throws SQLException;
    List<Adres> findByWoonplaats(String woonplaats) throws SQLException;
    Adres findByReiziger(Reiziger reiziger) throws SQLException;
    List<Adres> findAll() throws SQLException;
}
