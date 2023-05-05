
package com.example.finall1.DAO;

import com.example.finall1.DTO.*;


//import Func.Hyrid_Encryption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class BlockUserDAO {

    public List<BlockUserDTO> getAllListBlock(Connection conn) {
        List<BlockUserDTO> listBlockUser = new ArrayList<>();
         try {
            String qry = "SELECT * FROM `block_user`";
            
            PreparedStatement prestm = conn.prepareStatement(qry);

            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
               listBlockUser.add(new BlockUserDTO(rs.getInt(1), rs.getInt(2)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listBlockUser;
    }

    public boolean block(int idSender, int idReceive,Connection conn) {
        try {
            String qry = "INSERT INTO `block_user`(`user1_id`, `user2_id`) VALUES (?,?);";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, idSender);
            prestm.setInt(2, idReceive);
            
            return prestm.executeUpdate() > 0;
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean unblock(int id, int idblock,Connection conn) {
        try {
            String qry = "DELETE FROM `block_user` WHERE `user1_id`=? AND `user2_id`=?;";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, id);
            prestm.setInt(2, idblock);
            
            return prestm.executeUpdate() > 0;
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
