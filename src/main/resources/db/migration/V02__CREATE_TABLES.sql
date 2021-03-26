CREATE TABLE IF NOT EXISTS TB_USER
(
    ID BIGINT NOT NULL DEFAULT nextval('USUARIO_ID_SEQ'),
    NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    CONFIRM_EMAIL VARCHAR(255) NOT NULL,
    PHONE VARCHAR(20) NOT NULL,
    PASSWORD VARCHAR(550) NOT NULL,
    CONFIRM_PASSWORD VARCHAR(550) NOT NULL,
    TYPE_USER VARCHAR(2) NOT NULL,
    CREATED_AT DATE DEFAULT NOW(),
    UPDATED_AT DATE,
    UNIQUE(EMAIL),

    CONSTRAINT pk_user primary key (ID)
);

CREATE TABLE IF NOT EXISTS TB_ROLE
(
    ID BIGINT NOT NULL DEFAULT nextval('ROLE_ID_SEQ'),
    ROLE_DESC VARCHAR(255) NOT NULL,

    CONSTRAINT pk_role primary key (ID)
);

CREATE TABLE IF NOT EXISTS TB_USER_ROLE
(
    ID_USER BIGINT NOT NULL,
    ID_ROLE VARCHAR (50) NOT NULL,

    FOREIGN KEY (ID_USER) REFERENCES TB_USER(ID),
    FOREIGN KEY (ID_ROLE) REFERENCES TB_ROLE(ID)
);

CREATE TABLE IF NOT EXISTS TB_BAND
(
    ID BIGINT NOT NULL DEFAULT nextval('BAND_ID_SEQ'),
    NAME VARCHAR(255) NOT NULL,
    CREATED_AT DATE DEFAULT NOW(),
    UPDATED_AT DATE,

    CONSTRAINT pk_band primary key (ID)
);

CREATE TABLE  IF NOT EXISTS TB_USER_BAND
(
    ID BIGINT NOT NULL DEFAULT nextval('BAND_USER_ID_SEQ'),
    ID_USER BIGINT NOT NULL,
    ID_BAND BIGINT NOT NULL,
    LEADER VARCHAR(1) NOT NULL DEFAULT 'N',

    CONSTRAINT pk_user_band primary key (ID),
    FOREIGN KEY (ID_USER) REFERENCES TB_USER(ID),
    FOREIGN KEY (ID_BAND) REFERENCES TB_BAND(ID)
);