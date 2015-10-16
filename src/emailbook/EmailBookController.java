/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailbook;

import emailbook.model.Person;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Bobby
 */
public class EmailBookController implements Initializable {
  
    @FXML
    private TextField firstNameLabel;
    @FXML
    private TextField lastNameLabel;
    @FXML
    private TextField emailLabel;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> emailColumn;
    @FXML
    private Button addButton;
    
    private EmailBook emailBook;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    @FXML
    private void addButtonHandle(ActionEvent event) {
        String firstName = firstNameLabel.getText();
        String lastName = lastNameLabel.getText();
        String email = emailLabel.getText();
        
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:emailcontacts.db");
            Statement s = c.createStatement();
            s.execute("INSERT INTO CONTACTS(FIRSTNAME, LASTNAME, EMAIL)  VALUES ('"
                    + firstName + "','" + lastName +
                      "','" + email + "')");
            personTable.setItems(emailBook.getPersonData());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void deleteButtonHandle() {
        try {
            int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
            if(selectedIndex < 0) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("No person selected!");
                alert.setContentText("Please select a person on the table");
                alert.showAndWait();
                return;
            }
            Person selectedPerson = personTable.getItems().get(selectedIndex);
            selectedPerson.delete();
            personTable.setItems(this.emailBook.getPersonData());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
    }    
    
    public void setMainApp(EmailBook emailBook) throws SQLException {
        this.emailBook = emailBook;
        personTable.setItems(emailBook.getPersonData());
    }
}
