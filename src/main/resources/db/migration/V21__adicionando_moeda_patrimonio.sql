ALTER TABLE patrimonio
    ADD COLUMN moeda VARCHAR(20)
        CHECK (moeda IN ('BTC', 'ETH', 'USDT', 'USDT', 'USDC', 'BNB', 'XRP', 'ADA', 'SOL', 'DOGE', 'MATIC'));