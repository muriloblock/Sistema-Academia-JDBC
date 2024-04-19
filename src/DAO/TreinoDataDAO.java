package DAO;

import conexao.Conexao;
import entity.Aluno;
import entity.Treino;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreinoDataDAO {
    public int cadastrarTreino(Treino treino, Aluno aluno) {
        String sql = "INSERT INTO treinos_data (id_aluno, id_treino, data_inicio, data_final) VALUES (?, ?, NOW(), NULL)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int primaryKey = -1; // Valor padrão para indicar falha na obtenção da PK

        try {
            ps = Conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, aluno.getId());
            ps.setInt(2, treino.getId());

            ps.execute();

            // Obtendo a chave primária gerada
            rs = ps.getGeneratedKeys();
            if (((ResultSet) rs).next()) {
                primaryKey = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return primaryKey;
    }

    public void finalizarTreino(int pk){
        String sql = "UPDATE TREINOS_DATA SET DATA_FINAL = NOW() WHERE ID_TREINOS_DATA = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1,pk);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Date> geraRelatorio(Aluno aluno, Date dataInicial, Date dataFinal) {
        List<Date> datasComparecimento = new ArrayList<>();
        String sql = "SELECT DISTINCT DATA_INICIO FROM TREINOS_DATA WHERE ID_ALUNO = ? AND DATA_INICIO BETWEEN ? AND ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.setDate(2, new java.sql.Date(dataInicial.getTime()));
            ps.setDate(3, new java.sql.Date(dataFinal.getTime()));

            rs = ps.executeQuery();

            while (rs.next()) {
                Date data = rs.getDate("DATA_INICIO");
                datasComparecimento.add(data);
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

        return datasComparecimento;
    }

}

