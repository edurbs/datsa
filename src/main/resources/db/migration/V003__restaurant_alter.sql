ALTER Table restaurant ADD COLUMN `active` boolean NOT NULL;
UPDATE restaurant SET `active` = true;