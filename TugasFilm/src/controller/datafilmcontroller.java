/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.List;
import DAOdatafilm.datafilmDAO;
import DAOimplement.datafilmimplement;
import model.*;
import view.MainView;


/**
 *
 * @author ASUS
 */
public class datafilmcontroller {
    MainView frame;
    datafilmimplement impldatafilm;
    List<datafilm> dp;
    
    public datafilmcontroller(MainView frame){
        this.frame = frame;
        impldatafilm = new datafilmDAO();
        dp = impldatafilm.getAll();
        
    }
    public void isitabel(){
        dp = impldatafilm.getAll();
        modeltabeldatafilm mp = new modeltabeldatafilm(dp);
        frame.getTabelDatafilm().setModel(mp);
    }
    
    public void insert(){
    datafilm dp = new datafilm();
    dp.setJudul(frame.getJTxtjudul().getText());
    
    double alur = Double.parseDouble(frame.getJTxtalur().getText());
    dp.setAlur(alur);
    
    double penokohan = Double.parseDouble(frame.getJTxtpenokohan().getText());
    dp.setPenokohan(penokohan);
    
    double akting = Double.parseDouble(frame.getJTxtakting().getText());
    dp.setAkting(akting);
    
    impldatafilm.insert(dp);
}
    public void update(){
    datafilm dp = new datafilm();
    dp.setJudul(frame.getJTxtjudul().getText());
    
    double alur = Double.parseDouble(frame.getJTxtalur().getText());
    dp.setAlur(alur);
    
    double penokohan = Double.parseDouble(frame.getJTxtpenokohan().getText());
    dp.setPenokohan(penokohan);
    
    double akting = Double.parseDouble(frame.getJTxtakting().getText());
    dp.setAkting(akting);
    
    // Hitung nilai berdasarkan rumus yang diberikan
    double nilai = (alur + penokohan + akting) / 3.0;
    dp.setNilai(nilai);
    
    impldatafilm.update(dp);
}
    public void delete() {
    // Dapatkan judul film dari JTextField di MainView
    String judul = frame.getJTxtjudul().getText();

    // Panggil metode delete dari implementasi datafilmimplement
    impldatafilm.delete(judul);
    
    // Perbarui tabel setelah penghapusan
    isitabel();
}



}
