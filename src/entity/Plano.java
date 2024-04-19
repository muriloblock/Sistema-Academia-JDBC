package entity;

public class Plano {
    private int id;
    private String nome;
    private float valor_mensal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public float getValorMensal() {
        return valor_mensal;
    }
    public void setValorMensal(float valor_mensal){
        this.valor_mensal = valor_mensal;
    }
}
