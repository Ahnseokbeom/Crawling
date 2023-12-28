create schema music;
create table top100(
    id int not null,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    primary key(id, title)
);
create table music_type(
    id int not null primary key auto_increment,
    type varchar(10) not null 
);
create table genre(
    id int not null primary key auto_increment,
    genre varchar(45) not null 
);
create table music(
    id int not null primary key auto_increment,
    ranking bigint not null,
    title varchar(255) not null,
    img varchar(255) not null,
    artist varchar(255) not null,
    album varchar(255) not null,
    type_id int,
    genre_id int,
    foreign key(genre_id) references genre(id),
    foreign key(type_id) references music_type(id)
);
