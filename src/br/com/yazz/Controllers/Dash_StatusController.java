package br.com.yazz.Controllers;

import br.com.yazz.Class.Chamado;
import br.com.yazz.Class.Projeto;
import static br.com.yazz.Class.Usuario.getID;
import br.com.yazz.Util.TextFieldFormatter;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class Dash_StatusController implements Initializable {
    
    @FXML private ChoiceBox cbProjeto;
    @FXML private ChoiceBox cbTitulo;
    @FXML private Label lblAbertura;
    @FXML private Label lblEncerramento;
    @FXML private Label lblTipo;
    @FXML private Label lblEstatus;
    @FXML private Label lblID;
    @FXML private TextArea txaDescricao;
    @FXML private AnchorPane pane_Base;
    @FXML private JFXButton butFechar;
    
    private String sql, Chave;
    private ArrayList<String> arrayChamado = new ArrayList<>();
    private ArrayList<String> arrayProjeto = new ArrayList<>();
    private ArrayList<String> arrayTitulo = new ArrayList<>();
    private Projeto proj = new Projeto();
    private Chamado cham = new Chamado();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO]
        
        //comando para buscar no banco a lista de projetos
         Chave = "EProjeto";
            proj.Pesquisar(Chave);
        arrayProjeto = proj.Retorno();
         ObservableList<String> oblProjeto = FXCollections.observableArrayList(arrayProjeto);
            cbProjeto.setItems(oblProjeto);
            
        //comando para buscar no banco a lista de titulos
        cbProjeto.setOnAction((event) -> { 
            Chave = "Es_Titulo";
            sql = "select Titulo from tb_chamados where nm_projeto = '" + cbProjeto.getValue() + "' and ID_USUARIO = " + getID();
                cham.Pesquisar(Chave, sql);
            arrayTitulo = cham.Retorno();
                ObservableList<String> oblTitulo = FXCollections.observableArrayList(arrayTitulo);
                    cbTitulo.setItems(oblTitulo);
        });
        
        
        //comando para buscar no banco os dados chamado
        cbTitulo.setOnAction((event) -> {
                Chave = "Chamados";
                sql = "Select * from tb_chamados where titulo = '" + cbTitulo.getValue() + "' and ID_USUARIO = " + getID();
          cham.Pesquisar(Chave, sql);
          arrayChamado = cham.Retorno();
          int count = 0;
            for(String i : arrayChamado){
                switch(count){
                    case 0:
                        lblID.setText(i);
                      break;
                    case 1:
                        lblTipo.setText(i);
                      break;
                    case 3:
                        lblEstatus.setText(i);
                      break;
                    case 4:
                        try {
                            TextFieldFormatter.FormData(lblAbertura, i);
                        } catch (ParseException ex) {
                          ex.printStackTrace();
                        }
                      break;
                    case 5:
                     if(i != null){
                        try {
                            TextFieldFormatter.FormData(lblEncerramento, i);
                        } catch (ParseException ex) {
                          ex.printStackTrace();
                        }}
                      break;
                    case 6:
                        txaDescricao.setText(i);
                      break;
                }
                count += 1;
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
