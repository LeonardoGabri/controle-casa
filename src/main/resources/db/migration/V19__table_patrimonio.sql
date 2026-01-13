CREATE TABLE patrimonio (
    id UUID PRIMARY KEY,
    conta_id UUID NOT NULL REFERENCES conta(id),
    tipo VARCHAR(20) CHECK (tipo IN ('CDB', 'RDB', 'CRIPTO', 'POUPANCA', 'PREVIDENCIA', 'PREVIDENCIA_PRIVADA', 'TESOURO_DIRETO', 'ACAO', 'FII' )),
    valor DECIMAL NOT NULL CHECK (valor >= 0),
    data_inicio date,
    data_fim date
);