INSERT INTO addresses(address, district, city, postcode, country) VALUES ('9336 Civic Center Dr.', 'Beverly Hills','California', '90210', 'USA');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('498 7th Ave.', 'New York','New York', '10018', 'USA');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('Gruvbacken 4', '','Stockholm', '11634', 'Sweden');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('Drury House, 34-43 Russell Street', '','London', 'WC2B 5HA', 'UK');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('12-20 Lexington Street', '','London', 'W1F 0LE', 'UK');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('916 West Burbank Bouvlevard', 'Burbank','California', '91506', 'USA');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('485 West 46th St.', 'Hells Kitchen','New York', '10019', 'USA');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('Nedre Åkargata 67 A', '','Gävle', '80251', 'Sweden');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('Klostergatan 67', '','Jönköping', '55337', 'Sweden');
INSERT INTO addresses(address, district, city, postcode, country) VALUES ('RINGGATAN 67', '','Uppsala', '75217', 'Sweden');

INSERT INTO games(game_name) VALUES ('Battleship');
INSERT INTO games(game_name) VALUES ('Mario Kart');
INSERT INTO games(game_name) VALUES ('Street Fighter');
INSERT INTO games(game_name) VALUES ('Asphalt 9');

INSERT INTO staff(staff_first_name, staff_last_name, staff_nick_name, address_id, staff_email) VALUES ("Benjamin", "Portsmouth", "Ben", 8, "b@p.com");
INSERT INTO staff(staff_first_name, staff_last_name, staff_nick_name, address_id, staff_email) VALUES ("Christoffer", "Frisk", "Chris", 8, "c@p.com");
INSERT INTO staff(staff_first_name, staff_last_name, staff_nick_name, address_id, staff_email) VALUES ("Niklas", "Einarsson", "Niklas", 8, "n@p.com");
INSERT INTO staff(staff_first_name, staff_last_name, staff_nick_name, address_id, staff_email) VALUES ("Lynsey", "Fox", "Lynsey", 8, "l@p.com");



INSERT INTO teams(team_name, game_id) VALUES ("Ninjas in Pyjamas",1);
INSERT INTO teams(team_name, game_id) VALUES ("Team Liquid", 1);
INSERT INTO teams(team_name, game_id) VALUES ("Fnatic", 2);
INSERT INTO teams(team_name, game_id) VALUES ("G2 Esports", 2);
INSERT INTO teams(team_name, game_id) VALUES ("Cloud9",3);
INSERT INTO teams(team_name, game_id) VALUES ("Natus Vincere",3);
INSERT INTO teams(team_name, game_id) VALUES ("FaZe Clan", 4);
INSERT INTO teams(team_name, game_id) VALUES ("Evil Geniuses", 4);

update players set team_id = 1 where player_id in (1,2);
update players set team_id = 2 where player_id in (3,4);
update players set team_id = 3 where player_id in (5,6);
update players set team_id = 4 where player_id in (7,8);


INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id, game_id, team_id,email) VALUES ('Harrison', 'Ford', 'Harry', 1, 1,1,"harry@hford.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,  game_id, team_id,email) VALUES ('Michael J', 'Fox', 'Mike',2,1,1, "MJ@MFox.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name,address_id, game_id, team_id,email) VALUES('Alexander', 'Skarsgård', 'Alex',3,2,3,"Alex@skarsgard.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id,email) VALUES ('Katherine', 'Winslet', 'Kate',4, 3,5,"Kate@Winslet.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id,email) VALUES ('Kiera', 'Knightly', 'Kiera',5,4,7, "kk@knightley.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id, game_id,team_id,email) VALUES ('Johnny', 'Depp', 'Jon', 6,2,3,"jonny@jd.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id,email) VALUES ('Jessica', 'Jones', 'JJ',7,4,7,"jj@jones.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id, team_id,email) VALUES ('Deborah', 'Sam', 'Deb',1,4,8,"d@sam.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id,email) VALUES ('Oskar', 'Isaksson', 'Osk',3,4,8,"osk@isaksson.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id, email) VALUES ('Lukas', 'Isaksson', 'Lukie',3,1,2,"lukas@isaksson.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id, email) VALUES ('Madelene', 'Hall', 'Maddie',9,1,2,"m@hall.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id, email) VALUES ('Sebastian', 'Hall', 'Sebbe',9,2,4,"s@hall.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id, email) VALUES ('Dante', 'Eriksson', 'Dante',10,2,4,"d@e.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id, email) VALUES ('Carola', 'Eriksson', 'Carola',10,3,5,"c@e.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id, email) VALUES ('Geoffrey', 'Wrenn', 'Jeff',10,3,6,"g@Wrenn.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,team_id,email) VALUES ('Doris', 'Liden', 'Dor',10,3,6,"d@liden.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id,email) VALUES ('Stina', 'Samsson', 'Stina',8,3,"s@samsson.com");
INSERT INTO players(player_first_name, player_last_name, player_nick_name, address_id,game_id, email) VALUES ('Folke', 'Dragotinovic', 'Folke',8,1,"folke@drago.com");



