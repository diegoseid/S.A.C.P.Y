package br.com.yazz.Util;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author Diego Seid
 */
public class Excessoes {
    
   public static void TratamentoDeErro(Exception e){
       
       Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falha de Conexão");
            alert.setHeaderText("Falha ao tentar abrir conexão com o Banco!");
            alert.setContentText("Log do Error:");

            // Cria um Exception Expansivel
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(textArea, 0, 1);

            //define o exception expansivel dentro do painel de dialogo
            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
   }
}
