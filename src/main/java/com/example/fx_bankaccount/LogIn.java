package com.example.fx_bankaccount;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import java.sql.*;

public class LogIn {

    ResultSet resultSet;
    Connection connection;
    CallableStatement myStmt = null;


    @FXML
    TextField logintxt;

    @FXML
    TextField pwdtxt;

    @FXML
    Button btn;

    @FXML
    Label msgLbl;

    @FXML
    void logIn (MouseEvent event) {


        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/auth","root","asd123");

            myStmt = connection.prepareCall("{call user_auth(?,?,?)}");
            myStmt.setString(1, logintxt.getText());
            myStmt.setString(2, pwdtxt.getText());
            myStmt.registerOutParameter(3, Types.VARCHAR);
            myStmt.execute();

            System.out.println("Executed....\n");

            String id = myStmt.getString(3);
            if(id!=null){
                System.out.println("Correct LogIn");
                myStmt = connection.prepareCall("{call user_info(?)}");
                String first_name = null;
                myStmt.setString(1, first_name);
                myStmt.execute();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                Parent root = loader.load();

                // Get controller of BankAccount.fxml and pass user info
                HelloController bankAccountController = loader.getController();
                //bankAccountController.resultSet(first_name); // Pass user ID or other relevant info

                // Set the scene to the stage
                Stage stage = (Stage) btn.getScene().getWindow(); // Assuming 'btn' is a component in your current scene
                stage.setScene(new Scene(root));
                stage.show();

            }else {
                msgLbl.setText("Wrong username or password");
            }

        }  catch (SQLException | IOException e) {
            e.printStackTrace();
        }



    }

}
