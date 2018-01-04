
package br.com.yazz.Class;
import br.com.yazz.DAO.ConexaoBD;
import static br.com.yazz.DAO.ConexaoBD.inserir;
import static br.com.yazz.Util.Excessoes.TratamentoDeErro;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Usuario {
    
    private static String ID, Usuario, Senha;
    private String RazaoSocial, Cnpj, Nome, Cpf, Telefone, Ramo, Endereco, Porte, Email;
    private static ArrayList<String> arraylist = new ArrayList<>(); 
    static Connection conexao = null;
    
    
    public Usuario(String ID, String Nome, String CPF) {
       this.ID = ID; this.Nome = Nome; this.Cpf = CPF;
    }
    
    public Usuario(){};

    ConexaoBD con = new ConexaoBD();
    
    public static String getID() {
        return ID;
    }
    
    public static void setID(String id) {
        ID = id;
    }
    
    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String RazaoSocial) {
        this.RazaoSocial = RazaoSocial;
    }

    public String getCnpj() {
        return Cnpj;
    }

    public void setCnpj(String Cnpj) {
        this.Cnpj = Cnpj;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String Cpf) {
        this.Cpf = Cpf;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public String getRamo() {
        return Ramo;
    }

    public void setRamo(String Ramo) {
        this.Ramo = Ramo;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String Endereco) {
        this.Endereco = Endereco;
    }

    public String getPorte() {
        return Porte;
    }

    public void setPorte(String Porte) {
        this.Porte = Porte;
    }

    public static String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public static String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    
    public void Inserir(){
        try {
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            CallableStatement callableStatement = null;
            callableStatement = conexao.prepareCall("{call sp_usuario_insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
                callableStatement.setString(1, Nome);
                callableStatement.setString(2, Cpf);
                callableStatement.setString(3, Cnpj);
                callableStatement.setString(4, Email);
                callableStatement.setString(5, Telefone);
                callableStatement.setString(6, Endereco);
                callableStatement.setString(7, Ramo);
                callableStatement.setString(8, RazaoSocial);
                callableStatement.setString(9, Porte);
                callableStatement.setString(10, Usuario);
                callableStatement.setString(11, Senha);
            callableStatement.execute();
                callableStatement.close();
              conexao.close();

               //Evita que o dado de um cliente seja enserido no outro
              Cpf = null;
              Cnpj = null;
        } catch (SQLException ex) {
            TratamentoDeErro(ex);
        }
       
        
    }
    
    public void Pesquisar(String Chave, String sql){
        //Chama o metodo Pesquisar na classe ConexaoBD
            con.pesquisar(Chave, sql);
    }
    
    public static void setArray(String array){
        arraylist.add(array);
    }
    
    public static void Limpar(){
        arraylist.clear();
    }
    
    public ArrayList Retorno(){
        return arraylist;
    }
    
    
}