public class Biblioteca {
    private ItemBiblioteca[] acervo;
    private int quantidadeItens;

    private Usuario[] usuarios;
    private int quantidadeUsuarios;

    public Biblioteca(int capacidadeAcervo, int capacidadeUsuarios) {
        this.acervo = new ItemBiblioteca[capacidadeAcervo];
        this.quantidadeItens = 0;
        this.usuarios = new Usuario[capacidadeUsuarios];
        this.quantidadeUsuarios = 0;
    }

    public boolean cadastrarItem(ItemBiblioteca item) {
        if (quantidadeItens >= acervo.length) {
            System.out.println("Erro: acervo lotado. Não foi possível cadastrar \"" + item.getTitulo() + "\".");
            return false;
        }
        acervo[quantidadeItens++] = item;
        return true;
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        if (quantidadeUsuarios >= usuarios.length) {
            System.out.println("Erro: limite de usuários atingido. Não foi possível cadastrar " + usuario.getNome() + ".");
            return false;
        }
        usuarios[quantidadeUsuarios++] = usuario;
        return true;
    }

    private ItemBiblioteca buscarItem(String codigo) {
        for (int i = 0; i < quantidadeItens; i++) {
            if (acervo[i].getCodigo().equals(codigo)) {
                return acervo[i];
            }
        }
        return null;
    }

    public boolean emprestar(Usuario usuario, String codigoItem) {
        ItemBiblioteca item = buscarItem(codigoItem);

        if (item == null) {
            System.out.println("Erro: item de código \"" + codigoItem + "\" não encontrado.");
            return false;
        }
        if (!item.isDisponivel()) {
            System.out.println("Erro: item \"" + item.getTitulo() + "\" já está emprestado.");
            return false;
        }
        if (!usuario.podeEmprestar()) {
            System.out.println("Empréstimo recusado: " + usuario.getNome() +
                    " já atingiu o limite de " + usuario.limiteItens() + " item(ns) emprestado(s).");
            return false;
        }

        item.setDisponivel(false);
        usuario.incrementarEmprestimo();
        System.out.println(usuario.getNome() + " emprestou \"" + item.getTitulo() +
                "\" com sucesso (prazo: " + item.prazoEmprestimo() + " dias).");
        return true;
    }

    public boolean devolver(Usuario usuario, String codigoItem, int diasAtraso) {
        ItemBiblioteca item = buscarItem(codigoItem);

        if (item == null) {
            System.out.println("Erro: item de código \"" + codigoItem + "\" não encontrado.");
            return false;
        }
        if (item.isDisponivel()) {
            System.out.println("Erro: item \"" + item.getTitulo() + "\" não estava emprestado.");
            return false;
        }

        item.setDisponivel(true);
        usuario.decrementarEmprestimo();
        double multa = item.calcularMulta(diasAtraso);

        if (multa > 0) {
            System.out.printf("%s devolveu \"%s\" com %d dia(s) de atraso. Multa: R$ %.2f%n",
                    usuario.getNome(), item.getTitulo(), diasAtraso, multa);
        } else {
            System.out.println(usuario.getNome() + " devolveu \"" + item.getTitulo() + "\" dentro do prazo.");
        }
        return true;
    }

    public void listarAcervo() {
        System.out.println("=== Acervo da Biblioteca ===");
        if (quantidadeItens == 0) {
            System.out.println("Nenhum item cadastrado.");
            return;
        }
        for (int i = 0; i < quantidadeItens; i++) {
            System.out.println(acervo[i]);
        }
    }
}
