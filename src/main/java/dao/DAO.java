package dao;


import entity.Bill;

import java.util.List;

/**
 * Created by airat on 26.04.16.
 */
public interface DAO {
    List<String> getAllCategories() throws DAOException;
    List<Bill> getAllBills() throws DAOException;
}


