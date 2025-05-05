CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    tenant_id UUID NOT NULL,
    FOREIGN KEY (tenant_id) REFERENCES tenant(id)
);