/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailbook.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Model class for a person.
 * 
 * @author Bobby
 */
public class Person {
    
    private final StringProperty firstName;
    private StringProperty lastName;
    private StringProperty emailAddress;
 
    /**
     * Constructor with some initial data
     * 
     * @param firstName
     * @param lastName 
     * @param emailAddress
     */
    public Person(String firstName, String lastName, String emailAddress) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.emailAddress = new SimpleStringProperty(emailAddress);
    }
    
    public void delete() {
        String sql = "DELETE FROM CONTACTS WHERE FIRSTNAME='" + this.getFirstName() +
                 "' AND LASTNAME='" + this.getLastName() + "'";
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:emailcontacts.db");
            Statement s = c.createStatement();
            boolean rs = s.execute(sql);
            if(!rs) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("Information");
                alert.setContentText("Deleted Succesfully!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    
    public void setFirstname(String firstName) {
        this.firstName.set(firstName);
    }
    
    public StringProperty getFirstNameProperty() {
        return firstName;
    }
    
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public StringProperty getLastNameProperty() {
        return lastName;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress.get();
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }
    
    public StringProperty getEmailProperty() {
        return emailAddress;
    }
}
