BEGIN TRANSACTION;

CREATE TABLE Users
(
        user_id serial,
        username varchar(64) NOT NULL,
        pw varchar(64) NOT NULL,
        email varchar(64),

        constraint pk_Users primary key(user_id)
);

CREATE TABLE Decks
(
        deck_id serial,
        user_id int,
        deck_name varchar(64),

        constraint pk_decks primary key(deck_id),
        constraint fk_user_deck foreign key(user_id) references Users(user_id)
);

CREATE TABLE Decklist
(
        decklist_id serial,
        deck_id int,
   
        constraint fk_decklist_deck foreign key (deck_id) references Decks(deck_id),
        constraint pk_decklist primary key(decklist_id)
);
CREATE TABLE Deckcards
(
        decklist_id int,
        card_id int,
        card_quantity int,
        
        constraint fk_deckcards_decklist foreign key (decklist_id) references Decklist(decklist_id)
);

COMMIT TRANSACTION;