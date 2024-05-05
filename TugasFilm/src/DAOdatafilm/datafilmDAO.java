/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOdatafilm;
import java.sql.*;
import java.util.*;
import koneksi.connector;
import model.*;
import DAOimplement.datafilmimplement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ASUS
 */
public class datafilmDAO implements datafilmimplement {
    Connection connection;
    
    final String select = "SELECT * FROM movie";
    final String insert = "INSERT INTO `movie`(`judul`, `alur`, `penokohan`, `akting`, `nilai`) VALUES (?,?,?,?,?)";
    final String update = "UPDATE movie SET alur=?, penokohan=?, akting=?, nilai=? WHERE judul=?";

            
    public datafilmDAO(){
        connection = connector.connection();
    }

    @Override
    
public void insert(datafilm p) {
    PreparedStatement statement = null;
    try{
        // Menghitung nilai berdasarkan rumus yang diberikan
        double nilai = (p.getAlur() + p.getPenokohan() + p.getAkting()) / 3.0;

        // Menyiapkan pernyataan SQL dengan parameter
        statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, p.getJudul());
        statement.setDouble(2, p.getAlur());
        statement.setDouble(3, p.getPenokohan());
        statement.setDouble(4, p.getAkting());
        statement.setDouble(5, nilai); // Menyetel nilai yang telah dihitung
        statement.executeUpdate();
        
        // Mendapatkan kunci yang dihasilkan (jika ada)
        ResultSet rs = statement.getGeneratedKeys();
        while(rs.next()){
            p.setJudul(rs.getString(1));
        }
    }catch(SQLException ex){
        ex.printStackTrace();
    }finally{
        try{
            statement.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}

@Override
public void update(datafilm p) {
    PreparedStatement statement = null;
    try {
        statement = connection.prepareStatement(update);
        
        statement.setDouble(1, p.getAlur());
        statement.setDouble(2, p.getPenokohan());
        statement.setDouble(3, p.getAkting());
        statement.setDouble(4, (p.getAlur() + p.getPenokohan() + p.getAkting()) / 3.0); // Menghitung nilai berdasarkan rumus yang diberikan
        statement.setString(5, p.getJudul()); // Menyetel judul sebagai kondisi WHERE
        
        statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}



    

    @Override
    public void delete(String judul) {
    PreparedStatement statement = null;
    try {
        // Menyiapkan pernyataan SQL untuk menghapus data berdasarkan judul
        String deleteQuery = "DELETE FROM movie WHERE judul=?";
        statement = connection.prepareStatement(deleteQuery);
        
        // Mengatur judul sebagai parameter pada pernyataan SQL
        statement.setString(1, judul);
        
        // Menjalankan pernyataan SQL untuk menghapus data
        statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

    @Override
    public List<datafilm> getAll() {
        List<datafilm> dp = null;
        try{
            dp = new ArrayList<datafilm>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while(rs.next()){
                datafilm film = new datafilm();
                film.setJudul(rs.getString("judul"));
                film.setAlur(rs.getDouble("alur"));
                film.setPenokohan(rs.getDouble("penokohan"));
                film.setAkting(rs.getDouble("akting"));
                film.setNilai(rs.getDouble("nilai"));
                dp.add(film);
                
            }
            
        }catch(SQLException ex){
            Logger.getLogger(datafilmDAO.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        return dp;
    }
}
