package service;

import dao.DAO;
import dao.DAOException;
import dao.HSQLDBDAO;
import entity.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by airat on 28.05.16.
 */
public class Service {
    private static Service ourInstance;
    private DAO dao;
    private List<Bill> bills;
    public static synchronized Service getInstance() {
        if(ourInstance == null){
            try {
                ourInstance = new Service();
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        return ourInstance;
    }

    private Service() throws DAOException {
        dao = new HSQLDBDAO();
        bills = dao.getAllBills();
    }

    public ArrayList<String> getCotegories(String place){
        ArrayList<String> cotegories = new ArrayList<>();
        cotegories.add("все");
        for (Bill bill : bills) {
            if(!cotegories.contains(bill.getCotegory()) && (place.equals("все") || place.equals(bill.getPlace()))){
                cotegories.add(bill.getCotegory());
            }
        }
        return cotegories;
    }

    public ArrayList<String> getPlaces(String cotegory){
        ArrayList<String> places = new ArrayList<>();
        places.add("все");
        for (Bill bill : bills) {
            if(!places.contains(bill.getPlace()) && (cotegory.equals("все") || cotegory.equals(bill.getCotegory()))){
                places.add(bill.getPlace());
            }
        }
        return places;
    }

    public ArrayList<Bill> getBills(String cotegory, String place){
        ArrayList<Bill> billArrayList = new ArrayList<>();
        Bill allBill = new Bill();
        allBill.setProductName("общая сумма");
        billArrayList.add(allBill);
        double summ = 0;
        for (Bill bill : bills) {
            if((cotegory.equals("все") || cotegory.equals(bill.getCotegory())) &&
                    (place.equals("все") || place.equals(bill.getPlace()))){
                billArrayList.add(bill);
                summ += bill.getPrice();
            }
        }
        allBill.setPrice(summ);
        return billArrayList;
    }

}
