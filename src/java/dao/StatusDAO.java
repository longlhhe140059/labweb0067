/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Status;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author stick
 */
public interface StatusDAO {
    public Status getDishesStatus(int id) throws SQLException, ClassNotFoundException;
    public ArrayList<Status> getAllStatus() throws ClassNotFoundException, SQLException;
}
