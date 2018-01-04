package br.com.yazz.Controllers;

import br.com.yazz.Class.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import static br.com.yazz.Util.Moveble.MovePane;
import static br.com.yazz.Util.Buttons.*;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;


/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class LoginPController implements Initializable {

    @FXML private TextField lblUsername;
    @FXML private PasswordField lblPassword;
    @FXML private JFXButton butLogin;
    @FXML private JFXButton butRegister;
    @FXML private JFXButton butMinimizar;
    @FXML private JFXButton butFechar;
    @FXML private CheckBox checkRSenha;
    
    private String Username, Password, Chave = "Login", sql;
    private Usuario usuario = new Usuario();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

      //verifica se o usuario é adm ou user
        butLogin.setOnAction((event) -> {
        //Thread
            new Thread(){
           @Override
           public void run(){
             Platform.runLater(() ->{
     try{    sql = "SELECT * FROM TB_USUARIO_CADASTRO WHERE USUARIO = '" + lblUsername.getText() + "'";
             usuario.Pesquisar(Chave, sql);
                Username = Usuario.getUsuario();
                Password = Usuario.getSenha();
     //adm   
    if(lblUsername.getText().equals("admin") && lblPassword.getText().equals("admin")){
               try{ FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/com/yazz/View/Administrator.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                MovePane(root, stage);
                FecharTela(butLogin);
                }
                catch(Exception e){
                    System.out.println("não foi possivel carregar a tela Administator");
                    e.printStackTrace();
                }
            }
    //User     
    else       
          if(lblUsername.getText().equals(Username) && lblPassword.getText().equals(Password)){             
                try{ FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/com/yazz/View/DashBoard.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                MovePane(root, stage);
                FecharTela(butLogin);
                }
                catch(Exception e){
                    System.out.println("não foi possivel carregar a tela de Dashboard");
                    e.printStackTrace();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login incorreto");
                alert.setHeaderText("Username ou Password invalido!");
                alert.show();
            }} //fim do try
     catch(Exception e){
         Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro no Banco");
                alert.setHeaderText("Usuario não cadastrado!" + e);
                alert.show();
     }
       finally{
         usuario.setUsuario("null");
         usuario.setSenha("null");
     }
             });} 
            }.start();});
        
          //fecha o sistema por completo
         butFechar.setOnAction((ActionEvent event) -> {
              System.exit(0);
        });
          
          //chama a tela de cadastro ao clica no botao re
          butRegister.setOnMouseClicked((event) -> {
               try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/com/yazz/View/Cadastro.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                MovePane(root, stage);
        }
        catch(IOException e){
            System.out.println("não foi possivel carregar a tela de cadastro");
            e.printStackTrace();
            
        }
          });
    }

}
