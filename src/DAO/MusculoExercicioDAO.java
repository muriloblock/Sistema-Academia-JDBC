package DAO;

import conexao.Conexao;
import entity.Exercicio;
import entity.Musculo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MusculoExercicioDAO {
    public void associarMusculoExercicio(Musculo musculo, Exercicio exercicio) {
        String sql = "INSERT INTO musculos_exercicio (id_exercicio, id_musculo) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, exercicio.getId());
            ps.setInt(2, musculo.getId());

            ps.execute();
            ps.close();

            System.out.println("Músculo associado ao exercício com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desassociarMusculoExercicio(Musculo musculo, Exercicio exercicio) {
        String sql = "DELETE FROM MUSCULOS_EXERCICIO WHERE ID_MUSCULO = ? AND ID_EXERCICIO = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, musculo.getId());
            ps.setInt(2, exercicio.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Músculo desassociado do exercício com sucesso.");
            } else {
                System.out.println("Não foi possível desassociar o músculo do exercício.");
            }

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
