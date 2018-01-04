package br.com.yazz.DAO;

import br.com.yazz.Class.Chamado;
import br.com.yazz.Class.ListaClientes;
import br.com.yazz.Class.Projeto;
import br.com.yazz.Class.Usuario;
import static br.com.yazz.Class.Usuario.setID;
import br.com.yazz.Controllers.AdRelatoriosController;
import br.com.yazz.Controllers.CadastroController;
import br.com.yazz.Controllers.LoginPController;
import static br.com.yazz.Util.Excessoes.TratamentoDeErro;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConexaoBD {

    static Connection conexao = null;
    static Statement consulta;
    static ResultSet rs;
    static String Proj,sql, Username, Password;;
    static long id;

    static void testeDaConexao() {
        try {
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            //conexao = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Oracleuni9");
            System.out.println("Conexao com o banco realizada com sucesso.");
        } catch (Exception e) {            
            System.out.println("Falha durante o teste de conexão!");
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                    System.out.println("Conexão com o banco encerrada com sucesso.");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Falha no encerramento da conexão com o banco!");
                }
            }
        }
    }

   public void call(Integer ID, String Procedure) {
        try {
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            CallableStatement callableStatement = null;
            callableStatement = conexao.prepareCall(Procedure);
             callableStatement.setInt(1, ID);
            callableStatement.execute();
                callableStatement.close();
              conexao.close();
        } catch (SQLException ex) {
            TratamentoDeErro(ex);
        }
   } 
   
   public void pesquisar(String chave, String Sql) {
        try {                
            Usuario usuario = new Usuario();
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            //conexao = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Oracleuni9");
            consulta = conexao.createStatement();
            rs = consulta.executeQuery(Sql);    
            //limpa ArrayList na classe 
            Projeto.Limpar();
            Chamado.Limpar();
            Usuario.Limpar();
                 
            while (rs.next()) {                
                   switch(chave){
                   case "Chamados":
                        Chamado.setArray(rs.getString("ID_CHAMADO"));
                        Chamado.setArray(rs.getString("TP_CHAMADO"));
                        Chamado.setArray(rs.getString("Titulo"));
                        Chamado.setArray(rs.getString("status"));
                        Chamado.setArray(rs.getString("Dt_abertura"));
                        Chamado.setArray(rs.getString("Dt_Entrega"));
                        Chamado.setArray(rs.getString("Descricao"));
                       break;
                   case "Estatus":
                         Projeto.setArray(rs.getString("ID_PROJETO"));
                         Projeto.setArray(rs.getString("STATUS"));                         
                         Projeto.setArray(rs.getString("DT_ABERTURA"));
                         Projeto.setArray(rs.getString("DT_ENTREGA"));
                         Projeto.setArray(rs.getString("DESCRICAO"));
                         Projeto.setArray(rs.getString("LINGUAGENS"));
                       break;
                   case "Inicio":
                         Projeto.setArray(rs.getString("NM_PROJETO"));
                         Projeto.setArray(rs.getString("Status"));
                         Projeto.setArray(rs.getString("CUSTO"));
                         Projeto.setArray(rs.getString("DT_ABERTURA"));                         
                        break;
                   case "Projeto":
                         Projeto.setArray(rs.getString("NM_PROJETO"));
                        break;
                   case "Es_Titulo":
                         Chamado.setArray(rs.getString("TITULO"));
                        break;
                   case "Login":
                         LoginPController LoginP = new LoginPController();
                            setID(rs.getString("ID_USUARIO"));
                            Username = rs.getString("USUARIO");
                            Password = rs.getString("SENHA");
                                 usuario.setUsuario(Username);
                                 usuario.setSenha(Password);
                        break;
                   case "Perfil":
                            Usuario.setArray(rs.getString("NM_Usuario"));
                            Usuario.setArray(rs.getString("Usuario"));
                            Usuario.setArray(rs.getString("Email"));
                            Usuario.setArray(rs.getString("NR_Cpf"));
                            Usuario.setArray(rs.getString("NR_Telefone"));
                            Usuario.setArray(rs.getString("NM_Razao_Social"));
                            Usuario.setArray(rs.getString("NM_Endereco"));
                            Usuario.setArray(rs.getString("NM_Ramo"));
                            Usuario.setArray(rs.getString("Porte"));
                        break;
                   case "Tabela":
                       ListaClientes lc = new ListaClientes(rs.getInt("ID_USUARIO"), rs.getString("NM_USUARIO"), rs.getString("NR_CPF"),
                               rs.getString("Email"), rs.getString("NM_Endereco"), rs.getString("NM_RAZAO_SOCIAL"), rs.getString("Usuario"),
                               rs.getString("Senha"), rs.getString("Porte"), rs.getString("NM_RAMO"), rs.getString("NR_Telefone"), rs.getString("NR_Cnpj"));
                       ListaClientes.setArrayTabela(lc);
                        break;
                    case "TbProjeto":
                       Projeto pjt = new Projeto(rs.getInt("ID_PROJETO"), rs.getString("NM_PROJETO"), rs.getString("DESCRICAO"),
                               rs.getInt("QTD_ACESSO"), rs.getString("PRIORIDADE"), rs.getString("STATUS"), rs.getString("DT_ABERTURA"),
                               rs.getFloat("CUSTO"), rs.getString("LINGUAGENS"), rs.getString("DT_ENTREGA"), rs.getInt("ID_USUARIO"));
                       Projeto.setArrayProjeto(pjt);
                        break;
                    case "TbChamados":
                       Chamado cham = new Chamado(rs.getInt("ID_CHAMADO"), rs.getString("NM_Projeto"), rs.getString("TP_CHAMADO"),
                               rs.getString("Titulo"), rs.getString("Descricao"), rs.getString("Status"), rs.getString("Dt_Abertura"),
                               rs.getString("Dt_Entrega"), rs.getInt("ID_USUARIO"));
                       Chamado.setArrayChamados(cham);
                        break;
                   case "ValidaCad":
                       CadastroController.Result(rs.getInt(1));
                        break;
                   case "RelChamados":
                       Chamado.setArrayRel(rs.getInt(1));
                        break;
                   case "FiltroRel":
                       AdRelatoriosController.arrayFiltro.add(rs.getString(1));
                       AdRelatoriosController.arrayFiltro.add(rs.getString(2));
                        break;
                   }                                
            }     
            
            consulta.close();
            rs.close();

        } catch (SQLException e) { 
            TratamentoDeErro(e);
        }
    }

    public static void inserir(String sql) {
        try {
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            //conexao = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Oracleuni9");
            consulta = conexao.createStatement();
            consulta.executeUpdate(sql);

            consulta.close();

        } catch (SQLException e) {
            TratamentoDeErro(e);
            System.out.println("Falha ao inserir dados na tabela!");
        }
    }

    public static void atualizar (String titulo, String id) {
        try {
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            PreparedStatement atualizacao = null;
            String comando = "UPDATE tb_usuario_cadastro SET senha = ? WHERE Id_usuario = ?";
            atualizacao = conexao.prepareStatement(comando);
            atualizacao.setString(1,titulo);
            atualizacao.setString(2,id);
            atualizacao.executeUpdate();
            
            conexao.close();

        } catch (SQLException e) {
            TratamentoDeErro(e);
            System.out.println("Falha ao alterar dados na tabela!");
        }
    }

    public static void apagar (String Tabela, Integer ID) {
        try {
            conexao = DriverManager.getConnection("jdbc:oracle:thin:@54.233.238.228:1251:XE", "system", "yazz");
            //conexao = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "Oracleuni9");
            PreparedStatement delecao = null;
            String comando = "DELETE FROM " + Tabela + " WHERE ID_CHAMADO = ?";
            delecao = conexao.prepareStatement(comando);
            delecao.setString(1,ID.toString());
            delecao.executeUpdate();

            conexao.close();

        } catch (SQLException e) {
            TratamentoDeErro(e);
            System.out.println("Falha ao alterar dados na tabela!");
        }
    }
}