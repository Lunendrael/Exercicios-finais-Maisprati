public abstract class Usuario {
    protected String nome;
    protected int quantidadeEmprestada;

    public Usuario(String nome) {
        this.nome = nome;
        this.quantidadeEmprestada = 0;
    }
    public abstract int limiteItens();

    public boolean podeEmprestar() {
        return quantidadeEmprestada < limiteItens();
    }

    public void incrementarEmprestimo() {
        quantidadeEmprestada++;
    }

    public void decrementarEmprestimo() {
        if (quantidadeEmprestada > 0) {
            quantidadeEmprestada--;
        }
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }
}
