CREATE TABLE conta (
    id UUID PRIMARY KEY,
    banco_id UUID not null references banco(id),
    valor_total DECIMAL,
    valor_total_ativo DECIMAL,
    responsavel_id UUID not null references responsavel(id)
);

