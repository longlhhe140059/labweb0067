/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import context.DBContext;
import dao.StatusDAO;
import entity.Status;
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
public class StatusDAOImpl extends DBContext implements StatusDAO {

    public Status getDishesStatus(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT s.StatusID,s.StatusName FROM Dishes d\n"
                + "INNER JOIN [Status] s ON d.StatusID=s.StatusID\n"
                + "WHERE d.DishesID = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Status s = new Status();
                s.setId(rs.getInt("StatusID"));
                s.setName(rs.getNString("StatusName"));

                return s;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StatusDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            closeConnection(conn, ps, rs);
        }
        return null;
    }
    public ArrayList<Status> getAllStatus() throws ClassNotFoundException, SQLException{
        String sql = "SELECT * FROM [Status] s WHERE s.StatusID>=8 AND s.StatusID<=11";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Status> sList = new ArrayList<>();
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs =ps.executeQuery();
            while(rs.next()){
                Status s = new Status();
                s.setId(rs.getInt("StatusID"));
                s.setName(rs.getNString("StatusName"));
                sList.add(s);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StatusDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }finally{
            closeConnection(conn, ps, rs);
        }
        return sList;
    }
    public static void main(String[] args) {
        StatusDAO dao = new StatusDAOImpl();
        try {
            ArrayList<Status> slist = dao.getAllStatus();
            for (Status s : slist) {
                System.out.println("status: "+s.getName());
            }
            System.out.println("dishes1 status:"+dao.getDishesStatus(1).getName());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(StatusDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
