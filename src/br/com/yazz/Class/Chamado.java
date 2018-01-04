package br.com.yazz.Class;

import br.com.yazz.DAO.ConexaoBD;
import static br.com.yazz.DAO.ConexaoBD.inserir;
import java.util.ArrayList;

/**
 *
 * @author Diego Seid
 */
public class Chamado {
    private Integer ID, Fk_Id_Cli;
    private String Projeto, Tipo, Titulo, Descricao, Status, Abertura, Encerramento, Filtro;
    private static ArrayList<String> array = new ArrayList<>();
    private static ArrayList<Integer> arrayRel = new ArrayList<>();
    private static ArrayList<Chamado> arrayChamados = new ArrayList<>();
    private final ConexaoBD con = new ConexaoBD();

    public Chamado(int ID, String Projeto, String Tipo, String Titulo, String Descricao, String Status, String Abertura, String Encerramento, int Fk_Id_Cli) {
        this.ID = ID;
        this.Projeto = Projeto;
        this.Tipo = Tipo;
        this.Titulo = Titulo;
        this.Descricao = Descricao;
        this.Status = Status;
        this.Abertura = Abertura;
        this.Encerramento = Encerramento;
        this.Fk_Id_Cli = Fk_Id_Cli;
    }

    public Chamado() {      
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
    
    public void setFiltro(String Filtro){
        this.Filtro = Filtro;
    }
    
    public String getProjeto() {
        return Projeto;
    }

    public void setProjeto(String Projeto) {
        this.Projeto = Projeto;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public Integer getFk_Id_Cli() {
        return Fk_Id_Cli;
    }

    public void setFk_Id_Cli(Integer Fk_Id_Cli) {
        this.Fk_Id_Cli = Fk_Id_Cli;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getAbertura() {
        return Abertura;
    }

    public void setAbertura(String Abertura) {
        this.Abertura = Abertura;
    }

    public String getEncerramento() {
        return Encerramento;
    }

    public void setEncerramento(String Encerramento) {
        this.Encerramento = Encerramento;
    }
    
    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }
    
    public void Inserir(String sql){
        inserir(sql);
    }
    
    public void Pesquisar(String Chave, String sql){
            con.pesquisar(Chave, sql);
    }

    public ArrayList Retorno() {
        return array;
    }
    
    public ArrayList RetornoRel() {
        return arrayRel;
    }
    
    public static void Limpar(){
        array.clear();
        arrayRel.clear();
        arrayChamados.clear();
    }

    public static void setArray(String arraylist) {
        array.add(arraylist);
    }
    
    public static void setArrayRel(Integer arraylist) {
        arrayRel.add(arraylist);
    }
    
    public static void setArrayChamados(Chamado chamado) {
        arrayChamados.add(chamado);
    }
    
    public static ArrayList<Chamado> getArrayChamados() {
        return arrayChamados;
    }   
    
}
