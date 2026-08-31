public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca(10, 10);

        Livro livro1 = new Livro("L001", "Dom Casmurro");
        Livro livro2 = new Livro("L002", "O Cortiço");
        Revista revista1 = new Revista("R001", "Superinteressante - Ed. 400");

        biblioteca.cadastrarItem(livro1);
        biblioteca.cadastrarItem(livro2);
        biblioteca.cadastrarItem(revista1);

        Aluno aluno = new Aluno("aluno1");
        Professor professor = new Professor("professor1");

        biblioteca.cadastrarUsuario(aluno);
        biblioteca.cadastrarUsuario(professor);

        biblioteca.listarAcervo();

        System.out.println("\n--- Teste de empréstimo ---");

        biblioteca.emprestar(aluno, "L001");

        biblioteca.emprestar(aluno, "L002");
        biblioteca.emprestar(aluno, "R001");

        Livro livro3 = new Livro("L003", "Memórias Póstumas de Brás Cubas");
        biblioteca.cadastrarItem(livro3);

        biblioteca.emprestar(aluno, "L003");

        System.out.println("\n--- Teste de devolução ---");
        biblioteca.devolver(aluno, "L001", 2); 
        System.out.println();
        biblioteca.listarAcervo();
    }
}
