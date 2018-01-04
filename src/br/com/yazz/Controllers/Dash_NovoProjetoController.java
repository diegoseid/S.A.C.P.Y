package br.com.yazz.Controllers;

import br.com.yazz.Class.Projeto;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class Dash_NovoProjetoController implements Initializable {

    @FXML private TextArea txtDescricao;
    @FXML private JFXButton butFechar;
    @FXML private JFXButton butEnviar;
    @FXML private TextField txtNomeProj;
    @FXML private TextField txtqtdeAcessos;
    @FXML private ChoiceBox txtPrioridade;
    @FXML private AnchorPane pane_Base;
    
    private Projeto projeto = new Projeto();
    private ObservableList<String> oblPrioridade = FXCollections.observableArrayList("Baixa", "Média", "Alta", "Urgente");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
       txtPrioridade.setItems(oblPrioridade);
       txtPrioridade.setValue("Baixa");
       
       butEnviar.setOnMouseClicked((event) -> { 
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           Alert al = new Alert(Alert.AlertType.WARNING);
           
           if(!"".equals(txtNomeProj.getText()) && !"".equals(txtqtdeAcessos.getText()) && !"".equals(txtDescricao.getText())){  
               //Gera um alert popup 
                alert.setTitle("Abertura De Projetos");
                alert.setHeaderText("Projeto solicitado com sucesso !");
                alert.setContentText("O seu projeto sera desenvolvido o quanto antes," +  
                        "voce pode acompanhar o status do seu projeto Clicando na aba de projetos");
                //Fecha a janela de Cadastro Após clicar em OK 
                        Optional<ButtonType> result = alert.showAndWait();
                     if (result.get() == ButtonType.OK){
                         //Inseri as informações no banco
                         projeto.setNomeProj(txtNomeProj.getText());
                         projeto.setDescricao(txtDescricao.getText());
                         projeto.setQTDEAcessos(Integer.parseInt(txtqtdeAcessos.getText()));
                         projeto.setPrioridade(txtPrioridade.getValue().toString());
                            projeto.Incluir();
                        txtNomeProj.clear();
                        txtDescricao.clear();
                        txtqtdeAcessos.clear();
                     } }
           else{
               al.setTitle("Abertura De Projetos");
               al.setHeaderText("Erro! Favor Preeencher Todos Os Campos!");
               al.show();
           } 
       });
       
        butFechar.setOnMouseClicked((event) -> {
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
       
    }    
    
}
