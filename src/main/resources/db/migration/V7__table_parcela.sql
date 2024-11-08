CREATE TABLE parcela (
    id UUID PRIMARY KEY,
    responsavel_id UUID NOT NULL REFERENCES responsavel(id),
    despesa_id UUID NOT NULL REFERENCES despesa(id),
    data_vencimento DATE NOT NULL,
    valor DECIMAL NOT NULL CHECK (valor >= 0),
    porcentagem_divisao DECIMAL NOT NULL CHECK (valor >= 0),
    situacao VARCHAR(50) NOT NULL
);