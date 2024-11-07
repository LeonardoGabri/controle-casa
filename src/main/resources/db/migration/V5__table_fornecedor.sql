CREATE TABLE fornecedor (
    id UUID PRIMARY KEY,
    nome VARCHAR(255),
    grupo_id UUID references grupo(id),
    valor_total DECIMAL,
    valor_total_ativo DECIMAL
);

