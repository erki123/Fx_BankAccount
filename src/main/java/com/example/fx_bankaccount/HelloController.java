package com.example.fx_bankaccount;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    ResultSet resultSet;
    Connection connection;
    ResultSet recs;
    String transactionType;
    String firstAccount, secondAccount;
    String transactionAmount;
    String balance, investments, pension, amount;


    @FXML
    private FontAwesomeIconView notificationIcon;

    @FXML
    public ImageView imgLogo;

    @FXML
    private TextField enterSum;

    @FXML
    private ComboBox<String> fromAccount;

    @FXML
    private ComboBox<String> toAccount;

    @FXML
    private Button makeTransaction;

    @FXML
    private Label lblAccount;

    @FXML
    private Label lblInvestments;

    @FXML
    private Label lblPension;

    @FXML
    private Label lblMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        fromAccount.getItems().addAll("Bank Account", "Investments", "Pension");
        toAccount.getItems().addAll("Bank Account", "Investments", "Pension");

        String bankAccount = "Bank Account";
        String investmentsAccount = "Investments";
        String pensionAccount = "Pension";



        Circle circleImg = new Circle(20, 20, 15);
        imgLogo.setClip(circleImg);

        try {
            String dbUrl = "jdbc:mysql://localhost/bank_account";
            Connection conn = DriverManager.getConnection(dbUrl, "root", "asd123");
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM bank_customers";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String balance = rs.getString("Balance");
                String investments = rs.getString("Investments");
                String pension = rs.getString("Pension");
                lblAccount.setText(balance);
                lblInvestments.setText(investments);
                lblPension.setText(pension);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void onEnteredSum(KeyEvent event) {

    }




    @FXML
    void makeTransaction(ActionEvent event) {
        String fromAcc = fromAccount.getSelectionModel().getSelectedItem();
        String toAcc = toAccount.getSelectionModel().getSelectedItem();
        int amountInt = Integer.parseInt(enterSum.getText());

        if (fromAcc.equals(toAcc)) {
            lblMessage.setText("Cannot transfer to the same account.");
            return;
        }

        int fromBalance = getAccountBalance(fromAcc);
        int toBalance = getAccountBalance(toAcc);

        if (fromBalance >= amountInt) {
            fromBalance -= amountInt;
            toBalance += amountInt;
            setAccountBalance(fromAcc, fromBalance);
            setAccountBalance(toAcc, toBalance);
            updateDisplay();
        } else {
            lblMessage.setText("Insufficient funds.");
        }
    }

    private int getAccountBalance(String accountName) {
        switch (accountName) {
            case "Bank Account":
                return Integer.parseInt(lblAccount.getText());
            case "Investments":
                return Integer.parseInt(lblInvestments.getText());
            case "Pension":
                return Integer.parseInt(lblPension.getText());
            default:
                return 0;
        }
    }

    private void setAccountBalance(String accountName, int newBalance) {
        switch (accountName) {
            case "Bank Account":
                lblAccount.setText(String.valueOf(newBalance));
                break;
            case "Investments":
                lblInvestments.setText(String.valueOf(newBalance));
                break;
            case "Pension":
                lblPension.setText(String.valueOf(newBalance));
                break;
        }
    }

    private void updateDisplay() {
        lblMessage.setText("Transaction successful.");
    }



    public HelloController() {
        makeTheConnection();
    }

    public void makeTheConnection() {
        System.out.println("Started==================");

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bank_account", "root", "asd123");
//https://stackoverflow.com/questions/55454688/how-to-fix-error-with-mysql-because-of-timezone-change

            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //Statement statement=connection.createStatement();
            String sql = "SELECT * FROM bank_customers;";// WHERE username = '"+ userName.getText()+"' AND password = '"+userPassword.getText()+"';";
            resultSet = statement.executeQuery(sql);

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String f = resultSet.getString("first_name");
                    String l = resultSet.getString("last_name");
                    String b = resultSet.getString("Balance");
                    String i = resultSet.getString("Investments");
                    String p = resultSet.getString("Pension");
                    System.out.println(f + " | " + l + " | " + b + " | " + i + " | " + p);
                }
            } else {
                System.out.println("not connected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    






}//xxxxxxxxxxxxxxxxxxxxx