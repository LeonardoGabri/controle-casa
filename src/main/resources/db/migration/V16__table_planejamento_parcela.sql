CREATE TABLE planejamento_parcelas
(
    id                  UUID PRIMARY KEY,
    responsavel_id      UUID NOT NULL REFERENCES responsavel (id),
    despesa_id          UUID REFERENCES despesa (id),
    porcentagem_divisao DECIMAL NOT NULL CHECK (porcentagem_divisao >= 0)
);