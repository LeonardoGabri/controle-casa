ALTER TABLE conta
DROP
CONSTRAINT conta_tipo_check;

ALTER TABLE conta
    ADD CONSTRAINT conta_tipo_check
        CHECK (tipo IN ('CONTA_CORRENTE', 'CREDITO', 'INVESTIMENTO', 'BENEFICIO'));