/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import context.DBContext;
import dao.DishesDAO;
import entity.Dishes;
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
public class DishesDAOImpl extends DBContext implements DishesDAO {

    public ArrayList<Dishes> searchDishes(int numPerPage, int pageIndex, String SearchName, int typeID) throws SQLException,ClassNotFoundException {
        String sql = "SELECT tbl.DishesID,tbl.NameDishes,tbl.TypeID,tbl.StatusID,tbl.Price,tbl.Note\n"
                + "FROM(SELECT ROW_NUMBER() OVER (ORDER BY d.DishesID ASC) as row_num, * \n"
                + "FROM Dishes d WHERE d.NameDishes like ? ";
        if (typeID > 0) {
            sql += "AND d.TypeID = " + typeID;
        }
        sql += " ) tbl ";
        int startItem = numPerPage * (pageIndex - 1) + 1;
        int endItem = startItem + numPerPage - 1;
        sql += " WHERE row_num >= " + startItem + " AND row_num <= " + endItem + " ";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Dishes> dList = new ArrayList<>();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setNString(1, "%" + SearchName + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Dishes d = new Dishes();
                d.setId(rs.getInt("DishesID"));
                d.setName(rs.getNString("NameDishes"));
                DishesTypeDAOImpl typeDAO = new DishesTypeDAOImpl();
                d.setType(typeDAO.getDishesType(d.getId()));
                StatusDAOImpl statusDAO = new StatusDAOImpl();
                d.setStatus(statusDAO.getDishesStatus(d.getId()));
                d.setPrice(rs.getInt("Price"));
                d.setNote(rs.getNString("Note"));

                dList.add(d);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (SQLException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            closeConnection(conn, ps, rs);
        }
        return dList;
    }

    public int getTotalPage(int numPerPage, String SearchName, int typeID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(d.DishesID)as'total' FROM Dishes d WHERE d.NameDishes like ? ";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            if (typeID > 0) {
                sql += " AND d.TypeID=" + typeID;
            }
            ps = conn.prepareStatement(sql);
            ps.setNString(1, "%" + SearchName + "%");
            rs = ps.executeQuery();
            if(rs.next()){
                int totalPage = rs.getInt("total") / numPerPage;
                if (rs.getInt("total") % numPerPage != 0) {
                    totalPage++;
                }
                return totalPage;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (SQLException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            closeConnection(conn, ps, rs);
        }
        return 0;
    }

    public Dishes getDishesByID(int id) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM Dishes d WHERE d.DishesID= ? ";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Dishes d = new Dishes();
                d.setId(rs.getInt("DishesID"));
                d.setName(rs.getNString("NameDishes"));
                DishesTypeDAOImpl typeDAO = new DishesTypeDAOImpl();
                d.setType(typeDAO.getDishesType(d.getId()));
                StatusDAOImpl statusDAO = new StatusDAOImpl();
                d.setStatus(statusDAO.getDishesStatus(d.getId()));
                d.setPrice(rs.getInt("Price"));
                d.setNote(rs.getNString("Note"));
                d.setImage(rs.getNString("img"));
                return d;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (SQLException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            closeConnection(conn, ps, rs);
        }
        return null;
    }

    public void addDishes(Dishes d) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Dishes(NameDishes,TypeID,Price,Note,img,StatusID) \n"
                + "VALUES (?,?,?,?,?,?);";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setNString(1, d.getName());
            ps.setInt(2, d.getType().getId());
            ps.setInt(3, d.getPrice());
            ps.setNString(4, d.getNote());
            ps.setNString(5, d.getImage());
            ps.setInt(6, d.getStatus().getId());
            ps.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (SQLException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            closeConnection(conn, ps, rs);
        }
    }

    
    public static void main(String[] args) {
        DishesDAO dao = new DishesDAOImpl();
        try {
            ArrayList<Dishes> dlist = dao.searchDishes(5, 2, "", 1);
            for (Dishes dishes : dlist) {
                System.out.println("dihes: "+dishes.getName());
            }
            Dishes d = dao.getDishesByID(1);
            System.out.println("ok:"+d.getName());
            d.setName("more dishes");
            dao.addDishes(d);
            d.setName("update dishes");
            dao.updateDishes(d);
            
        } catch (SQLException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updateDishes(Dishes d) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE Dishes\n"
                + "SET NameDishes=?,Price=?,Note=?,img=?,StatusID=?,TypeID=?\n"
                + "WHERE DishesID =1";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setNString(1, d.getName());
            ps.setInt(2, d.getPrice());
            ps.setNString(3, d.getNote());
            ps.setString(4, d.getImage());
            ps.setInt(5, d.getStatus().getId());
            ps.setInt(6, d.getType().getId());
            ps.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (SQLException ex) {
            Logger.getLogger(DishesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }finally{
            closeConnection(conn, ps, rs);
        }
    }
}
