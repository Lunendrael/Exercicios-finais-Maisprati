public class Professor extends Usuario {

    public Professor(String nome) {
        super(nome);
    }

    @Override
    public int limiteItens() {
        return 5;
    }
}
