package br.com.yazz.Controllers;

import br.com.yazz.Class.Chamado;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class AdChamadosController implements Initializable {

    @FXML private AnchorPane pane_Base;
    @FXML private TextField txtParametro;
    @FXML private JFXButton butBuscar;
    @FXML private JFXButton butDetalhes;
    @FXML private TableView<Chamado> Tabela;
    @FXML private TableColumn<Chamado, Integer> colunaID;
    @FXML private TableColumn<Chamado, String> colunaTitulo;
    @FXML private TableColumn<Chamado, String> colunaDescricao;
    @FXML private TableColumn<Chamado, String> colunaProjeto;
    @FXML private TableColumn<Chamado, String> colunaTipo;
    @FXML private TableColumn<Chamado, String> colunaStatus;
    @FXML private TableColumn<Chamado, String> colunaAbertura;
    @FXML private TableColumn<Chamado, String> colunaEncerramento;
    @FXML private TableColumn<Chamado, Integer> colunaFK_ID;
    @FXML private ChoiceBox cbEscolha;
    @FXML private JFXButton butAtualizar;
    
    private String Chave = "TbChamados", Sql = "select * from tb_chamados order by DT_ABERTURA desc";
    private Chamado cham = new Chamado();
    private ObservableList oblEscolha = FXCollections.observableArrayList("ID", "Titulo");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbEscolha.setItems(oblEscolha);
        cbEscolha.setValue("ID");
        iniTabela();
    }

    public void iniTabela(){
        new Thread(){
            public void run(){
                Platform.runLater(() -> {
                     //Define em qual coluna sera exibidada cada informação.
              try{
                colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
                colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
                colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
                colunaProjeto.setCellValueFactory(new PropertyValueFactory<>("Projeto"));
                colunaTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
                colunaStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
                colunaAbertura.setCellValueFactory(new PropertyValueFactory<>("Abertura"));
                colunaEncerramento.setCellValueFactory(new PropertyValueFactory<>("Encerramento"));               
                colunaFK_ID.setCellValueFactory(new PropertyValueFactory<>("Fk_Id_Cli"));

                //Define os itens da tabela, usando um metodo para receber um ObservableList.
                Tabela.setItems(atualizaTabela());

              }
              catch(Exception e){
                  e.printStackTrace();
              }
                });
            }
        }.start();
    }
    
    private ObservableList<Chamado> atualizaTabela() {
        cham.Limpar();
        cham.Pesquisar(Chave, Sql);       
    return FXCollections.observableArrayList(cham.getArrayChamados());
    }

    @FXML
    private void Pesquisar(ActionEvent event) {
        new Thread(){
          public void run(){
              Platform.runLater(() -> {                  
                     if(cbEscolha.getValue().equals("ID"))
                         Sql = "select * from tb_chamados where id_chamado = " + txtParametro.getText();                         
                    else
                         Sql = "select * from tb_chamados where Titulo like '%" + txtParametro.getText() + "%'";
                        iniTabela();
              });
          }
      }.start();
    }

    @FXML
    private void Detalhes(ActionEvent event) {
        if(Tabela.getSelectionModel().getSelectedItem().getID() != null){
        try {
            AdChamadosadmController.setIDCham(Tabela.getSelectionModel().getSelectedItem().getID());
            
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/AdChamadosadm.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);          
              
           } catch (IOException ex) {
               ex.printStackTrace();
           }  
     }
    }

    @FXML
    private void Atualizar(ActionEvent event) {
        Sql = "select * from tb_chamados order by DT_ABERTURA desc";
        iniTabela();
    }
    
}
