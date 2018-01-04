package br.com.yazz.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import static br.com.yazz.Util.Buttons.*;
import static br.com.yazz.Util.Moveble.MovePane;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class DashBoardController implements Initializable {

    @FXML private Pane pane;
    @FXML private JFXButton butPerfil;
    @FXML private JFXButton butEncerrar;
    @FXML private JFXButton butEstatus;
    @FXML private JFXButton butProjetos;
    @FXML private JFXButton butChamados;
    @FXML private Pane pane_Base;
    @FXML private JFXButton butMinimizar;
    @FXML private JFXButton butFechar;
    @FXML private Label lblHora;
    @FXML private Label lblData;
    @FXML private Label lblProjeto;
    @FXML private Label lblEstatus;
    @FXML private Label lblCusto;
    @FXML private Label lblDtSolicitacao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
       
       MudarCor(butChamados);
       MudarCor(butPerfil);
       MudarCor(butEstatus);
       MudarCor(butProjetos);  
       MudarCor(butEncerrar);
        
       //Chama a tela inicio
       try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/Dash_Inicio.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               System.out.println(ex);
               ex.printStackTrace();
           }
       
       //Fechar a tela pelo Botão x
       butFechar.setOnMouseClicked((MouseEvent event) -> {
           System.exit(0);
       });
       //Minimizar a tela pelo Botão _
       butMinimizar.setOnMouseClicked ((MouseEvent event) -> {
            Minimizar(butMinimizar);
        });
       
       butPerfil.setOnMouseClicked((event) -> {
           try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/Dash_Perfil.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }
         });
       
       butEstatus.setOnMouseClicked((event) -> {
           try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/Dash_Status.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }
         });
       
       butProjetos.setOnMouseClicked((event) -> {
           try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/Dash_Projetos.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       });
       
       butChamados.setOnMouseClicked((event) -> {
           try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/Dash_Chamados.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       });

        
        
        butEncerrar.setOnAction((ActionEvent event) ->{
         //Fechar a Tela
        FecharTela(butEncerrar);
         TelaLogin();
        });

    }       
    
    public void TelaLogin(){
        //Abrir Tela Login         
           try {
              Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/LoginP.fxml"));
                Stage stage = new Stage();
                   MovePane(root,stage);
           } catch (IOException ex) {
               System.out.println("Não foi possivel abrir a tela de Login");
                ex.printStackTrace();
           }
    }
  
}
