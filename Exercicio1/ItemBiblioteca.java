public abstract class ItemBiblioteca {
    protected String codigo;
    protected String titulo;
    protected boolean disponivel;

    public ItemBiblioteca(String codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.disponivel = true;
    }

    public abstract int prazoEmprestimo(); 
    public abstract double multaPorDia();  

    public double calcularMulta(int diasAtraso) {
        return diasAtraso > 0 ? diasAtraso * multaPorDia() : 0.0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (prazo: %d dias, multa: R$ %.2f/dia)",
                codigo, titulo, disponivel ? "Disponível" : "Emprestado",
                prazoEmprestimo(), multaPorDia());
    }
}
