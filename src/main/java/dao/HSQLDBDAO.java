package dao;

import entity.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by airat on 27.04.16.
 */
public class HSQLDBDAO implements DAO{
    private Connection connection;
    private Statement statement;

    public HSQLDBDAO() throws DAOException {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            throw new DAOException("Неудадось загрузить драйвер. " + e.getMessage());
        }
        try {
            String path = "db/";
            String dbName = "imc";
            String connectionString = "jdbc:hsqldb:file:"+path+dbName;
            String login = "SA";
            String password = "";
            connection = DriverManager.getConnection(connectionString, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new DAOException("Неудадось подключится к БД. " + e.getMessage());
        }
    }

    @Override
    public List<String> getAllCategories() throws DAOException {
        ArrayList<String> cotegories = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select * from collections");
            while(resultSet.next()){
                String cotegory = resultSet.getString(1);
                cotegories.add(cotegory);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return cotegories;
    }

    @Override
    public List<Bill> getAllBills() throws DAOException {
        ArrayList<Bill> bills = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select * from bills");
            while(resultSet.next()){
                Bill bill = new Bill();
                bill.setCotegory(resultSet.getString("cotegory"));
                bill.setDate(resultSet.getDate("date"));
                bill.setPlace(resultSet.getString("place"));
                bill.setPrice(resultSet.getDouble("price"));
                bill.setProductName(resultSet.getString("product_name"));
                bills.add(bill);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return bills;
    }

}
