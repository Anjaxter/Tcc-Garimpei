package br.com.TccAndroid.dao;

import android.database.Cursor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.TccAndroid.Conexao;
import br.com.TccAndroid.model.Brecho;

public class BrechoDAO {

    public static List<Brecho> pesquisarBrecho(){
        //lista os brechos
        List<Brecho> lista = null;
        //objeto de preparacao
        PreparedStatement pst;

        try {
            //cria a declaracao
            pst = Conexao.conectar().prepareStatement("" +
                    "select * from brecho Order by id ASC");
            //executa o comando sql
            ResultSet res = pst.executeQuery();

            lista = new ArrayList<>();
            while(res.next()){
                lista.add(new Brecho(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getBytes(8),
                        res.getString(9)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static List<Brecho> pesquisarBrecho(String dado){
        //lista os brechos
        List<Brecho> lista = null;
        //objeto de preparacao
        PreparedStatement pst;

        try {
            //cria a declaracao
            pst = Conexao.conectar().prepareStatement("SELECT * FROM brecho WHERE titulo LIKE ?  or endereco LIKE ?");
            pst.setString(1, "%" + dado + "%");
            pst.setString(2, "%" + dado + "%");

            ResultSet res = pst.executeQuery();

            lista = new ArrayList<>();
            while(res.next()){
                lista.add(new Brecho(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getBytes(8),
                        res.getString(9)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

//    String titulo, String descricao, String telefone, String horariofuncionamento, String endereco
    public static int cadastrarBrecho(Brecho brecho){
        //0 significa que nao inseriu
        int res = 0;

        try {
            PreparedStatement pst = Conexao.conectar().prepareStatement(
                    "insert into brecho (titulo,descricao,telefone,horaFuncionamentoDas,horaFuncionamentoAs,endereco,numero,fotos,codUsuario) values" +
                            " (?,?,?,?,?,?,?,?,?)"
            );

            pst.setString(1, brecho.getTitulo());
            pst.setString(2, brecho.getDescricao());
            pst.setString(3, brecho.getTelefone());
            pst.setString(4, brecho.getHoraFuncionaentoDas());
            pst.setString(5, brecho.getHoraFuncionaentoAs());
            pst.setString(6, brecho.getEndereco());
            pst.setString(7, brecho.getNumero());
            pst.setBytes(8, brecho.getFotos());
            pst.setInt(9,brecho.getCodUsuario());

            res = pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public static List<Brecho> infoBrecho(String id){
        //lista os brechos
        List<Brecho> lista = null;
        //objeto de preparacao
        PreparedStatement pst;

        try {
            //cria a declaracao
            pst = Conexao.conectar().prepareStatement("" +
                    "select * from brecho where id = ?");
            //executa o comando sql
            pst.setString(1, id);
            ResultSet res = pst.executeQuery();

            lista = new ArrayList<>();
            while(res.next()){
                lista.add(new Brecho(
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        res.getString(8),
                        res.getBytes(7),
                        res.getString(9)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static byte[] getBytesFromDatabase(String recordId) {
        String query = "select fotos from brecho where id = ?";

        try (PreparedStatement statement = Conexao.conectar().prepareStatement(query)) {
            statement.setString(1, recordId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBytes("fotos");
                }
                Conexao.conectar().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Brecho> meusBrecos(String id){
        //lista os brechos
        List<Brecho> lista = null;
        //objeto de preparacao
        PreparedStatement pst;

        try {
            //cria a declaracao
            pst = Conexao.conectar().prepareStatement("" +
                    "select * from brecho WHERE codUsuario = ?");
            //executa o comando sql
            pst.setString(1, id);
            ResultSet res = pst.executeQuery();

            lista = new ArrayList<>();
            while(res.next()){
                lista.add(new Brecho(
                        res.getInt(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getBytes(8),
                        res.getString(9)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public static void deletarBrecho(int id) {

        try {
            Connection conn = Conexao.conectar();

            // Query para deletar o usuário pelo ID
            String deleteQuery = "DELETE FROM brecho WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int atualizarDadosBrecho(String titulo, String descricao, String telefone, String endereco, String enderecoNum, String horaDas, String horaAs, byte[] foto, String id) {
        Connection conn = Conexao.conectar();
        PreparedStatement preparedStatement = null;

        try {
            // Consulta SQL de atualização
            String updateQuery = "UPDATE brecho SET titulo = ?,descricao = ?, telefone = ?,endereco = ?, numero = ?,horaFuncionamentoDas = ?, horaFuncionamentoAs = ?, fotos = ? WHERE id = ?";

            // Cria o PreparedStatement com a consulta
            preparedStatement = conn.prepareStatement(updateQuery);

            // Define os parâmetros da consulta
            preparedStatement.setString(1, titulo);
            preparedStatement.setString(2, descricao);
            preparedStatement.setString(3, telefone);
            preparedStatement.setString(4, endereco);
            preparedStatement.setString(5, enderecoNum);
            preparedStatement.setString(6, horaDas);
            preparedStatement.setString(7, horaAs);
            preparedStatement.setBytes(8, foto);
            preparedStatement.setString(9, id);


            // Executa a consulta de atualização
            int linhasAfetadas = preparedStatement.executeUpdate();

            return linhasAfetadas;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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

}
