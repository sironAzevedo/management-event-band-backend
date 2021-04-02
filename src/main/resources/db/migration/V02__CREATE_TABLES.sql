CREATE TABLE IF NOT EXISTS TB_USER
(
    ID BIGINT NOT NULL DEFAULT nextval('USER_ID_SEQ'),
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
    ID_ROLE BIGINT NOT NULL,

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

CREATE TABLE IF NOT EXISTS TB_GROUP_INSTRUMENT_MUSICAL
(
    ID BIGINT NOT NULL DEFAULT nextval('GROUP_INSTRUMENT_ID_SEQ'),
    NAME VARCHAR(255) NOT NULL,

    CONSTRAINT pk_group_instruments primary key (ID)
);

CREATE TABLE IF NOT EXISTS TB_INSTRUMENT_MUSICAL
(
    ID BIGINT NOT NULL DEFAULT nextval('INSTRUMENTS_ID_SEQ'),
    NAME VARCHAR(255) NOT NULL,
    ID_GROUP BIGINT,

    CONSTRAINT pk_instruments primary key (ID),
    FOREIGN KEY (ID_GROUP) REFERENCES TB_GROUP_INSTRUMENT_MUSICAL(ID)
);

CREATE TABLE IF NOT EXISTS TB_USER_INSTRUMENT
(
    ID_USER BIGINT NOT NULL,
    ID_INSTRUMENT BIGINT NOT NULL,

    FOREIGN KEY (ID_USER) REFERENCES TB_USER(ID),
    FOREIGN KEY (ID_INSTRUMENT) REFERENCES TB_INSTRUMENT_MUSICAL(ID)
);

CREATE TABLE IF NOT EXISTS TB_VOICE
(
    ID BIGINT NOT NULL DEFAULT nextval('VOICE_ID_SEQ'),
    NAME VARCHAR(255) NOT NULL,

    CONSTRAINT pk_voice primary key (ID)
);

CREATE TABLE IF NOT EXISTS TB_USER_VOICE
(
    ID_USER BIGINT NOT NULL,
    ID_VOICE BIGINT NOT NULL,

    FOREIGN KEY (ID_USER) REFERENCES TB_USER(ID),
    FOREIGN KEY (ID_VOICE) REFERENCES TB_VOICE(ID)
);

CREATE TABLE IF NOT EXISTS TB_BAND_INFO
(
    ID_USER BIGINT NOT NULL,
    ID_BAND BIGINT NOT NULL,
    LEADER VARCHAR(1) NOT NULL DEFAULT 'N',
    ID_INSTRUMENT BIGINT,
    ID_VOICE BIGINT,
	
    CONSTRAINT pk_band_info primary key (ID_USER, ID_BAND),
    
    FOREIGN KEY (ID_USER) REFERENCES TB_USER(ID),
    FOREIGN KEY (ID_BAND) REFERENCES TB_BAND(ID),
    FOREIGN KEY (ID_INSTRUMENT) REFERENCES TB_INSTRUMENT_MUSICAL(ID),
    FOREIGN KEY (ID_VOICE) REFERENCES TB_VOICE(ID)
);

CREATE TABLE IF NOT EXISTS TB_PJ_CHAVE
(
    
    ID_USER BIGINT NOT NULL,
    CHAVE VARCHAR(255) NOT NULL,
    
    CONSTRAINT pk_pj_chave primary key (ID_USER),
    FOREIGN KEY (ID_USER) REFERENCES TB_USER(ID)
);

CREATE TABLE IF NOT EXISTS TB_PJ_ASSOCIATED_USER
(
    
    CHAVE VARCHAR(255) NOT NULL,
    ID_USER BIGINT,
    
    CONSTRAINT pk_pj_associated primary key (CHAVE, ID_USER),
    FOREIGN KEY (ID_USER) REFERENCES TB_USER(ID)
);

CREATE TABLE IF NOT EXISTS TB_PJ_ASSOCIATED_BAND
(
    
    CHAVE VARCHAR(255) NOT NULL,
    ID_BAND BIGINT,
    
    CONSTRAINT pk_pj_associated primary key (CHAVE, ID_BAND),
    FOREIGN KEY (ID_BAND) REFERENCES TB_BAND(ID)
);


