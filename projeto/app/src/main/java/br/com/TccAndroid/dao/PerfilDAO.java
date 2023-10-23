package br.com.TccAndroid.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.TccAndroid.Conexao;
import br.com.TccAndroid.model.Usuario;

public class PerfilDAO {
    public static List<Usuario> getUserInfo(int id){
        //lista os brechos
        List<Usuario> lista = null;
        //objeto de preparacao
        PreparedStatement pst;

        try {
            //cria a declaracao
            pst = Conexao.conectar().prepareStatement("SELECT * FROM usuario WHERE id = ?");
            pst.setInt(1 ,id);
            //executa o comando sql
            ResultSet res = pst.executeQuery();

            lista = new ArrayList<>();
            while(res.next()){
                lista.add(new Usuario(
                        res.getString(2),
                        res.getString(3),
                        res.getString(4)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
//    String newnome, String email, String senha,
    public static boolean atualizarDadosUsuario(int id,String nome, String senha, String email) {
        Connection conn = Conexao.conectar();
        PreparedStatement preparedStatement = null;

        try {
            // Consulta SQL de atualização
            String updateQuery = "UPDATE usuario SET nome = ?, senha = ?, email = ? WHERE id = ?";

            // Cria o PreparedStatement com a consulta
            preparedStatement = conn.prepareStatement(updateQuery);

            // Define os parâmetros da consulta
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, senha);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, id);

            // Executa a consulta de atualização
            int linhasAfetadas = preparedStatement.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void deletarUser(int id) {

        try {
            Connection conn = Conexao.conectar();

            // Query para deletar o usuário pelo ID
            String deleteQuery = "DELETE FROM usuario WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletarBrechoByUser(int id) {

        try {
            Connection conn = Conexao.conectar();

            // Query para deletar o usuário pelo ID
            String deleteQuery = "DELETE brecho WHERE codUsuario = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int buscarIdUsuarioPorNome(String nome) {
        String sql = "SELECT id FROM usuario WHERE nome = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Retorna -1 se o usuário não for encontrado
    }
    public static Usuario validarUsuarioCad(String usuario, int id) {
        try (Connection conn = Conexao.conectar()) {
            if (conn != null) {
                String sql = "SELECT * FROM usuario WHERE nome = ? AND id != ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, usuario);
                    ps.setInt(2, id);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Usuario valiusu = new Usuario();
                            valiusu.setUsername(rs.getString("nome"));
                            return valiusu;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Handle or log the exception here
            e.printStackTrace();
        }
        return null;
    }


    public static Usuario validarEmailCad(String usuario, int id) {
        try (Connection conn = Conexao.conectar()) {
            if (conn != null) {
                String sql = "SELECT * FROM usuario WHERE email = ? AND id != ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, usuario);
                    ps.setInt(2, id);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Usuario valiusu = new Usuario();
                            valiusu.setUsername(rs.getString("nome"));
                            return valiusu;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // Handle or log the exception here
            e.printStackTrace();
        }
        return null;
    }


}
