
package com.example.finall1.DAO;


import com.example.finall1.DTO.*;


import Func.Hyrid_Encryption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDAO {

    public boolean addNewAccount(UserDTO user,Connection conn) {
        try {
            String qry = "INSERT INTO `User`( `email`, `password`, `name`, `sex`, `birthday`, `online_status`, `is_active`,`ServerBlock`) VALUES (?,?,?,?,?,?,?,false);";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setString(1, user.getEmail());
            prestm.setString(2, Hyrid_Encryption.convertToMd5(user.getPassword()));
            prestm.setString(3, user.getName());
            prestm.setBoolean(4, (user.getSex().compareToIgnoreCase("Nam") == 0));
            prestm.setString(5, user.getBirthday());
            prestm.setBoolean(6, user.isIsOnline());
            prestm.setBoolean(7, user.isIsActive());
            
            return prestm.executeUpdate() > 0;
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public UserDTO getUserByEmail(String email,Connection conn) {
        try {
            String qry = "SELECT * FROM `User` WHERE `email` = ?";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setString(1, email);
            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
                return new UserDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5) ? "Nam" : "Nữ",
                        rs.getString(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8),
                        rs.getBoolean(9));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public UserDTO getUserById(String id,Connection conn) {
        try {
            String qry = "SELECT * FROM `User` WHERE `id_user` = ?";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setString(1, id);
            ResultSet rs = prestm.executeQuery();

            if (rs == null) {
                return null;
            }
            while (rs.next()) {
                return new UserDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5) ? "Nam" : "Nữ",
                        rs.getString(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8),
                        rs.getBoolean(9));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

   

    public boolean activeAccout(String email,Connection conn) {
        try {
            String qry = "UPDATE `User` SET `is_active`=true WHERE `email` = ?";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setString(1, email);            
            
            return prestm.executeUpdate() > 0;
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateOnlineStatus(String email,boolean status,Connection conn) {
        try {
            String qry = "UPDATE `User` SET `online_status`=? WHERE `email` = ?";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setBoolean(1, status);
            prestm.setString(2, email);            
            
            return prestm.executeUpdate() > 0;
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<UserDTO> getAllUser(Connection conn) {
        List<UserDTO> listRoom = new ArrayList<>();
         try {
            String qry = "SELECT * FROM `User`";
            PreparedStatement prestm = conn.prepareStatement(qry);

            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
                listRoom.add(new UserDTO(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getBoolean(5) ? "Nam":"Nữ", 
                        rs.getString(6), 
                        rs.getBoolean(7), 
                        rs.getBoolean(8),
                        rs.getBoolean(9)
                       
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listRoom;
    }

    public void logOutAccount(int id,Connection conn) {
        try {
            String qry = "UPDATE `User` SET `online_status`=false WHERE `id` = ?";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, id);
            
            prestm.executeUpdate();
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean blockUnblockUser(int id,boolean status,Connection conn) {
        try {
            String qry = "UPDATE `User` SET `ServerBlock`= ? WHERE `Id`= ?;";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setBoolean(1, status);
            prestm.setInt(2, id);

            
            return (prestm.executeUpdate() > 0);
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
