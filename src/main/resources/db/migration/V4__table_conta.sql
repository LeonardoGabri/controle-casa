CREATE TABLE conta (
    id UUID PRIMARY KEY,
    banco_id UUID not null references banco(id),
    responsavel_id UUID not null references responsavel(id),
    CONSTRAINT unique_responsavel_banco UNIQUE (banco_id, responsavel_id)
);

