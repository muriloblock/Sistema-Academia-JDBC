package DAO;

import conexao.Conexao;
import entity.Exercicio;
import entity.Treino;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExercicioTreinoDAO {
    public void associarExercicioTreino(Exercicio exercicio, Treino treino) {
        String sql = "INSERT INTO TREINO_EXERCICIOS (id_exercicio,id_treino) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, exercicio.getId());
            ps.setInt(2, treino.getId());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exercicio> buscarExerciciosPorTreino(int idTreino) {
        List<Exercicio> exercicios = new ArrayList<>();
        String sql = "SELECT exercicios.* FROM treino_exercicios JOIN exercicios ON treino_exercicios.ID_EXERCICIO = exercicios.ID_EXERCICIO WHERE treino_exercicios.ID_TREINO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, idTreino);
            rs = ps.executeQuery();

            while (rs.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setId(rs.getInt("ID_EXERCICIO"));
                exercicio.setNome(rs.getString("NOME")); // Substitua "NOME" pelo nome do atributo na tabela exercicios
                exercicio.setPeso(rs.getInt("PESO"));
                exercicio.setRepeticoes(rs.getInt("REPETICOES"));
                exercicio.setSeries(rs.getInt("SERIES"));
                exercicios.add(exercicio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Certifique-se de fechar os recursos ResultSet, PreparedStatement e conexão
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

        return exercicios;
    }

    public void deletarExercicio(Exercicio exercicio, Treino treino) {
        String sql = "DELETE FROM treino_exercicios WHERE id_exercicio = ? AND id_treino = ?";
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, exercicio.getId());
            ps.setInt(2, treino.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
