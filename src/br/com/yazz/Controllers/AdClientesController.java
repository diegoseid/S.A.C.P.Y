package br.com.yazz.Controllers;

import br.com.yazz.Class.ListaClientes;
import static br.com.yazz.Controllers.AdProjetosadmController.ID;
import br.com.yazz.DAO.ConexaoBD;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class AdClientesController implements Initializable {

    @FXML private JFXButton butBuscar;
    @FXML private JFXButton butAtualizar;
    @FXML private JFXButton butRemover;
    @FXML private JFXButton butEditar;
    @FXML private TextField txtParametro;
    @FXML private TableView<ListaClientes> Tabela;
    @FXML private TableColumn<ListaClientes, Integer> colunaID;
    @FXML private TableColumn<ListaClientes, String> colunaNome;
    @FXML private TableColumn<ListaClientes, String> colunaCPF;
    @FXML private TableColumn<ListaClientes, String> colunaEmail;
    @FXML private TableColumn<ListaClientes, String> colunaEndereco;
    @FXML private TableColumn<ListaClientes, String> colunaRazaoSocial;
    @FXML private TableColumn<ListaClientes, String> colunaUsuario;
    @FXML private TableColumn<ListaClientes, String> colunaSenha;
    @FXML private TableColumn<ListaClientes, String> colunaPorte;
    @FXML private TableColumn<ListaClientes, String> colunaRamo;
    @FXML private TableColumn<ListaClientes, String> colunaTelefone;
    @FXML private TableColumn<ListaClientes, String> colunaCnpj;
    
    private String Procedure, Chave = "Tabela", sql = "select * from tb_usuario_cadastro order by id_usuario", Table = "TB_USUARIO_CADASTRO";
    private ListaClientes lc = new ListaClientes();
    private ConexaoBD con = new ConexaoBD();
    static Connection conexao = null;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Chama o metodo para Iniciar a Tabela.
        iniTabela();
        //Chama o metodo para permitir editar campos da Tabela.
        editarTabela();
    }    
    
    //metodo para Iniciar a Tabela
    public void iniTabela(){
    new Thread(){
        public void run(){
            Platform.runLater(() -> {
              //Define em qual coluna sera exibidada cada informação.
      try{
        colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        colunaCPF.setCellValueFactory(new PropertyValueFactory<>("Cpf"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("Endereço"));
        colunaRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("Razao"));
        colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("Usuario"));
        colunaSenha.setCellValueFactory(new PropertyValueFactory<>("Senha"));
        colunaPorte.setCellValueFactory(new PropertyValueFactory<>("Porte"));
        colunaRamo.setCellValueFactory(new PropertyValueFactory<>("Ramo"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        colunaCnpj.setCellValueFactory(new PropertyValueFactory<>("Cnpj"));
        
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
    
    //Metodo resposavel por realizar a consulta no banco, retorna um ObservableList.
    public ObservableList<ListaClientes> atualizaTabela(){
        lc.Limpar();
        lc.pesquisar(Chave, sql);       
    return FXCollections.observableArrayList(lc.getArrayTabela());
    }
   
    //Metodo responsavel por permitir que as colunas da tebela possam ser editadas.
    public void editarTabela(){
    new Thread(){
        public void run(){
            Platform.runLater(() -> {
        //Coluna Nome
    colunaNome.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaNome.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setNome(s.getNewValue());
            });
        //Coluna Email
    colunaEmail.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaEmail.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setEmail(s.getNewValue());
            });
        //Coluna CPF
    colunaCPF.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaCPF.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setCpf(s.getNewValue());
            });
        //Coluna Endereço
    colunaEndereco.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaEndereco.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setEndereço(s.getNewValue());
            });
        //Coluna Razao Social
    colunaRazaoSocial.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaRazaoSocial.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setRazao(s.getNewValue());
            });
        //Coluna Senha
    colunaSenha.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaSenha.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setSenha(s.getNewValue());
            });
        //Coluna Porte
    colunaPorte.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaPorte.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setPorte(s.getNewValue());
            });
        //Coluna Ramo
    colunaRamo.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaRamo.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setRamo(s.getNewValue());
            });
        //Coluna Telefone
    colunaTelefone.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaTelefone.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setTelefone(s.getNewValue());
            });
        //Coluna Cnpj
    colunaCnpj.setCellFactory(TextFieldTableCell.forTableColumn());    
        colunaCnpj.setOnEditCommit((CellEditEvent<ListaClientes, String> s) -> {
            ((ListaClientes) s.getTableView().getItems().get(
                    s.getTablePosition().getRow())
                    ).setCnpj(s.getNewValue());
            });
                                
            });
        }
    }.start();
    }
    
    //Metodo responsavel para dar um Refresh na Tabela, Acionado pelo Botão Atualizar.
    @FXML
    private void Atualizar() {
        sql = "select * from tb_usuario_cadastro order by id_usuario";
        Tabela.setItems(atualizaTabela());      
    }
    
    //Metodo responsavel para deletar um usuario no banco, após Delete ele chama o Metodo Atualizar, Acionado pelo Botão Remover.
    @FXML
    private void Remover() {
        Procedure = "{call sp_usuario_delete (?)}";
                     con.call(Tabela.getSelectionModel().getSelectedItem().getID(), Procedure);
        Atualizar();
    }
    
    @FXML
    private void Alterar(){
               try {
            String sql = "update tb_usuario_cadastro set NM_USUARIO = ?, Email = ?, NM_Endereco = ?, NM_RAZAO_SOCIAL = ?, Senha = ?, Porte = ?, NM_Ramo = ?, NR_Telefone = ?, NR_Cpf = ?, NR_Cnpj = ? where ID_USUARIO = ?";
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            //conexao = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Oracleuni9");
            PreparedStatement atualizacao = null;
            atualizacao = conexao.prepareStatement(sql);
            atualizacao.setString(1,Tabela.getSelectionModel().getSelectedItem().getNome());
            atualizacao.setString(2,Tabela.getSelectionModel().getSelectedItem().getEmail());
            atualizacao.setString(3,Tabela.getSelectionModel().getSelectedItem().getEndereço());
            atualizacao.setString(4,Tabela.getSelectionModel().getSelectedItem().getRazao());
            atualizacao.setString(5,Tabela.getSelectionModel().getSelectedItem().getSenha());
            atualizacao.setString(6,Tabela.getSelectionModel().getSelectedItem().getPorte());
            atualizacao.setString(7,Tabela.getSelectionModel().getSelectedItem().getRamo());
            atualizacao.setString(8,Tabela.getSelectionModel().getSelectedItem().getTelefone());
            atualizacao.setString(9,Tabela.getSelectionModel().getSelectedItem().getCpf());
            atualizacao.setString(10,Tabela.getSelectionModel().getSelectedItem().getCnpj());
            atualizacao.setInt(11,Tabela.getSelectionModel().getSelectedItem().getID());
            atualizacao.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Clientes");
                alert.setHeaderText("Dados Do Cliente Editado Com Sucesso!");
               alert.show();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha ao alterar dados na tabela!");
        }
    }
    
    @FXML
    public void Pesquisar(){
        sql = "select * from tb_usuario_cadastro where nm_usuario like '%" + txtParametro.getText() + "%'";        
        iniTabela();        
    }
    
   }
