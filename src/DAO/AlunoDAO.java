package DAO;

import conexao.Conexao;
import entity.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlunoDAO {

    public void cadastrarAluno(Aluno aluno){
        String sql = "INSERT INTO ALUNOS (CPF, NOME, DATA_NASCIMENTO) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, aluno.getCpf());
            ps.setString(2, aluno.getNome());
            ps.setDate(3,aluno.getDataNascimento());

            ps.execute();
            ps.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Aluno> buscarTodosAlunos(){
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT ID_ALUNO, CPF, NOME, DATA_NASCIMENTO FROM ALUNOS";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("ID_ALUNO"));
                aluno.setCpf(rs.getString("CPF"));
                aluno.setNome(rs.getString("NOME"));
                aluno.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        return alunos;
    }

    public void deletarAluno(int id) {
        String sql = "DELETE FROM ALUNOS WHERE ID_ALUNO = ?";
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

    public Aluno buscarAlunoPorId() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do aluno que deseja buscar: ");
        int id = Integer.parseInt(scanner.nextLine());

        String sql = "SELECT CPF, NOME, DATA_NASCIMENTO FROM ALUNOS WHERE ID_ALUNO = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Aluno aluno = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setId(id);
                aluno.setCpf(rs.getString("CPF"));
                aluno.setNome(rs.getString("NOME"));
                aluno.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
            } else {
                System.out.println("Aluno não encontrado.");
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

        return aluno;
    }

    public void atualizarAluno(Aluno aluno) {
        String sql = "UPDATE ALUNOS SET CPF = ?, NOME = ?, DATA_NASCIMENTO = ? WHERE ID_ALUNO = ?";
        PreparedStatement ps = null;
        try{
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, aluno.getCpf());
            ps.setString(2, aluno.getNome());
            ps.setDate(3, aluno.getDataNascimento());
            ps.setInt(4, aluno.getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Aluno buscarAlunoPorCPF(String CPF) {
        Scanner scanner = new Scanner(System.in);

        String sql = "SELECT ID_ALUNO, NOME, DATA_NASCIMENTO FROM ALUNOS WHERE CPF = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Aluno aluno = null;

        try {
            Connection conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setString(1, CPF); // Corrigido para setString para um campo de CPF que é uma string
            rs = ps.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setId(rs.getInt("ID_ALUNO"));
                aluno.setCpf(CPF);
                aluno.setNome(rs.getString("NOME"));
                aluno.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
            } else {
                System.out.println("Aluno não encontrado.");
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

        return aluno;
    }


}
