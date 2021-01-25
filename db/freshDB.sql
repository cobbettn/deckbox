-- kill sessions
\c postgres;
SELECT
	pg_terminate_backend(pg_stat_activity.pid)
FROM
	pg_stat_activity
WHERE
	pg_stat_activity.datname = 'deckbop'
	AND pid <> pg_backend_pid();

-- drop db
\c deckbop;
DROP TABLE card;
DROP TABLE deck;
DROP TABLE user_account;
\c postgres;
DROP DATABASE deckbop;

-- re-create
CREATE DATABASE deckbop;
\c deckbop;
CREATE TYPE user_role AS ENUM ('USER', 'ADMIN');
CREATE TABLE user_account
(
        user_id serial,
        username varchar(64) NOT NULL UNIQUE,
        pw varchar(64) NOT NULL,
        email varchar(64) NOT NULL UNIQUE,
        account_role user_role NOT NULL,
        is_activated boolean NOT NULL,

        constraint pk_User primary key(user_id)
);

CREATE TABLE deck
(
        deck_id serial,
        user_id int,
        deck_name varchar(64),

        constraint pk_Deck primary key(deck_id),
        constraint fk_User_Deck foreign key(user_id) references user_account(user_id)
);

CREATE TABLE card
(
        deck_id int,
        card_id varchar(255),
        card_quantity int,
        
        constraint fk_Card_Deck foreign key (deck_id) references deck(deck_id)
);
