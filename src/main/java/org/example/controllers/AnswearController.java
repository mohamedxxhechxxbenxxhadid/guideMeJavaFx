package org.example.controllers;

import jakarta.mail.MessagingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

    public Stage stage ;

    @FXML
    public void sendMail(ActionEvent event) {
        System.out.println("senMail");
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
        System.out.println("i m in the middle of send mail");
        System.out.println(objectB && messageB);
        if(objectB== true && messageB == true){
            System.out.println("mail sent 50%");
            try {
                System.out.println("mail sent 50%");
                EmailSender.sendEmail(reclamation.getEmail(),messageId.getText(),objectId.getText());
                System.out.println("mail sent");
                this.rC.remove(reclamation,rC);
                this.rIC.DeleteItem(event);
                stage.close();

            }catch (MessagingException e){
                System.out.println(e.getMessage());
            }

        }
    }
}
