package br.com.TccAndroid.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.com.TccAndroid.Conexao;
import br.com.TccAndroid.model.Usuario;

public class DAO {

    public Usuario verificaLogin(String usuario, String password){

        try{
            Connection conn = Conexao.conectar();

            if(conn != null){
                String sql = "select * from usuario where nome = '"+usuario+"' and senha = '"+password+"'";

                Statement st = null;
                st = conn.createStatement();

                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    Usuario usu = new Usuario();
                    usu.setUsername(rs.getString(2));
                    usu.setPassword(rs.getString(3));

                    conn.close();
                    return usu;
                }
            }
        }catch(Exception e){
            e.getMessage();
        }
        return null;
    }

    public static int inserirUsuario(Usuario usuario){
        int resposta = 0;

        try{
            PreparedStatement pst = Conexao.conectar().prepareStatement(
                    "insert into usuario (nome,senha,email)" +
                            "values (?,?,?)"
            );
            pst.setString(1, usuario.getUsername());
            pst.setString(2, usuario.getPassword());
            pst.setString(3, usuario.getEmail());

            resposta = pst.executeUpdate();

        }catch (Exception e){
            e.getMessage();
        }
        return resposta;
    }

    public Usuario validarUsuarioCad(String usuario){

        try{
            Connection conn = Conexao.conectar();

            if(conn != null){
                String sql = "select * from usuario where nome = '"+usuario+"'";

                Statement st = null;
                st = conn.createStatement();

                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    Usuario valiusu = new Usuario();
                    valiusu.setUsername(rs.getString(1));

                    conn.close();
                    return valiusu;
                }
            }
        }catch(Exception e){
            e.getMessage();
        }
        return null;

    }

    public Usuario validarEmailCad(String email){

        try{
            Connection conn = Conexao.conectar();

            if(conn != null){
                String sql = "select * from usuario where email = '"+email+"'";

                Statement st = null;
                st = conn.createStatement();

                ResultSet rs = st.executeQuery(sql);
                while(rs.next()) {
                    Usuario valiEmail = new Usuario();
                    valiEmail.setUsername(rs.getString(1));

                    conn.close();
                    return valiEmail;
                }
            }
        }catch(Exception e){
            e.getMessage();
        }
        return null;
    }

}


