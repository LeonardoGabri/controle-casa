CREATE TABLE criptomoeda
(
    id      UUID PRIMARY KEY,
    simbolo VARCHAR(20) CHECK (simbolo IN
                               ('BTC', 'ETH', 'USDT', 'USDT', 'USDC', 'BNB', 'XRP', 'ADA', 'SOL', 'DOGE', 'MATIC')),
    nome    VARCHAR(150),
    valor   DECIMAL NOT NULL CHECK (valor >= 0)
);

INSERT INTO criptomoeda (id, simbolo, nome, valor)
VALUES ('e03e9655-6d06-4958-8519-959b25d74ca5', 'BTC', 'Bitcoin', 0),
       ('ba491b25-198a-47a4-a0b4-24b0803876c3', 'ETH', 'Ethereum', 0),
       ('1b5e53d7-b417-4329-8760-599a8e81ff16', 'USDT', 'Tether', 0),
       ('ef50aec2-efb8-4c20-8361-f25fae3a26bb', 'USDC', 'USD Coin', 0),
       ('7c997891-645b-4fb6-9b5a-6f8429041419', 'BNB', 'Binance Coin', 0),
       ('e979526c-140e-4be4-9f5f-27fca87c9bce', 'SOL', 'Solana', 0),
       ('3d9f8e2a-1b5c-4d6e-9f0a-2b3c4d5e6f7a', 'XRP', 'Ripple', 0);
