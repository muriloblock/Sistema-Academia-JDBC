package DAO;

import conexao.Conexao;
import entity.Treino;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreinoDAO {

    public void cadastrarTreino(Treino treino){
        String sql = "INSERT INTO TREINOS (NOME) VALUES (?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, treino.getNome());

            ps.execute();
            ps.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public int buscaIdPorNome(String nomeExercicio) {
        String sql = "SELECT id_treino FROM treinos WHERE nome = ?";
        PreparedStatement ps = null;
        int idTreino = -1; // Valor padrão para indicar erro

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, nomeExercicio);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTreino = rs.getInt("id_treino"); // Obtém o ID do exercício encontrado
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idTreino; // Retorna o ID do exercício ou -1 em caso de erro
    }

    public List<Treino> buscarTodosTreinos() {
        List<Treino> treinos = new ArrayList<>();
        String sql = "SELECT ID_TREINO, NOME FROM TREINOS";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Treino treino = new Treino();
                treino.setId(rs.getInt("ID_TREINO"));
                treino.setNome(rs.getString("NOME"));
                treinos.add(treino);
            }

            ps.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treinos;
    }

    public void atualizarNome(Treino treino) {
        String sql = "UPDATE treinos SET nome = ? WHERE id_treino = ?";
        PreparedStatement ps = null;
        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, treino.getNome());
            ps.setInt(2, treino.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarTreino(int idTreino) {
        String sql = "DELETE FROM treinos WHERE id_treino = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, idTreino);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
