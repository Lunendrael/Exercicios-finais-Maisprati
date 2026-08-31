public class Aluno extends Usuario {

    public Aluno(String nome) {
        super(nome);
    }

    @Override
    public int limiteItens() {
        return 3;
    }
}
