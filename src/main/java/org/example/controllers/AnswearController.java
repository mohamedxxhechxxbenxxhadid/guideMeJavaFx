package org.example.controllers;

import jakarta.mail.MessagingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.models.Reclamation;
import org.example.utils.EmailSender;

public class AnswearController {


    @FXML
    private Label descriptionLabelId;

    @FXML
    private TextArea messageId;

    @FXML
    private TextField objectId;

    @FXML
    private Label objectLabelId;

    @FXML
    private Button sendId;

    public ReclamationItemController rIC ;
    public ShowReclamationController rC ;
    public Reclamation reclamation ;
    boolean messageB ;
    boolean objectB ;

    @FXML
    public void sendMail(ActionEvent event) {
        if (messageId.getText().isBlank() ){
            descriptionLabelId.setVisible(true);
            messageId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank() ) {
                    descriptionLabelId.setVisible(false);
                    messageB = true ;
                }
            }));
        }
        if (objectId.getText().isBlank() ){
            objectLabelId.setVisible(true);
            objectId.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (!oldValue.isBlank() ) {
                   objectLabelId.setVisible(false);
                    objectB = true ;
                }
            }));
        }
        if(objectB== true && messageB == true){
            System.out.println("mail sent 50%");
            try {
                System.out.println("mail sent 50%");
                EmailSender.sendEmail(reclamation.getEmail(),messageId.getText(),objectId.getText());
                System.out.println("mail sent");
            }catch (MessagingException e){
                System.out.println(e.getMessage());
            }

        }
    }
}
