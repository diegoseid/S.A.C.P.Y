package br.com.yazz.Controllers;

import br.com.yazz.Class.Chamado;
import br.com.yazz.DAO.ConexaoBD;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Diego Seid
 */
public class AdRelatoriosController implements Initializable {

    @FXML private PieChart PCProjetos;
    @FXML private PieChart PCChamados;
    @FXML private JFXButton butBuscar;
    @FXML private DatePicker dpInico;
    @FXML private DatePicker dpFim;
    @FXML private TextField ChAbertos;
    @FXML private TextField ChEncerrados;
    @FXML private TextField PrEncerrados;
    @FXML private TextField PrAbertos;
    
    private String Aberto, Encerrado, EmAnalise, Chave = "RelChamados", sql, procedure;
    private Chamado cham = new Chamado();
    private ConexaoBD con = new ConexaoBD();
    
    private ArrayList<Integer> array = new ArrayList<>();
    public static ArrayList<String> arrayFiltro = new ArrayList<>();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      new Thread(){
          public void run(){
              Platform.runLater(() -> {
        sql = "select count(*) from tb_chamados group by status order by status";
        cham.Pesquisar(Chave, sql);
        array = cham.RetornoRel();

        ObservableList<PieChart.Data> pieChartDataChamados = FXCollections.observableArrayList(
                new PieChart.Data("Abertos", array.get(0)),
                new PieChart.Data("Em Analise", array.get(1)),
                new PieChart.Data("Encerrados", array.get(2)));
        
        PCChamados.setData(pieChartDataChamados);
        
         pieChartDataChamados.forEach(data ->
                                    data.nameProperty().bind(
                                                Bindings.concat(
                                                    data.getName(), " ", data.pieValueProperty())
                                                )
                                        );

        cham.Limpar();
              });
          }
      }.start();
       
        
       new Thread(){ 
           public void run(){
               Platform.runLater(() -> {
        sql = "select count(*) from tb_projetos group by status order by status";
        cham.Pesquisar(Chave, sql);
        array = cham.RetornoRel();

        ObservableList<PieChart.Data> pieChartDataProjetos = FXCollections.observableArrayList(
                new PieChart.Data("Abertos", array.get(0)),
                new PieChart.Data("Em Analise", array.get(1)),
                new PieChart.Data("Encerrados", array.get(2)));
        
        PCProjetos.setData(pieChartDataProjetos); 
        
           pieChartDataProjetos.forEach(data ->
                                    data.nameProperty().bind(
                                                Bindings.concat(
                                                    data.getName(), " ", data.pieValueProperty())
                                                )
                                        );
        
        cham.Limpar();
               });}
             }.start();
    }    
    

    @FXML
    private void Pesquisar(ActionEvent event) {
        Chave = "FiltroRel";
        
        sql = "select count(*), status from tb_projetos where dt_abertura between '" + dpInico.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")) +
                "' and '" + dpFim.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")) + "' group by status";
        con.pesquisar(Chave, sql);
        
        PrAbertos.setText("0");
        PrEncerrados.setText("0");
        
        int i = 0;
        for(String str : arrayFiltro){
            if(str.equals("Aberto"))
                PrAbertos.setText(arrayFiltro.get(i-1));
            
            if(str.equals("Encerrado"))
                 PrEncerrados.setText(arrayFiltro.get(i-1));
            i++;
        }       

        arrayFiltro.clear();
        
        sql = "select count(*), status from tb_chamados where dt_abertura between '" + dpInico.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")) +
                "' and '" + dpFim.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")) + "' group by status";
        con.pesquisar(Chave, sql);
        
        ChAbertos.setText("0");
        ChEncerrados.setText("0");
        
            int in = 0;
        for(String str : arrayFiltro){
            if(str.equals("Aberto"))
                ChAbertos.setText(arrayFiltro.get(in-1));
            
            if(str.equals("Encerrado"))
                 ChEncerrados.setText(arrayFiltro.get(in-1));
            in++;
        }       

        arrayFiltro.clear();
    }
    
}
