
package com.example.finall1.DAO;


import com.example.finall1.DTO.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class JoinGroupDAO {
    public List<GroupMemberDTO> getAllJoinGroup(Connection conn) {
        List<GroupMemberDTO> listJoinGroup = new ArrayList<>();
         try {
            String qry = "SELECT * FROM `Join_Group`";

            PreparedStatement prestm = conn.prepareStatement(qry);

            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
                listJoinGroup.add(new GroupMemberDTO(rs.getInt(1), rs.getInt(2)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listJoinGroup;
    }

    public boolean joinToGroup(int idUser, int idGroup,Connection conn) {
        try {
            String qry = "INSERT INTO `Join_Group`(`id_user`, `id_group`) VALUES (?,?)";
           
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, idUser);
            prestm.setInt(2, idGroup);

            if(prestm.executeUpdate() != 0){
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean removeJoinGroup(int idUser, int idRoom,Connection conn) {
        try {
            String qry = "DELETE FROM `Join_Group` WHERE `id_user`=? AND `id_group` = ?";

            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, idUser);
            prestm.setInt(2, idRoom);

            if(prestm.executeUpdate() != 0){
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean blockGroup(int idUser, int idGroup,Connection conn) {
        try {
            String qry = "INSERT INTO `Block_Group`(`id_user`, `id_group`) VALUES (?,?)";

            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, idUser);
            prestm.setInt(2, idGroup);

            if(prestm.executeUpdate() != 0){
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean unblockGroup(int idUser, int idGroup,Connection conn) {
        try {
            String qry = "DELETE FROM `Block_Group` WHERE `id_user` = ? AND `id_group` = ?";

            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, idUser);
            prestm.setInt(2, idGroup);

            if(prestm.executeUpdate() != 0){
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
}
