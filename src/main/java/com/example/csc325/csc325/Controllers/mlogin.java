package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class mlogin {

    @FXML
    public Button loginBtn;

    @FXML
    public PasswordField hiddenPasswordField;

    @FXML
    public TextField visiblePasswordField;


    @FXML
    public Label lblErrorMsg;
    public TextField usernameField;
    public TextField passwordField;

//    public void togglePasswordVisibility(ActionEvent actionEvent) {
//        if (visiblePasswordField.isVisible()) {
//            hiddenPasswordField.setText(visiblePasswordField.getText());
//            hiddenPasswordField.setVisible(true);
//            passwordField.setText(getDots(hiddenPasswordField.getText()));
//            passwordField.setVisible(true);
//            visiblePasswordField.setVisible(false);
//        } else {
//            visiblePasswordField.setText(hiddenPasswordField.getText());
//            visiblePasswordField.setVisible(true);
//            visiblePasswordField.requestFocus();
//            passwordField.setVisible(false);
//            hiddenPasswordField.setVisible(false);
//        }
//    }

    private String getDots(String text) {
        return "•".repeat(text.length());
    }

    public void signup(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showSignUpScene();
    }

    public void signin(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("auth")
                .whereEqualTo("username", usernameField.getText())
                .get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        String storedHashedPassword = documents.get(0).getString("hashPassword");
        assert storedHashedPassword != null;
        System.out.println(BCrypt.checkpw(passwordField.getText(), storedHashedPassword));


        //SceneManager.getInstance().showMainScene();
    }


}
