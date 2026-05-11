ALTER TABLE refresh_tokens
ADD CONSTRAINT fk_refresh_tokens_user
FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE refresh_tokens
ADD CONSTRAINT fk_refresh_tokens_tenant
FOREIGN KEY (tenant_id)
REFERENCES tenants(id);
