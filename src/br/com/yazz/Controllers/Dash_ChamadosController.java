package br.com.yazz.Controllers;

import br.com.yazz.Class.Chamado;
import br.com.yazz.Class.Projeto;
import static br.com.yazz.Class.Usuario.getID;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class Dash_ChamadosController implements Initializable {   
    
    @FXML private TextField txtTitulo;
    @FXML private TextArea txtDescricao;
    @FXML private ChoiceBox cbProjeto;
    @FXML private JFXButton butEnviar;
    @FXML private JFXButton butCancelar;
    @FXML private AnchorPane pane_Base;
    @FXML private ChoiceBox cbCategoria;
    
    private String Chave = "Projeto";
    private Chamado cham = new Chamado();
    private ArrayList<String> array = new ArrayList<String>();    
    private final ObservableList<String> oblCategoria = FXCollections.observableArrayList("Solicitações", "Duvidas", "Reclamações", "Erros"); 


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Chama a funçãp pesquisar quer traz os nomes dos projetos
        Projeto proj = new Projeto();
         proj.Pesquisar(Chave);
        //Função retorno um array, array esse preenchido na pesquisa 
         array = proj.Retorno(); 
         
            cbCategoria.setItems(oblCategoria);
            cbCategoria.setValue("Duvida");
          ObservableList<String> oblProjeto = FXCollections.observableArrayList(array);
        cbProjeto.setItems(oblProjeto);
        
        //Codigo para inserir o chamado no banco
        butEnviar.setOnMouseClicked((event) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Alert al = new Alert(Alert.AlertType.WARNING);
            
        if(!"".equals(txtTitulo.getText()) && !"".equals(txtDescricao.getText()) && cbProjeto.getValue() != null && cbCategoria.getValue() != null){
           String sql = "insert into tb_chamados values(seqchamados.nextval, '" + cbProjeto.getValue().toString() +  "'," + getID() +  ", 'Aberto', '" + cbCategoria.getValue().toString() + 
                   "', '" + txtTitulo.getText() + "', '" + txtDescricao.getText() + "', "
                   + "sysdate, null)";
              cham.Inserir(sql);
            sql = "update tb_projetos set N_PxC = 1 where nM_projeto = '" + cbProjeto.getValue()+ "'";
              cham.Inserir(sql);
             //Gera um alert popup 
                alert.setTitle("Abertura De Chamado");
                alert.setHeaderText("Chamado aberto com sucesso !");
                alert.setContentText("O seu chamado sera respondido o quanto antes possivel," +  
                        "voce pode verificar o status do chamado clicando na aba de Status");
                //Fecha a janela de Cadastro Após clicar em OK 
                        Optional<ButtonType> result = alert.showAndWait();
                     if (result.get() == ButtonType.OK){
                         //Inseri as informações no banco
                        cbCategoria.setValue(null);
                        cbProjeto.setValue(null);
                        txtTitulo.clear();
                        txtDescricao.clear();
                     } }
        else{
               al.setTitle("Abertura De Chamado");
               al.setHeaderText("Erro! Favor Preeencher Todos Os Campos!");
               al.show();
           } 
        });
       
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
    }    

    
}
