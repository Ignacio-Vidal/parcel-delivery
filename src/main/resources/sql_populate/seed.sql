INSERT INTO users (uuid, name, email, password, created, role)
VALUES ('4c9c2a9f-ce78-40f7-8591-df60421d3d82', 'Ignacio', 'test@gmail.com', '123456', now(), 'DRIVER');
INSERT INTO users (uuid, name, email, password, created, role)
VALUES ('666c63ef-ece1-4abf-aea4-40fe3430920c', 'Nacho', 'test@gmail.com', '123456', now(), 'CUSTOMER');
INSERT INTO users (uuid, name, email, password, created, role)
VALUES ('9d719427-8119-4a94-9a74-5f5e4a73cdac', 'Inaki', 'test@gmail.com', '123456', now(), 'DRIVER');
INSERT INTO users (uuid, name, email, password, created, role)
VALUES ('9e4b1400-ab27-446f-a6c7-b6ad41148acc', 'Inazio', 'test@gmail.com', '123456', now(), 'CUSTOMER');


INSERT INTO parcels(uuid, pickup_address, destination_address, status, recipient_name)
VALUES ('f6833235-28ae-4e53-8dc3-db5ccad38dd6', 'Calle 1', 'Calle 2', 'REGISTERED', 'Ignacio');
INSERT INTO parcels(uuid, pickup_address, destination_address, status, recipient_name)
VALUES ('07e09b43-0b05-464d-b831-4c48fcc8e874', 'Calle 3', 'Calle 4', 'REGISTERED', 'Nacho');
INSERT INTO parcels(uuid, pickup_address, destination_address, status, recipient_name)
VALUES ('d6b0025c-b0b9-4f70-95bd-30b9cb462fd1', 'Calle 5', 'Calle 6', 'REGISTERED', 'Inaki');
INSERT INTO parcels(uuid, pickup_address, destination_address, status, recipient_name)
VALUES ('61b55ce3-b749-4a1f-b18d-fcaa7ae9bd73', 'Calle 7', 'Calle 8', 'REGISTERED', 'Inazio');

INSERT INTO parcel_owner(parcel_uuid, user_uuid)
VALUES ('f6833235-28ae-4e53-8dc3-db5ccad38dd6', '9d719427-8119-4a94-9a74-5f5e4a73cdac');
INSERT INTO parcel_owner(parcel_uuid, user_uuid)
VALUES ('07e09b43-0b05-464d-b831-4c48fcc8e874', '666c63ef-ece1-4abf-aea4-40fe3430920c');
INSERT INTO parcel_owner(parcel_uuid, user_uuid)
VALUES ('d6b0025c-b0b9-4f70-95bd-30b9cb462fd1', '666c63ef-ece1-4abf-aea4-40fe3430920c');
INSERT INTO parcel_owner(parcel_uuid, user_uuid)
VALUES ('61b55ce3-b749-4a1f-b18d-fcaa7ae9bd73', '9d719427-8119-4a94-9a74-5f5e4a73cdac');