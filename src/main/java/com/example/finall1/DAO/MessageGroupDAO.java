
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



public class MessageGroupDAO {
    public List<MessageGroupDTO> getAllMessage(Connection conn) {
        List<MessageGroupDTO> listMessageGroup = new ArrayList<>();
        try {
            String qry = "SELECT * FROM `Message_Group`";

            PreparedStatement prestm = conn.prepareStatement(qry);

            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
                listMessageGroup.add(new MessageGroupDTO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBoolean(6)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listMessageGroup;
    }

    public void addNewMess(int idUser, int idGroup, String content,Connection conn) {
        String time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        try {
            String qry = "INSERT INTO `Message_Group`(`id_user`, `id_group`, `time`, `content`, `url`) VALUES (?,?,?,?,false)";

            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, idUser);
            prestm.setInt(2, idGroup);
            prestm.setString(3, time);
            prestm.setString(4, content);
            
            prestm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addNewMess(MessageGroupDTO mess,Connection conn) {
        try {
            String qry = "INSERT INTO `Message_Group`(`id_user`, `id_group`, `time`, `content`, `url`) VALUES (?,?,?,?,?)";

            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, mess.getUserID());
            prestm.setInt(2, mess.getId_group());
            prestm.setString(3, mess.getTime());
            prestm.setString(4, mess.getContent());
            prestm.setBoolean(5, mess.isIsURL());
            
            prestm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
