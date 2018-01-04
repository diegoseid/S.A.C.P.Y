package br.com.yazz.Controllers;

import br.com.yazz.Class.Projeto;
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
public class Dash_ProjetosController implements Initializable {

    @FXML private JFXButton butNProjeto;
    @FXML private AnchorPane pane_Base;
    @FXML private Label lblEstatus;
    @FXML private Label lblCod;
    @FXML private Label lblAbertura;
    @FXML private Label lblEncerramento;
    @FXML private ChoiceBox cbProjeto;
    @FXML private TextArea lblDescricao;
    @FXML private TextArea lblLinguagens;
    @FXML private JFXButton butCancelar;
    
    private Projeto proj = new Projeto();
    private String Chave = "Projeto";
    private ArrayList<String> arraylist = new ArrayList<>();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //arraylist para receber o dados que irao aparecer para o cliente
        ArrayList<String> array = new ArrayList<>();        
        
        //Botão para chamar a tela de novo Projeto
         butNProjeto.setOnMouseClicked((event) -> {
            try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/Dash_NovoProjeto.fxml"));
                 pane_Base.getChildren().clear();
                 pane_Base.getChildren().add(root);
           } catch (IOException ex) {
               ex.printStackTrace();
           }
        });
         
         //chama a pesquisa passando a chave com parametro, ira listar os projetos do cliente
         proj.Pesquisar(Chave);
         array = proj.Retorno();
         ObservableList<String> oblProjeto = FXCollections.observableArrayList(array);
         cbProjeto.setItems(oblProjeto);

         
         //codigo ira entrar em ação assim que um projeto for selecionado
         cbProjeto.setOnAction((event) -> {
          Chave = "Estatus";
          lblEncerramento.setText("");
            //passa o nome do projeto a ser usado no select
           proj.setNomeProj(cbProjeto.getValue().toString());
            proj.Limpar();
             proj.Pesquisar(Chave); 
                arraylist = proj.Retorno();
                int count = 0;
                  //o for ira verificar a posicao do array e vai pegar e enserir os valores em cada label correspondete
                    for(String i : arraylist){
                        switch(count){
                            case 0:
                                lblCod.setText(i);
                                break;
                            case 1:
                                lblEstatus.setText(i);
                                break;
                            case 2:
                                try {
                                    TextFieldFormatter.FormData(lblAbertura, i);
                                    } catch (ParseException ex) {
                                  ex.printStackTrace();
                                }
                                break;
                            case 3:
                             if(i != null){   
                                try {
                                    TextFieldFormatter.FormData(lblEncerramento, i);
                                    } catch (ParseException ex) {
                                  ex.printStackTrace();
                                }}
                                break;
                            case 4:
                                lblDescricao.setText(i);
                                break;
                            case 5:
                                lblLinguagens.setText(i);
                                break;
                            default:
                                break;
                        }
                        count += 1;
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
