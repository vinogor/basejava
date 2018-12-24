CREATE TABLE resume
(
  uuid      VARCHAR(36) NOT NULL
    CONSTRAINT resume_pkey
      PRIMARY KEY,
  full_name TEXT        NOT NULL
);

ALTER TABLE resume
  OWNER TO postgres;



CREATE TABLE contact
(
  id          SERIAL      NOT NULL,
  resume_uuid VARCHAR(36) NOT NULL
    CONSTRAINT contact_resume_uuid_fkey
      REFERENCES resume
      ON DELETE CASCADE,
  type        TEXT        NOT NULL,
  value       TEXT        NOT NULL
);

ALTER TABLE contact
  OWNER TO postgres;

CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);



CREATE TABLE section
(
  id            SERIAL      NOT NULL,
  type_section  TEXT        NOT NULL,
  value_section TEXT        NOT NULL,
  resume_uuid   VARCHAR(36) NOT NULL
    CONSTRAINT section_resume_uuid_fk
      REFERENCES resume
      ON UPDATE RESTRICT ON DELETE CASCADE
);

ALTER TABLE section
  OWNER TO postgres;

CREATE UNIQUE INDEX section_uuid_type_index
  ON section (resume_uuid, type_section);




