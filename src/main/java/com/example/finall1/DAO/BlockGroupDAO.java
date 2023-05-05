
package com.example.finall1.DAO;


import com.example.finall1.DTO.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class BlockGroupDAO {
    public List<BlockGroupDTO> getAllBlockGroup(Connection conn) {
        List<BlockGroupDTO> listBlockGroup = new ArrayList<>();
         try {
            String qry = "SELECT * FROM `Block_Group`";

            PreparedStatement prestm = conn.prepareStatement(qry);

            ResultSet rs = prestm.executeQuery();

            if (rs == null || !rs.isBeforeFirst()) {
                return null;
            }

            while (rs.next()) {
                listBlockGroup.add(new BlockGroupDTO(rs.getInt(1), rs.getInt(2)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listBlockGroup;
    }
    
}
