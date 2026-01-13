CREATE TABLE transacao (
    id UUID PRIMARY KEY,
    data_inicio date not null,
    patrimonio_id UUID NOT NULL REFERENCES patrimonio(id),
    tipo VARCHAR(20) CHECK (tipo IN ('ENTRADA', 'SAIDA', 'TRANSFERENCIA')),
    valor DECIMAL NOT NULL CHECK (valor >= 0),
    descricao VARCHAR(150),
    patrimonio_origem UUID REFERENCES patrimonio(id),
    patrimonio_destino UUID REFERENCES patrimonio(id)
);