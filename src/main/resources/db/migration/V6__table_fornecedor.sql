CREATE TABLE fornecedor (
    id UUID PRIMARY KEY,
    nome VARCHAR(255),
    subgrupo_id UUID references subgrupo(id)
);