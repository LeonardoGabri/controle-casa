CREATE TABLE subgrupo (
    id UUID PRIMARY KEY,
    nome varchar(255) NOT NULL,
    grupo_id UUID REFERENCES grupo(id)
);

