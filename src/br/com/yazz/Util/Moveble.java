package br.com.yazz.Util;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Diego Seid
 */
public class Moveble {
   static double xOffset = 0;
   static double yOffset = 0;
   
static public void MovePane(Parent root,Stage stage){
        
    
        //remove a barra padrão do windows
         stage.initStyle(StageStyle.UNDECORATED);
         
         root.setOnMousePressed(new EventHandler<MouseEvent>
        () {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        
        root.setOnMouseDragged(new EventHandler<MouseEvent>
        () {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                
            }
        });
        
        Image image = new Image("/br/com/yazz/Images/logo.png");
        stage.getIcons().add(image);
                
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
}
}
