
package br.com.yazz.Class;

import br.com.yazz.DAO.ConexaoBD;
import java.util.ArrayList;

public class ListaClientes {

    private String Nome, Cpf, Email, Endereço, Razao, Usuario, Senha, Porte, Ramo, Telefone, Cnpj;
    private Integer ID;
    private static ArrayList<ListaClientes> arrayTabela = new ArrayList<>(); 
    
    ConexaoBD con = new ConexaoBD();

    public ListaClientes(Integer ID, String Nome, String Cpf, String Email, String Endereço, String Razao, String Usuario, String Senha, String Porte, String Ramo, String Telefone, String Cnpj) {
        this.ID = ID;
        this.Nome = Nome;
        this.Cpf = Cpf;
        this.Email = Email;
        this.Endereço = Endereço;
        this.Razao = Razao;
        this.Usuario = Usuario;
        this.Senha = Senha;
        this.Porte = Porte;
        this.Ramo = Ramo;
        this.Telefone = Telefone;
        this.Cnpj = Cnpj;
    }

    public ListaClientes(){}    

    public String getRazao() {
        return Razao;
    }

    public void setRazao(String Razao) {
        this.Razao = Razao;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }
    
    public Integer getID() {
        return ID;
    }
    
    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEndereço() {
        return Endereço;
    }

    public void setEndereço(String Endereço) {
        this.Endereço = Endereço;
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

    public String getPorte() {
        return Porte;
    }

    public void setPorte(String Porte) {
        this.Porte = Porte;
    }

    public String getRamo() {
        return Ramo;
    }

    public void setRamo(String Ramo) {
        this.Ramo = Ramo;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public String getCnpj() {
        return Cnpj;
    }

    public void setCnpj(String Cnpj) {
        this.Cnpj = Cnpj;
    }
    
    public void pesquisar(String Chave, String sql){
        //Chama o metodo pesquisar na classe ConexaoBD
        con.pesquisar(Chave, sql);
    }

    public static ArrayList<ListaClientes> getArrayTabela() { 
        return arrayTabela;
    }

    public static void setArrayTabela(ListaClientes lc) {
       arrayTabela.add(lc);
    }
    
    public void Limpar(){
        arrayTabela.clear();
    }
    
}