package DAO;

import conexao.Conexao;
import entity.Exercicio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExercicioDAO {
    public void cadastrarExercicio(Exercicio exercicio) {
        String sql = "INSERT INTO EXERCICIOS (NOME, REPETICOES, SERIES, PESO) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, exercicio.getNome());
            ps.setInt(2, exercicio.getRepeticoes());
            ps.setInt(3,exercicio.getSeries());
            ps.setInt(4, exercicio.getPeso());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Exercicio buscarExercicioPorIndice(int indice) {
        String sql = "SELECT ID_EXERCICIO, NOME FROM EXERCICIOS WHERE ID_EXERCICIO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exercicio exercicio = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, indice);
            rs = ps.executeQuery();

            if (rs.next()) {
                exercicio = new Exercicio();
                exercicio.setId(rs.getInt("ID_EXERCICIO"));
                exercicio.setNome(rs.getString("NOME"));
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
    public int buscaIdPorNome(String nomeExercicio) {
        String sql = "SELECT id_exercicio FROM exercicios WHERE nome = ?";
        PreparedStatement ps = null;
        int idExercicio = -1; // Valor padrão para indicar erro

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, nomeExercicio);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idExercicio = rs.getInt("id_exercicio"); // Obtém o ID do exercício encontrado
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idExercicio; // Retorna o ID do exercício ou -1 em caso de erro
    }
    public void listarExercicios() {
        String sql = "SELECT ID_EXERCICIO, NOME FROM EXERCICIOS";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idExercicio = rs.getInt("ID_EXERCICIO");
                String nomeExercicio = rs.getString("NOME");
                System.out.println(idExercicio + " - " + nomeExercicio);
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
    public void deletarExercicio(int idExercicio) {
        String sql1 = "DELETE FROM exercicios WHERE id_exercicio = ?";
        String sql2 = "DELETE FROM musculos_exercicio WHERE id_exercicio = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql1);
            ps.setInt(1, idExercicio);

            ps.executeUpdate();
            ps = Conexao.getConexao().prepareStatement(sql2);
            ps.setInt(1, idExercicio);

            ps.executeUpdate();
            ps.close();

            System.out.println("Exercício deletado com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void atualizarNomeExercicio(Exercicio exercicio) {
        String sql = "UPDATE EXERCICIOS SET NOME = ? WHERE ID_EXERCICIO = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, exercicio.getNome());
            ps.setInt(2, exercicio.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Nome do exercício atualizado com sucesso.");
            } else {
                System.out.println("Não foi possível atualizar o nome do exercício.");
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listarMusculosAssociados(Exercicio exercicio) {
        String sql = "SELECT M.NOME FROM MUSCULOS M JOIN MUSCULOS_EXERCICIO ME ON M.ID_MUSCULO = ME.ID_MUSCULO WHERE ME.ID_EXERCICIO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, exercicio.getId());
            rs = ps.executeQuery();

            System.out.println("Músculos associados ao exercício " + exercicio.getNome() + ":");
            while (rs.next()) {
                String nomeMusculo = rs.getString("NOME");
                System.out.println(nomeMusculo);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Exercicio> buscarTodosExercicios() {
        List<Exercicio> exercicios = new ArrayList<>();
        String sql = "SELECT ID_EXERCICIO, NOME FROM EXERCICIOS";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setId(rs.getInt("ID_EXERCICIO"));
                exercicio.setNome(rs.getString("NOME"));
                exercicios.add(exercicio);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercicios;
    }

}
