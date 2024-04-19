package DAO;

import conexao.Conexao;
import entity.Aluno;
import entity.Exercicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunosExerciciosInfoDAO {
    public void associarInfo(Aluno aluno, Exercicio exercicio, int id_data) {

        String sql = "INSERT INTO alunos_exercicios_info (ID_DATA, ID_ALUNO, ID_EXERCICIO, SERIES, REPETICOES, PESO) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, id_data);
            ps.setInt(2, aluno.getId());
            ps.setInt(3, exercicio.getId());
            ps.setInt(4, exercicio.getSeries());
            ps.setInt(5, exercicio.getRepeticoes());
            ps.setInt(6, exercicio.getPeso());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void concluirExercicio(int id_exercicio, int id_data) {
        String sql = "UPDATE alunos_exercicios_info SET CONCLUIDO = 1 WHERE id_exercicio = ? AND id_data = ?";
        PreparedStatement ps = null;

        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, id_exercicio);
            ps.setInt(2, id_data);

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exercicio> buscarTodosExercicios(int id_data) {
        List<Exercicio> exercicios = new ArrayList<>();
        PreparedStatement ps = null;
        String sql = "SELECT e.ID_EXERCICIO, e.NOME, aei.SERIES, aei.REPETICOES, aei.PESO, aei.CONCLUIDO " +
                "FROM EXERCICIOS e " +
                "INNER JOIN alunos_exercicios_info aei ON e.ID_EXERCICIO = aei.ID_EXERCICIO " +
                "WHERE aei.ID_DATA = ?";


        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, id_data);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setId(rs.getInt("ID_EXERCICIO"));
                exercicio.setNome(rs.getString("NOME"));
                exercicio.setSeries(rs.getInt("SERIES"));
                exercicio.setRepeticoes(rs.getInt("REPETICOES"));
                exercicio.setPeso(rs.getInt("PESO"));
                exercicio.setConcluido(rs.getInt("CONCLUIDO")); // Assuming CONCLUIDO is a boolean field

                exercicios.add(exercicio);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercicios;
    }

    public Exercicio verificaInfoAnterior(Aluno aluno, Exercicio exercicio) {
        String sql = "SELECT * FROM alunos_exercicios_info WHERE ID_ALUNO = ? AND ID_EXERCICIO = ? ORDER BY ID_DATA DESC LIMIT 1";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exercicio exercicioAnterior = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.setInt(2, exercicio.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                // Se encontrar informações anteriores, cria um novo objeto Exercicio com esses dados
                exercicioAnterior = new Exercicio();
                exercicioAnterior.setId(exercicio.getId());
                exercicioAnterior.setSeries(rs.getInt("SERIES"));
                exercicioAnterior.setRepeticoes(rs.getInt("REPETICOES"));
                exercicioAnterior.setPeso(rs.getInt("PESO"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar conexões e liberar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Se não houver informações anteriores, retorna o próprio exercício sem alterações
        return exercicioAnterior != null ? exercicioAnterior : exercicio;
    }
    public void atualizarInfo(Aluno aluno, Exercicio exercicio, int id_data) {
        String sql = "UPDATE alunos_exercicios_info SET SERIES = ?, REPETICOES = ?, PESO = ?, TEMPO_DESCANSO = ? WHERE ID_EXERCICIO = ? AND ID_ALUNO = ? AND ID_DATA = ?";
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, exercicio.getSeries());
            ps.setInt(2, exercicio.getRepeticoes());
            ps.setInt(3, exercicio.getPeso());
            ps.setInt(4, exercicio.getTempo_descanso());
            ps.setInt(5, exercicio.getId());
            ps.setInt(6, aluno.getId());
            ps.setInt(7, id_data);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Exercicio buscarInfoPorId(int idExercicio, int idData, int idAluno) {
        Exercicio exercicio = new Exercicio();
        String sql = "SELECT SERIES, REPETICOES, PESO, TEMPO_DESCANSO FROM alunos_exercicios_info WHERE ID_EXERCICIO = ? AND ID_DATA = ? AND ID_ALUNO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, idExercicio);
            ps.setInt(2, idData);
            ps.setInt(3, idAluno);
            rs = ps.executeQuery();

            if (rs.next()) {
                exercicio.setId(idExercicio);
                exercicio.setSeries(rs.getInt("SERIES"));
                exercicio.setRepeticoes(rs.getInt("REPETICOES"));
                exercicio.setPeso(rs.getInt("PESO"));
                exercicio.setTempo_descanso(rs.getInt("TEMPO_DESCANSO"));
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

        return exercicio;
    }

    public List<Integer> geraRelatorio(Aluno aluno, int idExercicio) {
        List<Integer> pesos = new ArrayList<>();
        String sql = "SELECT PESO FROM alunos_exercicios_info WHERE ID_ALUNO = ? AND ID_EXERCICIO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.setInt(2, idExercicio);

            rs = ps.executeQuery();

            while (rs.next()) {
                int peso = rs.getInt("PESO");
                pesos.add(peso);
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

        return pesos;
    }

}
