-- Table: public.request_log

-- DROP TABLE IF EXISTS public.request_log;

CREATE TABLE IF NOT EXISTS request_log
(
    id               character varying(255)                              NOT NULL,
    request_number_a double precision                                    NOT NULL,
    request_number_b double precision                                    NOT NULL,
    result           double precision                                    NOT NULL,
    created_at       timestamp with time zone                            NOT NULL,
    CONSTRAINT request_log_pkey PRIMARY KEY (id)
)

