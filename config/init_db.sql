create table resume
(
  uuid      char(36) not null
    constraint resume_pkey
    primary key,
  full_name text     not null
);

alter table resume
  owner to postgres;

create table contact
(
  id          serial   not null
    constraint contact_pkey
    primary key,
  type        text     not null,
  value       text     not null,
  resume_uuid char(36) not null
    constraint contact_resume_uuid_fk
    references resume
    on delete cascade
);

alter table contact
  owner to postgres;

CREATE INDEX contact_uuid_type_index
  ON public.contact (resume_uuid, type);