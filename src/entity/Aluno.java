package entity;

public class Aluno {
    private int id;
    private String cpf;
    private String nome;
    private java.sql.Date data_nascimento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public java.sql.Date getDataNascimento() {
        return data_nascimento;
    }

    public void setDataNascimento(java.sql.Date data_nascimento) {
        this.data_nascimento = (java.sql.Date) data_nascimento;
    }

}
