package br.com.yazz.Controllers;

import br.com.yazz.Class.Usuario;
import static br.com.yazz.Class.Usuario.getID;
import br.com.yazz.DAO.ConexaoBD;
import static br.com.yazz.Util.Buttons.FecharTela;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class Dash_PerfilController implements Initializable {

    @FXML private JFXButton butCancelar;
    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private TextField txtUser;
    @FXML private TextField txtTelefone;
    @FXML private PasswordField txtSenha;
    @FXML private TextField txtCPF_CNPJ;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtRazao;
    @FXML private TextField txtRamo;
    @FXML private TextField txtPorte;
    @FXML private AnchorPane pane_Base;
    @FXML private JFXButton butDelete;
    @FXML private JFXButton butAlterSenha;
    @FXML private JFXButton butSalvar;
    
    private int v = 0;
    private String sql, Chave = "Perfil", Tabela = "tb_usuario_cadastro", Procedure;
    private ArrayList<String> array = new ArrayList<>();
    private Usuario usu = new Usuario();;
    private ConexaoBD con = new ConexaoBD();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        butSalvar.setVisible(false);
        sql = "select * from tb_usuario_cadastro where id_usuario = '" + getID() + "'";
        
        //chama a funcao pesquisar, que realiza um select no banco
        usu.Pesquisar(Chave, sql);
        
        //Adiciona o Retorno do select nos Campos do Pane Perfil
        ArrayList<String> array = new ArrayList<>();
        array = usu.Retorno();
        
          //ForReach para varrer o arraylist e adicionar os valores nos campos do Perfil
            for(String i: array){
                switch(v){
                    case 0:
                        txtNome.setText(i);
                        break;
                    case 1:
                        txtUser.setText(i);
                        break;
                    case 2:
                        txtEmail.setText(i);
                        break;
                    case 3:
                        txtCPF_CNPJ.setText(i);
                        break;
                    case 4:
                        txtTelefone.setText(i);
                        break;
                    case 5:
                        txtRazao.setText(i);
                        break;
                    case 6:
                        txtEndereco.setText(i);
                        break;
                    case 7:
                        txtRamo.setText(i);
                        break;
                    case 8:
                        txtPorte.setText(i);
                        break;
                }
                v += 1;
            }
            
            butCancelar.setOnMouseClicked((event) -> {
           //Chama a tela inicio
       try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/Dash_Inicio.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               System.out.println(ex);
               ex.printStackTrace();
           }
        });
            
          butDelete.setOnMouseClicked((event) ->{
             //Alert pré confirmação  
              Alert Excluido = new Alert(Alert.AlertType.INFORMATION);
                Excluido.setTitle("Excluir Conta");
                Excluido.setHeaderText("Sua Conta Foi Encerrada, Obrigado por utilizar o Nosso Sistema !");
              //Alert prós confirmação 
              Alert Excluir = new Alert(Alert.AlertType.CONFIRMATION);
                Excluir.setTitle("Excluir Conta");
                Excluir.setHeaderText("Deseja realmente Excluir sua Conta ?");
                
                //Teste para verificar se a exclusao foi confirmada
                    Optional<ButtonType> result = Excluir.showAndWait();
                        if (result.get() == ButtonType.OK){
                             Procedure = "{call sp_usuario_delete (?)}";
                              con.call(Integer.parseInt(getID()), Procedure);
                                DashBoardController D = new DashBoardController();
                                    D.TelaLogin();
                                   Excluido.show();
                                FecharTela(butDelete); }                                         
          });
    }    

    @FXML
    private void Alterar(ActionEvent event) {
        txtSenha.setEditable(true);
        txtSenha.requestFocus();
        butSalvar.setVisible(true);
    }

    @FXML
    private void Salvar(ActionEvent event) {
        ConexaoBD.atualizar(txtSenha.getText(), getID());
        txtSenha.setEditable(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alterar Senha");
                alert.setHeaderText("Senha Atualizada com Sucesso!");
              alert.show();
        butSalvar.setVisible(false);
    }
    
}
