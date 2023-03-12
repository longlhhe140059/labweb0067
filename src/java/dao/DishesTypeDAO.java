/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DishesType;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author stick
 */
public interface DishesTypeDAO {
    public DishesType getDishesType(int id) throws ClassNotFoundException, SQLException;
    public ArrayList<DishesType> getAllType() throws ClassNotFoundException, SQLException;
}
