package br.com.yazz.Controllers;

import br.com.yazz.Class.Chamado;
import static br.com.yazz.Controllers.AdClientesController.conexao;
import static br.com.yazz.Controllers.AdProjetosadmController.consulta;
import br.com.yazz.DAO.ConexaoBD;
import static br.com.yazz.Util.Excessoes.TratamentoDeErro;
import br.com.yazz.Util.TextFieldFormatter;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class AdChamadosadmController implements Initializable {

    @FXML private AnchorPane pane_Base;
    @FXML private TextField txtCodCham;
    @FXML private ChoiceBox cbStatus;
    @FXML private TextField txtFk_Id_Cli;
    @FXML private TextField txtTipo;
    @FXML private Label lblAbertura;
    @FXML private Label lblEncerramento;
    @FXML private TextArea lblDescricao;
    @FXML private TextField txtChamado;
    @FXML private TextField txtProjeto;
    @FXML private JFXButton butVoltar;
    @FXML private JFXButton butExcluir;
    @FXML private JFXButton butSalvar;
    
    private static String Tabela = "tb_chamados", Chave = "TbChamados";
    private String Sql;
    public static Integer ID;
    private ObservableList<String> oblStatus = FXCollections.observableArrayList("Aberto", "Em Analise", "Encerrado");
    private Chamado cham = new Chamado();
    private ArrayList<Chamado> arraylist = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbStatus.setItems(oblStatus);
        
        txtCodCham.setText(ID.toString());
        Sql = "select * from tb_chamados where id_chamado = " + ID;
        cham.Pesquisar(Chave, Sql);
        arraylist = cham.getArrayChamados();
        
        txtFk_Id_Cli.setText(arraylist.get(0).getFk_Id_Cli().toString());
        cbStatus.setValue(arraylist.get(0).getStatus());
        txtTipo.setText(arraylist.get(0).getTipo());
        txtChamado.setText(arraylist.get(0).getTitulo());
        txtProjeto.setText(arraylist.get(0).getProjeto());
        lblDescricao.setText(arraylist.get(0).getDescricao());
        //Formata o Campo Data de Abertura 
        try {
             TextFieldFormatter.FormData(lblAbertura, arraylist.get(0).getAbertura());
        } catch (ParseException ex) {
             ex.printStackTrace();
            }
        if(arraylist.get(0).getEncerramento() != null){        
         //Formata o Campo Data de Encerramento
        try {
             TextFieldFormatter.FormData(lblEncerramento, arraylist.get(0).getEncerramento());
        } catch (ParseException ex) {
             ex.printStackTrace();
            }}

          
    }    

    @FXML
    private void Voltar(ActionEvent event) {
        //Chama a tela que mosta os Chamados solicitados.
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/AdChamados.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }  
    }

    @FXML
    private void Excluir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alterar Dados Do Chamado");
            alert.setHeaderText("Atenção, Deseja Realmente Excluir Este Chamado ?");

            ButtonType buttonTypeSim = new ButtonType("Sim");
            ButtonType buttonTypeNão = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNão);

            Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeSim){
                     ConexaoBD.apagar(Tabela, ID);
                     Voltar(event);
                }  
    }

    @FXML
    private void Salvar(ActionEvent event) {
        try {
            String sql = "update tb_Chamados set Status = ?, Titulo = ?, Descricao = ? where ID_CHAMADO = ?";
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            //conexao = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Oracleuni9");
            PreparedStatement atualizacao = null;
            atualizacao = conexao.prepareStatement(sql);
            atualizacao.setString(1, cbStatus.getValue().toString());
            atualizacao.setString(2, txtChamado.getText());
            atualizacao.setString(3, lblDescricao.getText());
            atualizacao.setInt(4, ID);
            atualizacao.executeUpdate();
                           
                if(cbStatus.getValue().equals("Encerrado")){
                    sql = "update tb_chamados set dt_entrega = sysdate where id_chamado = " + ID;
                    consulta = conexao.createStatement();
                    consulta.executeUpdate(sql);
                }
                else{        
                    sql = "update tb_chamados set dt_entrega = null where id_chamado = " + ID;
                    consulta = conexao.createStatement();
                    consulta.executeUpdate(sql);
                }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Chamado");
                alert.setHeaderText("Chamado Atualizado com Sucesso!");
               alert.show();
            conexao.close();

        } catch (SQLException e) {
            TratamentoDeErro(e);
            System.out.println("Falha ao alterar dados na tabela!");
        }
    }
    
    static void setIDCham(Integer id) {
        ID = id;
    }
}
