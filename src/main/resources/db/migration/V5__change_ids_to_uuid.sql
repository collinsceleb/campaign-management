-- Drop existing tables
DROP TABLE IF EXISTS payments CASCADE;
DROP TABLE IF EXISTS campaign_locations CASCADE;
DROP TABLE IF EXISTS campaigns CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS campaign_location CASCADE;
DROP TABLE IF EXISTS campaign_status CASCADE;
DROP TABLE IF EXISTS verifications CASCADE;

-- Recreate tables with UUID

CREATE TABLE campaign_status (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

CREATE TABLE campaign_location (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    status_id UUID,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_campaign_location_status FOREIGN KEY (status_id) REFERENCES campaign_status(id)
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    tries INTEGER NOT NULL DEFAULT 0,
    last_login TIMESTAMPTZ,
    email_status VARCHAR(255) NOT NULL,
    status_id UUID,
    -- Embedded BaseStatus
    created_by UUID,
    updated_by UUID,
    status VARCHAR(255) NOT NULL,
    status_changed_at TIMESTAMPTZ,
    status_changed_by UUID,
    status_change_reason TEXT,
    version BIGINT,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_users_status FOREIGN KEY (status_id) REFERENCES campaign_status(id),
    CONSTRAINT chk_users_tries CHECK (tries >= 0)
);

CREATE INDEX idx_users_status ON users(status_id);
CREATE INDEX idx_users_email ON users(email);

CREATE TABLE campaigns (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    "from" TIMESTAMPTZ,
    "to" TIMESTAMPTZ,
    status_id UUID,
    owner_id UUID,
    amount NUMERIC(10, 2) NOT NULL,
    banners JSONB,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_campaigns_status FOREIGN KEY (status_id) REFERENCES campaign_status(id),
    CONSTRAINT fk_campaigns_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE campaign_locations (
    campaign_id UUID NOT NULL,
    location_id UUID NOT NULL,
    CONSTRAINT fk_campaign_locations_campaign_id FOREIGN KEY (campaign_id) REFERENCES campaigns(id),
    CONSTRAINT fk_campaign_locations_location_id FOREIGN KEY (location_id) REFERENCES campaign_location(id)
);

CREATE TABLE payments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    campaign_id UUID,
    user_id UUID,
    reference VARCHAR(255) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    currency VARCHAR(255) NOT NULL DEFAULT 'USD',
    status_id UUID,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_payments_campaign FOREIGN KEY (campaign_id) REFERENCES campaigns(id),
    CONSTRAINT fk_payments_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_payments_status FOREIGN KEY (status_id) REFERENCES campaign_status(id)
);

CREATE TABLE verifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL,
    passcode VARCHAR(6) NOT NULL,
    tries INTEGER NOT NULL DEFAULT 0,
    expires_at TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    CONSTRAINT chk_verifications_tries CHECK (tries >= 0)
);
