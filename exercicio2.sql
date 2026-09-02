CREATE TABLE item (
    id          SERIAL PRIMARY KEY,
    codigo      VARCHAR(20) NOT NULL UNIQUE,
    titulo      VARCHAR(200) NOT NULL,
    tipo        VARCHAR(10) NOT NULL CHECK (tipo IN ('livro', 'revista')),
    autor       VARCHAR(150),
    edicao      VARCHAR(50),
    disponivel  BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE usuario (
    id             SERIAL PRIMARY KEY,
    nome           VARCHAR(150) NOT NULL,
    tipo           VARCHAR(10) NOT NULL CHECK (tipo IN ('aluno', 'professor')),
    limite_itens   INTEGER NOT NULL CHECK (limite_itens > 0)
);

CREATE TABLE emprestimo (
    id                        SERIAL PRIMARY KEY,
    item_id                   INTEGER NOT NULL REFERENCES item(id),
    usuario_id                INTEGER NOT NULL REFERENCES usuario(id),
    data_retirada             DATE NOT NULL,
    data_devolucao_prevista   DATE NOT NULL,
    data_devolucao            DATE,
    valor_multa               NUMERIC(10,2) NOT NULL DEFAULT 0.00
);


INSERT INTO item (codigo, titulo, tipo, autor, edicao, disponivel) VALUES
    ('L001', 'Dom Casmurro',                       'livro',   'Machado de Assis', NULL,  FALSE),
    ('L002', 'O Cortiço',                          'livro',   'Aluísio Azevedo',  NULL,  TRUE),
    ('R001', 'Superinteressante',                  'revista', NULL,               'Ed. 400', TRUE),
    ('R002', 'National Geographic Brasil',         'revista', NULL,               'Ed. 250', FALSE);

INSERT INTO usuario (nome, tipo, limite_itens) VALUES
    ('aluna1', 'aluno',     3),
    ('professor1', 'professor', 5);

INSERT INTO emprestimo (item_id, usuario_id, data_retirada, data_devolucao_prevista, data_devolucao, valor_multa)
VALUES (
    (SELECT id FROM item WHERE codigo = 'L001'),
    (SELECT id FROM usuario WHERE nome = 'aluna1'),
    '2026-08-20',
    '2026-08-20'::date + INTERVAL '14 days',
    NULL,
    0.00
);
INSERT INTO emprestimo (item_id, usuario_id, data_retirada, data_devolucao_prevista, data_devolucao, valor_multa)
VALUES (
    (SELECT id FROM item WHERE codigo = 'R002'),
    (SELECT id FROM usuario WHERE nome = 'professor1'),
    '2026-08-10',
    '2026-08-10'::date + INTERVAL '7 days',
    '2026-08-20',
    3.00
);
