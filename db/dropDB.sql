--useage psql -u postgres -f /path/to/file
\c deckbop;
DROP TABLE card;
DROP TABLE deck;
DROP TABLE user_account;
\c postgres;
DROP DATABASE deckbop;
\q;