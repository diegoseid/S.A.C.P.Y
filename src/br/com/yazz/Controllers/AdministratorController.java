package br.com.yazz.Controllers;

import static br.com.yazz.Util.Buttons.FecharTela;
import static br.com.yazz.Util.Buttons.Minimizar;
import static br.com.yazz.Util.Buttons.MudarCor;
import static br.com.yazz.Util.Moveble.MovePane;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class AdministratorController implements Initializable {

    @FXML private JFXButton butRelatorios;
    @FXML private JFXButton butEncerrar;
    @FXML private JFXButton butProjetos;
    @FXML private JFXButton butChamados;
    @FXML private JFXButton butClientes;
    @FXML private Pane pane_Base;
    @FXML private JFXButton butMinimizar;
    @FXML private JFXButton butFechar;
    @FXML private Label lblHora;
    @FXML private Label lblData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Relogio   
       new Thread(){
           @Override
           public void run(){
               while(true){      
                   Platform.runLater(() -> {
                       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                       lblHora.setText(LocalTime.now().format(dtf));
                   });                         
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(LoginPController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                } 
           }
       }.start();
       
       lblData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
        
       MudarCor(butChamados);
       MudarCor(butProjetos); 
       MudarCor(butClientes);
       MudarCor(butRelatorios);
       MudarCor(butEncerrar);
       
       //Fechar a tela pelo Botão x
       butFechar.setOnMouseClicked((MouseEvent event) -> {
           System.exit(0);
       });
       //Minimizar a tela pelo Botão _
       butMinimizar.setOnMouseClicked ((MouseEvent event) -> {
            Minimizar(butMinimizar);
    });
       
       butEncerrar.setOnAction((ActionEvent event) ->{
         //Fechar a Tela
        FecharTela(butEncerrar);
        //Abrir Tela Login         
           try {
              Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/LoginP.fxml"));
                Stage stage = new Stage();
                   MovePane(root,stage);
           } catch (IOException ex) {
               System.out.println("Não foi possivel abrir a tela de Login");
                ex.printStackTrace();
           }
        });
       
       //Chama a tela que mostra a lista de Clientes cadastrados
       butClientes.setOnMouseClicked((event) -> {           
       try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/AdClientes.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               //System.out.println(ex);
               ex.printStackTrace();
           }
       });
       
       //Chama a tela que mostra alguns relarorios em forma grafica
       butRelatorios.setOnMouseClicked((event) -> {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/AdRelatorios.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }  
       });
       
       //Chama a tela que mosta os Projetos solicitados.
       butProjetos.setOnMouseClicked((event) -> {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/AdProjetos.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }  
       });
       
       //Chama a tela que mosta os Projetos solicitados.
       butChamados.setOnMouseClicked((event) -> {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/AdChamados.fxml"));
                pane_Base.getChildren().clear();
                pane_Base.getChildren().add(root);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
       });
        
    }    
    
}
