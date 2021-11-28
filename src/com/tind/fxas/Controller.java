package com.tind.fxas;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //Table
    @FXML
    private TableView<Customer> tableView;

    //Columns
    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, Integer> ageColumn;

    @FXML
    private TableColumn<Customer, Integer> numberColumn;
    @FXML
    private TableColumn<Customer,String> emailC;
    @FXML
    private TableColumn<Customer,String>adressC;

    //Text input
    @FXML
    private TextField nameInput;

    @FXML
    private TextField ageInput;

    @FXML
    private TextField numberInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField adressInput;
    @FXML
    private TextField alert;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("age"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("number"));
        emailC.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        adressC.setCellValueFactory(new PropertyValueFactory<Customer, String>("adress"));

    }

    //Submit button
    @FXML
    void submit(ActionEvent event) {
        Customer customer = new Customer(nameInput.getText(),
                Integer.parseInt(ageInput.getText()),
                Integer.parseInt(numberInput.getText()),
                (emailInput.getText()),
                (adressInput.getText()));
        ObservableList<Customer> customers = tableView.getItems();


        tableView.setItems(customers);
        if(!customers.contains(customer))
            customers.add(customer);
        else
        alert.setText("DUPLICATE");

    }
    public void initialize1(URL url, ResourceBundle resourceBundle){


    }

    @FXML
    void removeCustomer(ActionEvent event) {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedID);
        alert.setText("");
    }
}