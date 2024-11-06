CREATE TABLE banco (
    id UUID PRIMARY KEY,
    nome VARCHAR(50),
    numero VARCHAR(50)
);

INSERT INTO banco (id, nome, numero)
VALUES
    ('d5a3f5d2-7f26-4d7a-8c93-1b97b0ae857d', 'Banco do Brasil', '001'),
    ('d903557a-6e92-41b2-8b89-43d76e547271', 'Itaú', '341'),
    ('53cf2170-d5a0-4898-b7c0-b5e766d93fa9', 'Bradesco', '237'),
    ('a8b05454-e3aa-401f-baaf-bda1e30e3ddb', 'Santander', '033'),
    ('db001a62-ab83-4897-9941-8d22a5b86eea', 'Nubank', '260'),
    ('3cf53322-e196-4c90-ad57-90de34662a53', 'C6 Bank', '336');