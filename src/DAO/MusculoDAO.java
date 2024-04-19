package DAO;

import conexao.Conexao;
import entity.Musculo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusculoDAO {
    public void listarMusculos() {
        String sql = "SELECT ID_MUSCULO, NOME FROM MUSCULOS";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idMusculo = rs.getInt("ID_MUSCULO");
                String nomeMusculo = rs.getString("NOME");
                System.out.println(idMusculo + " - " + nomeMusculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void cadastrarMusculo(Musculo musculo) {
        String sql = "INSERT INTO MUSCULOS (NOME) VALUES (?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, musculo.getNome());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int buscaIdPorNome(String nomeMusculo) {
        String sql = "SELECT id_musculo FROM musculos WHERE nome = ?";
        PreparedStatement ps = null;
        int idMusculo = -1; // Valor padrão para indicar erro

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, nomeMusculo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idMusculo = rs.getInt("id_musculo"); // Obtém o ID do exercício encontrado
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idMusculo; // Retorna o ID do exercício ou -1 em caso de erro
    }

    public Musculo buscarMusculoPorIndice(int indice) {
        String sql = "SELECT ID_MUSCULO, NOME FROM MUSCULOS WHERE ID_MUSCULO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Musculo musculo = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, indice);
            rs = ps.executeQuery();

            if (rs.next()) {
                musculo = new Musculo();
                musculo.setId(rs.getInt("ID_MUSCULO"));
                musculo.setNome(rs.getString("NOME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return musculo;
    }


}
