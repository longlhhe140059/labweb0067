/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import context.DBContext;
import dao.DishesTypeDAO;
import entity.DishesType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stick
 */
public class DishesTypeDAOImpl extends DBContext implements DishesTypeDAO {

    public DishesType getDishesType(int id) throws ClassNotFoundException, SQLException {
        String sql = "SELECT dt.TypeID,dt.NameType FROM Dishes d \n"
                + "INNER JOIN DishesType dt ON d.TypeID = dt .TypeID\n"
                + "WHERE d.DishesID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps= conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                DishesType dt = new DishesType();
                dt.setId(rs.getInt("TypeID"));
                dt.setName(rs.getNString("NameType"));
                
                return dt;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw  ex;
        } catch (SQLException ex) {
            Logger.getLogger(DishesTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }finally{
            closeConnection(conn, ps, rs);
        }
        return null;
    }
    public ArrayList<DishesType> getAllType() throws ClassNotFoundException, SQLException{
        String sql = "SELECT * FROM DishesType";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<DishesType> dtList = new ArrayList<>();
        
        try {
            conn = getConnection();
             ps = conn.prepareStatement(sql);
             rs = ps.executeQuery();
             while(rs.next()){
                 DishesType dt = new DishesType();
                 dt.setId(rs.getInt("TypeID"));
                 dt.setName(rs.getNString("NameType"));
                 
                 dtList.add(dt);
             }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (SQLException ex) {
            Logger.getLogger(DishesTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }finally{
            closeConnection(conn, ps, rs);
        }
        return dtList;
    }
    public static void main(String[] args) {
        DishesTypeDAO dao = new DishesTypeDAOImpl();
        try {
            ArrayList<DishesType> dtList = dao.getAllType();
            for (DishesType d : dtList) {
                System.out.println("d: "+d.getName());
            }
            DishesType dt = dao.getDishesType(1);
            System.out.println("dt: "+dt.getName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DishesTypeDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
