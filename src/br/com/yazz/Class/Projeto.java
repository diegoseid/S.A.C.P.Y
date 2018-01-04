package br.com.yazz.Class;

import static br.com.yazz.Class.Usuario.getID;
import br.com.yazz.DAO.ConexaoBD;
import static br.com.yazz.DAO.ConexaoBD.inserir;
import java.util.ArrayList;

/**
 *
 * @author Diego Seid
 */
public class Projeto {
    
    private Integer Id, QTDEAcessos, Fk_Id_Cli;
    private float Custo;
    private String NomeProj, Descricao, Prioridade,Status, Abertura, Encerramento, Linguagens, sql, Filtro ;
    private static ArrayList<String> arraylist = new ArrayList<>();
    private static ArrayList<Projeto> arrayprojeto = new ArrayList<>();
    private ConexaoBD con = new ConexaoBD();

    public Projeto(int ID, String NomeProj, String Descricao, int QTDEAcessos, String Prioridade, String Status, String Dt_Abertura, float Custo, String Linguagens, String Dt_Fim, int Fk_Id_Cli) {
        this.Id = ID;
        this.NomeProj = NomeProj;
        this.Descricao = Descricao;
        this.QTDEAcessos = QTDEAcessos;
        this.Prioridade = Prioridade;
        this.Status = Status;
        this.Abertura = Dt_Abertura;
        this.Custo = Custo;
        this.Linguagens = Linguagens;
        this.Encerramento = Dt_Fim;
        this.Fk_Id_Cli = Fk_Id_Cli;
    }

    public Projeto() {}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer ID) {
        this.Id = ID;
    }

    public void setFiltro(String Filtro) {
        this.Filtro = Filtro;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    public String getLinguagens() {
        return Linguagens;
    }

    public void setLinguagens(String Linguagens) {
        this.Linguagens = Linguagens;
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

    public float getCusto() {
        return Custo;
    }

    public void setCusto(float Custo) {
        this.Custo = Custo;
    }

    public Integer getFk_Id_Cli() {
        return Fk_Id_Cli;
    }

    public void setFk_Id_Cli(Integer Fk_Id_Cli) {
        this.Fk_Id_Cli = Fk_Id_Cli;
    }

    public String getNomeProj() {
        return NomeProj;
    }

    public void setNomeProj(String NomeProj) {
        this.NomeProj = NomeProj;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public Integer getQTDEAcessos() {
        return QTDEAcessos;
    }

    public void setQTDEAcessos(Integer QTDEAcessos) {
        this.QTDEAcessos = QTDEAcessos;
    }

    public String getPrioridade() {
        return Prioridade;
    }

    public void setPrioridade(String Prioridade) {
        this.Prioridade = Prioridade;
    }
    
    public void Incluir(){
        sql = "INSERT INTO TB_PROJETOS VALUES (seqProjetos.nextval," + getID() + ", '" + NomeProj + "', 'Aberto', " + 
              QTDEAcessos + ", null , '" + Prioridade + "','" + Descricao + "', null, sysdate, null, null)";
        //metodo inserir da classe ConexaoBD;
        inserir(sql);
    }
    
    public void Pesquisar(String Chave){
        //metodo pesquisar da classe ConexaoBD;
        switch(Chave){
            case "Projeto":
                sql = "select * from tb_projetos where id_usuario = " + getID();
                    con.pesquisar(Chave, sql);
                break;
            case "Estatus":
                sql = "select * from tb_projetos where nm_projeto = '" + NomeProj + "' and id_usuario = " + getID();
                    con.pesquisar(Chave, sql);
                break;
            case "Inicio":
                sql = "select * from tb_projetos where  dt_abertura = (select max(dt_abertura) from tb_projetos where id_usuario = " + getID() + ")";
                    con.pesquisar(Chave, sql);
                break;
            case "EProjeto":
                sql = "select * from tb_projetos where n_pxc = 1 and id_usuario = " + getID();
                  Chave = "Projeto";
                    con.pesquisar(Chave, sql);
                break;
            case "TbProjeto":
                sql = "select * from tb_projetos order by dt_abertura";
                    con.pesquisar(Chave, sql);
                break;
            case "EdtProjeto":                
                sql = "select * from tb_Projetos where id_projeto = " + getId();
                  String chave = "TbProjeto";
                    con.pesquisar(chave, sql);
                break;
            case "Filtro":               
                    if(Filtro == "ID")
                        sql = "select * from tb_Projetos where id_projeto = " + Id;                        
                    else
                        sql = "select * from tb_Projetos where nm_projeto like '%" + NomeProj + "%'";
                  String key = "TbProjeto";
                    con.pesquisar(key, sql);
                break;    
            default:
                break;
    }
    }
    
    public static void Limpar(){
        arraylist.clear();
        arrayprojeto.clear();
    }
    
     public static void setArray(String Proj){
        arraylist.add(Proj);      
    }
    
    public ArrayList Retorno(){
        return arraylist;
    }
    
    public static void setArrayProjeto(Projeto projeto){
        arrayprojeto.add(projeto);
    }
    
    public static ArrayList<Projeto> getArrayProjeto(){
        return arrayprojeto;
    }
}
