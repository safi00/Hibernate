package nl.hu.dp.ovchip.dao;

import nl.hu.dp.ovchip.domein.OVChipkaart;
import nl.hu.dp.ovchip.domein.Product;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean addOVChipkaart(OVChipkaart ovc, Product product, String status, Date lastUpdate) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    Product findById(long idnummer) throws SQLException;
    List<Product> findByOVChipKaart(long ov) throws SQLException;
    List<Product> findAll() throws SQLException;
}
