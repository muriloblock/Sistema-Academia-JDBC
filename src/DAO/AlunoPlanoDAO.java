package DAO;

import conexao.Conexao;
import entity.Aluno;
import entity.Plano;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlunoPlanoDAO {

    public void associarAlunoPlano(Aluno aluno, Plano plano, Date dataInicio, String dadosCartao){
        String sql = "INSERT INTO Aluno_Planos (id_aluno, id_plano, data_inicio, dados_cartao_credito) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.setInt(2,plano.getId());
            ps.setDate(3,dataInicio);
            ps.setString(4,dadosCartao);
            ps.execute();
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desassociarAlunoPlano(Aluno aluno){
        String sql = "DELETE FROM ALUNO_PLANOS WHERE ID_ALUNO = ?";
        PreparedStatement ps = null;
        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, aluno.getId());
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
