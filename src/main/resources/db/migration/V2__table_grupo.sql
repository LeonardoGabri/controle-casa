CREATE TABLE grupo (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) UNIQUE,
    valor_total DECIMAL,
    valor_total_ativo DECIMAL
);