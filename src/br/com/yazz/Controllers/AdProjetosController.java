package br.com.yazz.Controllers;

import br.com.yazz.Class.Projeto;
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
public class AdProjetosController implements Initializable {

    @FXML private AnchorPane pane_Base;
    @FXML private TextField txtParametro;
    @FXML private JFXButton butBuscar;
    @FXML private JFXButton butDetalhes;
    @FXML private JFXButton butAtualizar;
    @FXML private TableView<Projeto> Tabela;
    @FXML private TableColumn<Projeto, Integer> colunaID;
    @FXML private TableColumn<Projeto, String> colunaTitulo;
    @FXML private TableColumn<Projeto, String> colunaDescricao;
    @FXML private TableColumn<Projeto, Integer> colunaqtdeAcessos;
    @FXML private TableColumn<Projeto, String> colunaPrioridade;
    @FXML private TableColumn<Projeto, String> colunaStatus;
    @FXML private TableColumn<Projeto, String> colunaAbertura;
    @FXML private TableColumn<Projeto, String> colunaEncerramento;
    @FXML private TableColumn<Projeto, Float> colunaCusto;
    @FXML private TableColumn<Projeto, String> colunaLinguagens;
    @FXML private TableColumn<Projeto, Integer> colunaFK_ID;
    @FXML private ChoiceBox cbEscolha;
    
    private ObservableList<String> oblEscolha = FXCollections.observableArrayList("ID", "Titulo");
    private String Chave = "TbProjeto", sql;
    private Projeto pjt = new Projeto();  
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbEscolha.setItems(oblEscolha);
        cbEscolha.setValue("ID");
        
        //Chama o metodo para Iniciar a Tabela.
        iniTabela();
        
    }    

    private void iniTabela() {
        new Thread(){
        public void run(){
            Platform.runLater(() -> {
              //Define em qual coluna sera exibidada cada informação.
      try{
        colunaID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("NomeProj"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        colunaqtdeAcessos.setCellValueFactory(new PropertyValueFactory<>("QTDEAcessos"));
        colunaPrioridade.setCellValueFactory(new PropertyValueFactory<>("Prioridade"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colunaAbertura.setCellValueFactory(new PropertyValueFactory<>("Abertura"));
        colunaEncerramento.setCellValueFactory(new PropertyValueFactory<>("Encerramento"));
        colunaCusto.setCellValueFactory(new PropertyValueFactory<>("Custo"));
        colunaLinguagens.setCellValueFactory(new PropertyValueFactory<>("Linguagens"));
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

    private ObservableList<Projeto> atualizaTabela() {
        pjt.Limpar();
        pjt.Pesquisar(Chave);       
    return FXCollections.observableArrayList(pjt.getArrayProjeto());
    }
    
    @FXML
    private void Detalhes(ActionEvent event) {
     if(Tabela.getSelectionModel().getSelectedItem().getId() != null){
        try {
            AdProjetosadmController.setIDproj(Tabela.getSelectionModel().getSelectedItem().getId());
            
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/AdProjetosadm.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);          
              
           } catch (IOException ex) {
               ex.printStackTrace();
           }  
     }
    }
    
    @FXML
    private void Pesquisar(ActionEvent event) {
      new Thread(){
          public void run(){
              Platform.runLater(() -> {
                  Chave = "Filtro";
                     if(cbEscolha.getValue().equals("ID")){
                         pjt.setFiltro("ID");
                         pjt.setId(Integer.parseInt(txtParametro.getText()));}
                    else{
                         pjt.setFiltro("Titulo");            
                         pjt.setNomeProj(txtParametro.getText());}
                        iniTabela();
              });
          }
      }.start();
        
    }

    @FXML
    private void Atualizar(ActionEvent event) {
        Chave = "TbProjeto";
          iniTabela();
    }
   
    
}
