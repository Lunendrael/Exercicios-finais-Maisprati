-- =========================================================
-- Modelo relacional da Biblioteca (sem herança)
-- item e usuario são diferenciados por uma coluna "tipo"
-- =========================================================

-- Ordem de criação: item e usuario não dependem uma da outra,
-- mas ambas precisam existir antes de emprestimo (que referencia as duas).

-- -------------------------
-- 11. Tabela item
-- -------------------------
CREATE TABLE item (
    id          SERIAL PRIMARY KEY,
    codigo      VARCHAR(20) NOT NULL UNIQUE,
    titulo      VARCHAR(200) NOT NULL,
    tipo        VARCHAR(10) NOT NULL CHECK (tipo IN ('livro', 'revista')),
    autor       VARCHAR(150),
    edicao      VARCHAR(50),          -- usado principalmente por revistas
    disponivel  BOOLEAN NOT NULL DEFAULT TRUE
);

-- -------------------------
-- 12. Tabela usuario
-- -------------------------
CREATE TABLE usuario (
    id             SERIAL PRIMARY KEY,
    nome           VARCHAR(150) NOT NULL,
    tipo           VARCHAR(10) NOT NULL CHECK (tipo IN ('aluno', 'professor')),
    limite_itens   INTEGER NOT NULL CHECK (limite_itens > 0)
);

-- -------------------------
-- 13. Tabela emprestimo
-- -------------------------
CREATE TABLE emprestimo (
    id                        SERIAL PRIMARY KEY,
    item_id                   INTEGER NOT NULL REFERENCES item(id),
    usuario_id                INTEGER NOT NULL REFERENCES usuario(id),
    data_retirada             DATE NOT NULL,
    data_devolucao_prevista   DATE NOT NULL,
    data_devolucao            DATE,                 -- NULL enquanto o item não é devolvido
    valor_multa               NUMERIC(10,2) NOT NULL DEFAULT 0.00
);

-- =========================================================
-- 15. Dados de teste
-- =========================================================

-- ---- Itens (2 livros + 2 revistas) ----
INSERT INTO item (codigo, titulo, tipo, autor, edicao, disponivel) VALUES
    ('L001', 'Dom Casmurro',                       'livro',   'Machado de Assis', NULL,  FALSE),
    ('L002', 'O Cortiço',                          'livro',   'Aluísio Azevedo',  NULL,  TRUE),
    ('R001', 'Superinteressante',                  'revista', NULL,               'Ed. 400', TRUE),
    ('R002', 'National Geographic Brasil',         'revista', NULL,               'Ed. 250', FALSE);

-- ---- Usuários (1 aluno + 1 professor) ----
INSERT INTO usuario (nome, tipo, limite_itens) VALUES
    ('Maria Silva', 'aluno',     3),
    ('João Pereira', 'professor', 5);

-- ---- Empréstimos ----
-- Empréstimo 1: em aberto (Maria pegou o livro L001, ainda não devolveu)
INSERT INTO emprestimo (item_id, usuario_id, data_retirada, data_devolucao_prevista, data_devolucao, valor_multa)
VALUES (
    (SELECT id FROM item WHERE codigo = 'L001'),
    (SELECT id FROM usuario WHERE nome = 'Maria Silva'),
    '2026-08-20',
    '2026-08-20'::date + INTERVAL '14 days',
    NULL,
    0.00
);

-- Empréstimo 2: já devolvido (João pegou a revista R002, devolveu com 3 dias de atraso)
-- multa = 3 dias x R$ 1,00/dia (regra de multa de revista) = R$ 3,00
INSERT INTO emprestimo (item_id, usuario_id, data_retirada, data_devolucao_prevista, data_devolucao, valor_multa)
VALUES (
    (SELECT id FROM item WHERE codigo = 'R002'),
    (SELECT id FROM usuario WHERE nome = 'João Pereira'),
    '2026-08-10',
    '2026-08-10'::date + INTERVAL '7 days',
    '2026-08-20',
    3.00
);
