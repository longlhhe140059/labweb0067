/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dishes;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author stick
 */
public interface DishesDAO {
    public ArrayList<Dishes> searchDishes(int numPerPage, int pageIndex, String SearchName, int typeID) throws SQLException,ClassNotFoundException;
    public int getTotalPage(int numPerPage, String SearchName, int typeID) throws SQLException, ClassNotFoundException;
    public Dishes getDishesByID(int id) throws ClassNotFoundException, SQLException;
    public void addDishes(Dishes d) throws SQLException, ClassNotFoundException;
    public void updateDishes(Dishes d) throws ClassNotFoundException, SQLException;
}
