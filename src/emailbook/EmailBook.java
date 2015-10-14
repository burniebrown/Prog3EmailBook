/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailbook;

import java.sql.*;
import emailbook.model.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Bobby
 */
public class EmailBook extends Application {
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    public EmailBook() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:src/database/emailcontacts.db");
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM CONTACTS");
        while (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("FIRSTNAME");
            String lastName = rs.getString("LASTNAME");
            String email = rs.getString("EMAIL");
            
            personData.add(new Person(firstName, lastName, email));
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EmailBook.class.getResource("EmailBook.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        EmailBookController emb = loader.getController();
        emb.setMainApp(this);
        
        stage.setTitle("EMAIL CONTACTS");
        stage.setScene(scene);
        stage.show();
    }
    
    public ObservableList<Person> getPersonData() throws SQLException {
        personData.clear();
        Connection c = DriverManager.getConnection("jdbc:sqlite:src/database/emailcontacts.db");
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM CONTACTS");
        while (rs.next()) {
            int id = rs.getInt("id");
            String firstName = rs.getString("FIRSTNAME");
            String lastName = rs.getString("LASTNAME");
            String email = rs.getString("EMAIL");
            
            personData.add(new Person(firstName, lastName, email));
        }
        return personData;
    }
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
