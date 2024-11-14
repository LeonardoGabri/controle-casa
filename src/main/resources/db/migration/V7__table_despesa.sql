CREATE TABLE despesa (
    id UUID PRIMARY KEY,
    conta_id UUID NOT NULL REFERENCES conta(id),
    fornecedor_id UUID REFERENCES fornecedor(id),
    subgrupo_id UUID REFERENCES subgrupo(id),
    data_lancamento date not null,
    n_parcelas int NOT NULL CHECK (n_parcelas >= 0),
    valor_total DECIMAL NOT NULL CHECK (valor_total >= 0),
    valor_total_ativo DECIMAL CHECK (valor_total_ativo >= 0),
    mes_inicio_cobranca INTEGER CHECK (mes_inicio_cobranca BETWEEN 1 AND 12),
    ano_inicio_cobranca INTEGER,
    situacao VARCHAR(50) NOT NULL
);