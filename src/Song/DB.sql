create schema music;
create table top100(
	id int not null primary key,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(45) not null,
    album varchar(100) not null
);
create table koreamusic(
	id int not null,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(45) not null,
	album varchar(100) not null,
    genre varchar(45) not null,
    primary key(id,title)
);
create table popmusic(
	id int not null,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(45) not null,
	album varchar(100) not null,
    genre varchar(45) not null,
    primary key(id,title)
);
create table etcmusic(
	id int not null,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(45) not null,
	album varchar(100) not null,
    genre varchar(45) not null,
    primary key(id,title)
);
