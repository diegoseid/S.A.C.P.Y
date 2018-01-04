package br.com.yazz.Util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import static br.com.yazz.Util.Moveble.MovePane;

/**
 *
 * @author Diego Seid
 */
public class Start extends Application {    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/yazz/View/LoginP.fxml"));        
        //Chama o metodo que permite mover o pane
        MovePane(root,stage);        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
