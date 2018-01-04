package br.com.yazz.Controllers;

import br.com.yazz.Class.Projeto;
import static br.com.yazz.Controllers.AdClientesController.conexao;
import br.com.yazz.DAO.ConexaoBD;
import static br.com.yazz.Util.Excessoes.TratamentoDeErro;
import br.com.yazz.Util.TextFieldFormatter;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
import javafx.scene.control.ButtonBar.ButtonData;
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
public class AdProjetosadmController implements Initializable {

    @FXML private AnchorPane pane_Base;
    @FXML private TextField txtProjeto;
    @FXML private TextField txtCodProj;
    @FXML private ChoiceBox<String> cbStatus;
    @FXML private TextField txtFk_Id_Cli;
    @FXML private Label lblAbertura;
    @FXML private Label lblEncerramento;
    @FXML private TextArea lblDescricao;
    @FXML private TextArea lblLinguagens;
    @FXML private TextField txtQTDE;
    @FXML private TextField txtCusto;
    @FXML private JFXButton butVoltar;
    @FXML private JFXButton butExcluir;
    @FXML private JFXButton butSalvar;
    
    public static Integer ID;
    static Statement consulta;
    private final String Chave = "EdtProjeto", Tabela = "tb_Projetos";
    private String Procedure;
    private Projeto proj = new Projeto();
    private ObservableList<String> oblStatus = FXCollections.observableArrayList("Aberto", "Em Analise", "Encerrado");
    private ArrayList<Projeto> array = new ArrayList<>();
    private final DecimalFormat formatter = new DecimalFormat("#.00");
    private ConexaoBD con = new ConexaoBD();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbStatus.setItems(oblStatus);
        
        txtCodProj.setText(ID.toString());
        
        proj.setId(ID);
        proj.Pesquisar(Chave);
        array = Projeto.getArrayProjeto();
        
        txtFk_Id_Cli.setText(array.get(0).getFk_Id_Cli().toString());
        txtProjeto.setText(array.get(0).getNomeProj());
        cbStatus.setValue(array.get(0).getStatus());
        txtQTDE.setText(array.get(0).getQTDEAcessos().toString());
        txtCusto.setText(formatter.format(array.get(0).getCusto()));
        lblDescricao.setText(array.get(0).getDescricao());
        lblLinguagens.setText(array.get(0).getLinguagens());
        //Formata o Campo Data de Abertura 
        try {
             TextFieldFormatter.FormData(lblAbertura, array.get(0).getAbertura());
        } catch (ParseException ex) {
             ex.printStackTrace();
            }
     if(array.get(0).getEncerramento() != null){        
         //Formata o Campo Data de Encerramento
        try {
             TextFieldFormatter.FormData(lblEncerramento, array.get(0).getEncerramento());
        } catch (ParseException ex) {
             ex.printStackTrace();
            }}
    }

    public static void setIDproj(Integer ID){
        AdProjetosadmController.ID = ID;
    }

    @FXML
    private void Voltar(ActionEvent event) {
         //Chama a tela que mosta os Projetos solicitados.
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/AdProjetos.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }  
    }

    @FXML
    private void Excluir(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alterar Dados Do Projeto");
            alert.setHeaderText("Atenção, Deseja Realmente Excluir Este projeto ?");

            ButtonType buttonTypeSim = new ButtonType("Sim");
            ButtonType buttonTypeNão = new ButtonType("Não", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNão);

            Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeSim){
                    Procedure = "{call sp_projeto_delete (?)}";
                     con.call(ID, Procedure);
                     Voltar(event);
                }        
    }

    @FXML
    private void Salvar(ActionEvent event) {
        try {
            String sql = "update tb_projetos set NM_PROJETO = ?, QTD_ACESSO = ?, Custo = ?, Descricao = ?, Linguagens = ?, Status = ? where ID_PROJETO = ?";
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            //conexao = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Oracleuni9");
            PreparedStatement atualizacao = null;
            atualizacao = conexao.prepareStatement(sql);
            atualizacao.setString(1, txtProjeto.getText());
            atualizacao.setString(2, txtQTDE.getText());
            atualizacao.setString(3, txtCusto.getText());
            atualizacao.setString(4, lblDescricao.getText());
            atualizacao.setString(5, lblLinguagens.getText());
            atualizacao.setString(6, cbStatus.getValue());
            atualizacao.setInt(7, ID);
            atualizacao.executeUpdate();
                           
                if(cbStatus.getValue().equals("Encerrado")){
                    sql = "update tb_projetos set dt_entrega = sysdate where id_projeto = " + ID;
                    consulta = conexao.createStatement();
                    consulta.executeUpdate(sql);
                }
                else{        
                    sql = "update tb_projetos set dt_entrega = null where id_projeto = " + ID;
                    consulta = conexao.createStatement();
                    consulta.executeUpdate(sql);
                }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Projeto");
                alert.setHeaderText("Projeto Atualizado com Sucesso!");
               alert.show();
            conexao.close();

        } catch (SQLException e) {
            TratamentoDeErro(e);
            System.out.println("Falha ao alterar dados na tabela!");
        }
    }
    
}
