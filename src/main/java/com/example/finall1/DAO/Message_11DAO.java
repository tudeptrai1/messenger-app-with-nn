
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



public class Message_11DAO {
    public List<MessageDTO> getAllMessage(Connection conn) {
        List<MessageDTO> listMessage11 = new ArrayList<>();
         try {
            String qry = "SELECT * FROM `Message_11`";
            PreparedStatement prestm = conn.prepareStatement(qry);

            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
               listMessage11.add(new MessageDTO(rs.getInt(1), rs.getInt(2),rs.getInt(3), rs.getString(4), rs.getString(5), rs.getBoolean(6)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listMessage11;
    }
    public void addNew11Mess(int idUser, int idReceive, String content,Connection conn) {
        String time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        try {
            String qry = "INSERT INTO `Message_11`(`id_sender`, `id_received`, `time`, `content`, `url`) VALUES (?,?,?,?,false)";

            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, idUser);
            prestm.setInt(2, idReceive);
            prestm.setString(3, time);
            prestm.setString(4, content);
            prestm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addNew11Mess(MessageDTO mess, Connection conn) {
        try {
            String qry = "INSERT INTO `Message_11`(`id_sender`, `id_received`, `time`, `content`, `url`) VALUES (?,?,?,?,?)";

            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, mess.getId_sender());
            prestm.setInt(2, mess.getId_received());
            prestm.setString(3, mess.getTime());
            prestm.setString(4, mess.getContent());
            prestm.setBoolean(5, mess.isIsURL());
            
            System.out.println("\n\n"+prestm+"\n\n");
            
            prestm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
