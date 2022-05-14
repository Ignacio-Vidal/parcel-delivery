CREATE TABLE users
(
    id       BIGSERIAL NOT NULL PRIMARY KEY,
    uuid     TEXT      NOT NULL UNIQUE,
    name     TEXT      NOT NULL,
    email    TEXT      NOT NULL,
    password TEXT      NOT NULL,
    created  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    role     TEXT      NOT NULL
);

CREATE TABLE parcels
(
    id                  BIGSERIAL NOT NULL PRIMARY KEY,
    uuid                TEXT      NOT NULL UNIQUE,
    pickup_address      TEXT      NOT NULL,
    destination_address TEXT      NOT NULL,
    status              TEXT      NOT NULL,
    recipient_name      TEXT      NOT NULL
);

CREATE TABLE parcel_owner
(
    id          BIGSERIAL NOT NULL,
    parcel_uuid TEXT      NOT NULL,
    user_uuid   TEXT      NOT NULL,
    FOREIGN KEY (parcel_uuid) REFERENCES parcels (uuid),
    FOREIGN KEY (user_uuid) REFERENCES users (uuid)
);

CREATE TABLE parcel_driver
(
    id          BIGSERIAL NOT NULL,
    parcel_uuid TEXT      NOT NULL,
    user_uuid   TEXT      NOT NULL,
    FOREIGN KEY (parcel_uuid) REFERENCES parcels (uuid),
    FOREIGN KEY (user_uuid) REFERENCES users (uuid)
);



