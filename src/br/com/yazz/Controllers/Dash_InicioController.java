package br.com.yazz.Controllers;

import br.com.yazz.Class.Projeto;
import br.com.yazz.Util.TextFieldFormatter;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class Dash_InicioController implements Initializable {

    @FXML private Pane pane_Base;
    @FXML private Label lblHora;
    @FXML private Label lblData;
    @FXML private Label lblProjeto;
    @FXML private Label lblEstatus;
    @FXML private Label lblCusto;
    @FXML private Label lblDtSolicitacao;
    
    private final String Chave = "Inicio";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Relogio   
      new Thread(){
           @Override
           public void run(){
               while(true){      
                   Platform.runLater(() -> {
                       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                       lblHora.setText(LocalTime.now().format(dtf));
                   });                         
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(LoginPController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                } 
           }
       }.start(); 
       
       lblData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
       
       Projeto proj = new Projeto();
       proj.Pesquisar(Chave);
       ArrayList<String> arraylist = new ArrayList<>();
       arraylist = proj.Retorno();        
       
       int count = 0;
            for(String i : arraylist){
                switch(count){
                    case 0:
                        lblProjeto.setText(i);
                      break;
                    case 1:
                        lblEstatus.setText(i);
                      break;
                    case 2:
                        lblCusto.setText(i);
                      break;
                    case 3:
                        try {
                            TextFieldFormatter.FormData(lblDtSolicitacao, i);
                        } catch (ParseException ex) {
                          ex.printStackTrace();
                        }
                      break;
                }
                    count += 1;
            }       
    }      
}
