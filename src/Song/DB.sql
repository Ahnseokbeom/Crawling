create schema music;
create table top100(
    id int not null primary key auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(45) not null,
    album varchar(100) not null
);
create table genre(
	genre varchar(45) not null primary key
);
create table etc_ost(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table etc_classic(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table etc_jazz(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table etc_newage(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table etc_jpop(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table etc_wordmusic(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table etc_ccm(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table pop_pop(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table pop_rock(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table pop_electronica(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table pop_rap(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table pop_RnB(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table pop_fork(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table korea_ballade(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table korea_dance(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table korea_rap(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table korea_RnB(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table korea_indie(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table korea_rock(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table korea_trot(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
create table korea_fork(
	id int not null auto_increment,
    title varchar(100) not null,
    img varchar(255) not null,
    artist varchar(100) not null,
    album varchar(100) not null,
    genre varchar(45) not null,
    primary key (id, title),
    foreign key(genre) references genre(genre)
);
