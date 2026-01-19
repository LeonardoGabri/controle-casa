ALTER TABLE responsavel
    ADD COLUMN titular BOOLEAN;

UPDATE responsavel
SET titular = false;