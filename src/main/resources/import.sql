INSERT INTO addresses(address, district, city, postcode, country) VALUES ('9336 Civic Center Dr.', 'Beverly Hills','California', '90210', 'USA');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('498 7th Ave.', 'New York','New York', '10018', 'USA');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('Gruvbacken 4', '','Stockholm', '11634', 'Sweden');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('Drury House, 34-43 Russell Street', '','London', 'WC2B 5HA', 'UK');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('12-20 Lexington Street', '','London', 'W1F 0LE', 'UK');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('916 West Burbank Bouvlevard', 'Burbank','California', '91506', 'USA');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('485 West 46th St.', 'Hells Kitchen','New York', '10019', 'USA');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('Nedre Åkargata 67 A', '','Gävle', '80251', 'Sweden');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('Klostergatan 67', '','Jönköping', '55337', 'Sweden');



INSERT INTO staff(staff_first_name, staff_last_name, staff_nick_name, address_id, staff_email) VALUES ("Benjamin", "Portsmouth", "Ben", 8, "b@p.com");
INSERT INTO staff(staff_first_name, staff_last_name, staff_nick_name, address_id, staff_email) VALUES ("Christoffer", "Frisk", "Chris", 8, "c@p.com");
INSERT INTO staff(staff_first_name, staff_last_name, staff_nick_name, address_id, staff_email) VALUES ("Niklas", "Einarsson", "Niklas", 8, "n@p.com");
INSERT INTO staff(staff_first_name, staff_last_name, staff_nick_name, address_id, staff_email) VALUES ("Lynsey", "Fox", "Lynsey", 8, "l@p.com");


INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Harrison', 'Ford', 'Harry', 1);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Michael J', 'Fox', 'Mike',2);
INSERT INTO players(player_first_name, player_last_name, player_nick_name,address_id) VALUES('Alexander', 'Skarsgård', 'Alex',3);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Katherine', 'Winslet', 'Kate',4);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Kiera', 'Knightly', 'Kiera',5);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Johnny', 'Depp', 'Jon', 6);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Jessica', 'Jones', 'JJ',7);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Deborah', 'Sam', 'Deb',1);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Oskar', 'Isaksson', 'Oskar',3);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Lukas', 'Isaksson', 'Lukas',3);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Madelene', 'Hall', 'Madde',9);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Sebastian', 'Hall', 'Sebbe',9);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Dante', 'Eriksson', 'Dante',9);
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id) VALUES ('Carola', 'Eriksson', 'Carola',9);

INSERT INTO games(game_name) VALUES ('Battleship');
INSERT INTO games(game_name) VALUES ('Mario Kart');
INSERT INTO games(game_name) VALUES ('Street Fighter');
INSERT INTO games(game_name) VALUES ('Asphalt 9');

INSERT INTO teams(team_name, game_id) VALUES ("Ninjas in Pyjamas",1);
INSERT INTO teams(team_name, game_id) VALUES ("Team Liquid", 1);
INSERT INTO teams(team_name, game_id) VALUES ("Fnatic", 2);
INSERT INTO teams(team_name, game_id) VALUES ("G2 Esports", 2);
INSERT INTO teams(team_name, game_id) VALUES ("Cloud9",3);
INSERT INTO teams(team_name, game_id) VALUES ("Natus Vincere",3);
INSERT INTO teams(team_name, game_id) VALUES ("FaZe Clan", 4);
INSERT INTO teams(team_name, game_id) VALUES ("Evil Geniuses", 4);


