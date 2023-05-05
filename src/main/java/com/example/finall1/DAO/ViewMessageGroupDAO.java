
package com.example.finall1.DAO;


import com.example.finall1.DTO.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ViewMessageGroupDAO {
    public List<ViewMessageDTO> getAllViewMessage(Connection conn) {
        List<ViewMessageDTO> listView = new ArrayList<>();
         try {
            String qry = "SELECT * FROM `View_Message_Group`";

            PreparedStatement prestm = conn.prepareStatement(qry);

            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
               listView.add(new ViewMessageDTO(rs.getInt(1), rs.getInt(2)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listView;
    }

    public void viewMessage(int idUser, int id,Connection conn) {
        try {
            String qry = "INSERT INTO `View_Message_Group`(`id_user`, `id_mess`) VALUES (?,?)";
            
            PreparedStatement prestm = conn.prepareStatement(qry);
            prestm.setInt(1, idUser);
            prestm.setInt(2, id);
            
            prestm.executeUpdate();
                
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
