package br.com.yazz.Util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Diego Seid
 */
public class Buttons {
    
    static public void FecharTela(JFXButton butFechar){
        Stage stage = (Stage) butFechar.getScene().getWindow();
        stage.close();
}
    
    static public void Minimizar(JFXButton btMinimizar){
        Stage stage = (Stage) btMinimizar.getScene().getWindow();
        stage.toBack();
}
    static public void MudarCor(JFXButton Botao){     
        
         Botao.setOnMouseMoved(((MouseEvent event) -> {
         Botao.setStyle("-fx-background-color: #23a3e2;");
    }));
         
         Botao.setOnMouseExited(((MouseEvent event) -> {
         Botao.setStyle("-fx-background-color: #2b579a;");
    }));

    }
    
}