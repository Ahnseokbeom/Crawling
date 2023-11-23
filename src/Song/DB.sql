create schema music;
create table top100(
	id int not null,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    primary key(id, title)
);
create table genre(
	id int not null primary key auto_increment,
    genre varchar(45) not null
);
create table korea_music(
	id int not null,
    title varchar(255) not null,
    img varchar(255) not null,
    artist varchar(255) not null,
    album varchar(255) not null,
    genre_id int,
    foreign key(genre_id) references genre(id),
    primary key(id, title,genre_id)
);
create table pop_music(
	id int not null,
    title varchar(255) not null,
    img varchar(255) not null,
    artist varchar(255) not null,
    album varchar(255) not null,
    genre_id int,
    foreign key(genre_id) references genre(id),
    primary key(id, title,genre_id)
);
