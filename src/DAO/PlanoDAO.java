package DAO;

import conexao.Conexao;
import entity.Plano;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlanoDAO {

    public void cadastrarPlano(Plano plano){
        String sql = "INSERT INTO PLANOS (NOME, VALOR_MENSAL) VALUES (?, ?)";
        PreparedStatement ps = null;

        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, plano.getNome());
            ps.setFloat(2,plano.getValorMensal());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Plano> buscarTodosPlanos() {
        List<Plano> planos = new ArrayList<>();
        String sql = "SELECT ID_PLANO, NOME, VALOR_MENSAL FROM PLANOS";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Plano plano = new Plano();
                plano.setId(rs.getInt("ID_PLANO"));
                plano.setNome(rs.getString("NOME"));
                plano.setValorMensal(rs.getFloat("VALOR_MENSAL"));
                planos.add(plano);
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

        return planos;
    }


    public Plano buscarPlanoPorId() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do plano que deseja buscar: ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "SELECT NOME, VALOR_MENSAL FROM PLANOS WHERE ID_PLANO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Plano plano = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                plano = new Plano();
                plano.setId(id);
                plano.setValorMensal(rs.getFloat("VALOR_MENSAL"));
                plano.setNome(rs.getString("NOME"));
            } else {
                System.out.println("Plano não encontrado.");
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

        return plano;
    }

    public void deletarPlano (int id) {
        String sql = "DELETE FROM PLANOS WHERE ID_PLANO = ?";
        PreparedStatement ps = null;
        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarPlano(Plano plano) {
        String sql = "UPDATE PLANOS SET NOME = ?, VALOR_MENSAL = ? WHERE ID_PLANO = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, plano.getNome());
            ps.setFloat(2, plano.getValorMensal());
            ps.setInt(3,plano.getId());

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


}
