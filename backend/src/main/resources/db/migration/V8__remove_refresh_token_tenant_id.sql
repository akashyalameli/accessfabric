ALTER TABLE refresh_tokens
DROP COLUMN tenant_id;
/* This automatically drops CONSTRAINT fk_refresh_tokens_tenant too */
