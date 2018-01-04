package br.com.yazz.Controllers;

import com.jfoenix.controls.JFXButton;
import br.com.yazz.Class.Usuario;
import br.com.yazz.DAO.ConexaoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import static br.com.yazz.Util.Buttons.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import br.com.yazz.Util.TextFieldFormatter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


/**
 * FXML Controller class
 *
 * @author Diego Seid
 */

public class CadastroController implements Initializable {

    @FXML private JFXButton butMinimizar;
    @FXML private JFXButton butFechar;
    @FXML private Label lblAviso;
    @FXML private TextField txtNome;
    @FXML private ChoiceBox cbSelect;
    @FXML private TextField txtCPF_CNPJ;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtRamo;
    @FXML private TextField txtRazaoSocial;
    @FXML private ChoiceBox cbPorte;
    @FXML private TextField txtUser;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblUsernameError;
    @FXML private JFXButton butCadastrar;
    @FXML private JFXButton butCancelar;
    
    private String txtCampo, Chave = "ValidaCad";     
    public static int Res;
    private Integer COK = 0;
    private Usuario usu = new Usuario();
    private TextFieldFormatter tff = new TextFieldFormatter();
    
    ObservableList<String> cbListPorte = FXCollections.observableArrayList("Pequeno", "Médio", "Grande");
    ObservableList<String> cbListSelect = FXCollections.observableArrayList("CPF", "CNPJ");

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      //ComboList Porte       
      cbPorte.setItems(cbListPorte);
      cbPorte.setValue("Pequeno");
      //ComboList Cpf / Cnpj
      cbSelect.setItems(cbListSelect);
      cbSelect.setValue("CPF");
      
       butMinimizar.setOnMouseClicked((event) -> {
          Minimizar(butMinimizar);
      });
       
       butFechar.setOnMouseClicked((event) -> {
          FecharTela(butFechar);
      });
      
      butCancelar.setOnMouseClicked((event) -> {
          FecharTela(butCancelar);
      });
      
      butCadastrar.setOnMouseClicked((event) -> {
          COK = 0;
          lblAviso.setVisible(false);
          
             Validação(txtNome);
             Validação(txtUser);
             Validação(txtEmail);
             Validação(txtEndereco);
             Validação(txtRazaoSocial);
             Validação(txtRamo);
             Validação(txtSenha);
             Validação(txtTelefone);
             
             usu.setPorte(cbPorte.getValue().toString());
             
                if(cbSelect.getValue().toString().equals("CPF")){
                   if(Nulo(txtCPF_CNPJ))
                        usu.setCpf(txtCPF_CNPJ.getText());
                    else
                       txtCPF_CNPJ.setStyle("-fx-border-color: red");}
                else{
                     if(Nulo(txtCPF_CNPJ))
                    usu.setCnpj(txtCPF_CNPJ.getText());
                     else 
                         txtCPF_CNPJ.setStyle("-fx-border-color: red");
                         }
            
                if(COK == 9){
                    if(ValUser()){
                        //Limpa Todos os Campos
                    txtNome.clear();
                    txtCPF_CNPJ.clear();
                    txtEmail.clear();
                    txtTelefone.clear();
                    txtEndereco.clear();
                    txtRamo.clear();
                    txtRazaoSocial.clear();
                    cbPorte.setValue("Pequeno");
                    txtUser.clear();
                    txtSenha.clear();
               //Gera um alert popup
                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Usuario Cadastrado");
                    alert.setHeaderText("Usuario Cadastrado Com Sucesso!");
                        //Fecha a janela de Cadastro Após clicar em OK 
                        Optional<ButtonType> result = alert.showAndWait();
                     if (result.get() == ButtonType.OK){
                         //Inseri as informações no banco
                        usu.Inserir();
                        FecharTela(butCancelar);}                     
                    }
                 else{
                    lblUsernameError.setVisible(true);
                    txtUser.setStyle("-fx-border-color: red");
                    txtUser.requestFocus();
                }
                }                
         }); 
    
    }
    //testa se o campo é diferente de nulo caso seja retorna true
    private boolean Nulo(TextField Campo) {
        TextField txtCampo = Campo;
        Campo.setStyle("-fx-border-color: null");
     if(!"".equals(Campo.getText())) {
         COK += 1;
         return true;
        } else {
         COK = 0;
         return false;
        }
    }
    
    private void Validação(TextField Campo){
        txtCampo = Campo.getId();
  
      if(Nulo(Campo)){
        switch(txtCampo){
            case "txtNome":
                usu.setNome(Campo.getText());
                    break;
            case "txtUser":
                usu.setUsuario(Campo.getText());
                    break;
            case "txtEmail":
                usu.setEmail(Campo.getText());
                    break;
            case "txtEndereco":
                usu.setEndereco(Campo.getText());
                    break;
            case "txtRazaoSocial":
                usu.setRazaoSocial(Campo.getText());
                    break;
            case "txtRamo":
                usu.setRamo(Campo.getText());
                    break;
            case "txtSenha":
                usu.setSenha(Campo.getText());
                    break;
            case "txtTelefone":
                usu.setTelefone(Campo.getText());
                    break;
            default:
                break;
                     }}
      else{
          Campo.setStyle("-fx-border-color: red");
            lblAviso.setVisible(true);}
    }
    //Formata o campo Celular
    @FXML
    private void FormatarCelular(KeyEvent event) {
        tff.setMask("(##)#####-####");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtTelefone);
        tff.formatter();
    }
    //Formata o campo CPF_CNPJ
    @FXML
    private void FormatarCPF_CNPJ(KeyEvent event) {
        if(cbSelect.getValue().equals("CPF"))
        tff.setMask("###.###.###-##");
        else
            tff.setMask("##.###.###/####-##");
        tff.setCaracteresValidos("0123456789");
        tff.setTf(txtCPF_CNPJ);
        tff.formatter();
    }

    
    //valida se o usuario ja existe no banco
    private boolean ValUser() {
            ConexaoBD con = new ConexaoBD();
            String sql = "select count(id_usuario) from tb_usuario_cadastro where usuario = '" + txtUser.getText() + "'";
             con.pesquisar(Chave, sql);
         return getRes();
    }
    
    private boolean getRes(){
        if (Res == 0) 
            return true;
        else
            return false;
    }
    
    public static void Result(int Resul){
        Res = Resul;
    }
    
}
