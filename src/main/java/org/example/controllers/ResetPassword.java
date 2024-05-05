package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.services.ServiceUser;

import static org.example.models.User.isValidEmail;


//import java.net.PasswordAuthentication;
//import javax.mail.Authenticator;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class ResetPassword {

    @FXML
    private TextField EmailField;

    @FXML
    private Label Email;

    @FXML
    private Button SendCodeBtn;

    @FXML
    private TextField VerifyCodeField;

    @FXML
    private Label VerifyCode;

    @FXML
    private Button VerifyBtn;

    @FXML
    private TextField NewPasswordField;

    @FXML
    private Label NewPassword;

    @FXML
    private Button ConfirmBtn;




    @FXML
    void initialize(){
        EmailField.setVisible(true);
        Email.setVisible(true);
        SendCodeBtn.setVisible(true);
        VerifyCodeField.setVisible(false);
        VerifyCode.setVisible(false);
        VerifyBtn.setVisible(false);
        NewPasswordField.setVisible(false);
        NewPassword.setVisible(false);
        ConfirmBtn.setVisible(false);

    }

    @FXML
    void SendCode(ActionEvent event){

        String userMail = EmailField.getText();

        if(!isValidEmail(userMail)){
            showAlert("invalid Email");
            return;
        }

        sendCodeEmail(userMail);
        EmailField.setVisible(false);
        Email.setVisible(true);
        SendCodeBtn.setVisible(false);
        VerifyCodeField.setVisible(true);
        VerifyCode.setVisible(true);
        VerifyBtn.setVisible(true);
        NewPasswordField.setVisible(false);
        NewPassword.setVisible(false);
        ConfirmBtn.setVisible(false);



    }

    @FXML
    void VerifyCode(ActionEvent event){


        String code = VerifyCodeField.getText();
        String userMail = getEmailForVerifyCode(code);

        if(!isValidCode(userMail,code)){
            showAlert("invalid code");
            return;
        }
        EmailField.setVisible(false);
        Email.setVisible(true);
        SendCodeBtn.setVisible(false);
        VerifyCodeField.setVisible(false);
        VerifyCode.setVisible(false);
        VerifyBtn.setVisible(false);
        NewPasswordField.setVisible(true);
        NewPassword.setVisible(true);
        ConfirmBtn.setVisible(true);

    }

    @FXML
    void confirmPassword(ActionEvent event){

        String newPassword = NewPasswordField.getText();
        String email = getEmailForVerifyCode(VerifyCodeField.getText());

        if (!isValidCode(email, VerifyCodeField.getText())) {
            showAlert("Invalid verification code");
            return;
        }

        updatePassword(email,newPassword);

        initialize();

        showAlert("Password Updated Successfuly");
    }



    private Map<String, String> resetCodes = new HashMap<>();

    void sendCodeEmail(String userMail){

        String code = generateRandomCode();
        resetCodes.put(userMail,code);

        // Paramètres de configuration pour Gmail
        String mailHost = "smtp.gmail.com";
        int mailPort = 587;
        String mailUsername = "benkhalifamalek29@gmail.com";
        String mailPassword = "icju gwzm unuz udks";

        // Configurer les propriétés du serveur de messagerie
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.port", mailPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Configurer l'authentification
        Authenticator authenticator = new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUsername, mailPassword);
            }
        };

        // Obtenir la session
        Session session = Session.getInstance(properties, authenticator);
        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userMail));
            message.setSubject("Password Reset");
            message.setText("Click the link below to reset your password:\n"+ code);

            // Send the message
            Transport.send(message);

            showAlert("Password reset email sent to " + userMail);
        } catch (Exception e) {
            showAlert("Failed to send password reset email");
            e.printStackTrace();
        }


    }

    private String getEmailForVerifyCode(String verifyCode){
        for (Map.Entry<String, String> entry : resetCodes.entrySet()) {
            if (entry.getValue().equals(verifyCode)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void updatePassword(String email, String newPassword) {
        // Instancier votre service UserService
        ServiceUser userService = new ServiceUser();

        // Appeler la méthode de mise à jour du mot de passe dans votre service
        userService.updatePassword(email, newPassword);
    }

    private boolean isValidCode(String userMail,String code) {
        return resetCodes.containsKey(userMail) && resetCodes.get(userMail).equals(code);
    }


    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private String generateRandomCode() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}
