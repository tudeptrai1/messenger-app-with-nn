
package com.example.finall1.DAO;


import com.example.finall1.DTO.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class GroupChatDAO {
    public List<GroupChatDTO> getAllGroup(Connection conn) {
        List<GroupChatDTO> listGroup = new ArrayList<>();
         try {
            String qry = "SELECT * FROM `group`";

            PreparedStatement prestm = conn.prepareStatement(qry);

            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
                listGroup.add(new GroupChatDTO(rs.getInt(1),rs.getInt(2), rs.getString(3)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listGroup;
    }
    
    public boolean newGroup(int IdUser, String room_name, Connection conn) {
       try {
            String qry = "INSERT INTO `group`( `creator_id`, `name`,`created_at`) VALUES (?,?,?)";
              String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setString(1, String.valueOf(IdUser));
            prestm.setString(2, room_name);
            prestm.setString(3, currentTime);
            return prestm.executeUpdate() > 0;
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
