package entity;

public class Exercicio {

    private int id;
    private String nome;
    private int repeticoes;
    private int series;
    private int peso;
    private int concluido;
    private int tempo_descanso;

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

    public int getRepeticoes(){
        return repeticoes;
    }
    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }
    public int getPeso() {
        return peso;
    }
    public void setPeso(int peso){
        this.peso = peso;
    }
    public int getSeries() {
        return series;
    }
    public void setSeries(int series) {
        this.series = series;
    }

    public int getConcluido() {
        return concluido;
    }
    public void setConcluido(int concluido) {
        this.concluido = concluido;
    }
    public int getTempo_descanso() {
        return tempo_descanso;
    }
    public void setTempo_descanso(int tempo_descanso) {
        this.tempo_descanso = tempo_descanso;
    }
}
