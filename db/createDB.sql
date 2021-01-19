CREATE DATABASE deckbop;

CREATE TABLE user_account
(
        user_id serial,
        username varchar(64) NOT NULL,
        pw varchar(64) NOT NULL,
        email varchar(64),

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

COMMIT TRANSACTION;