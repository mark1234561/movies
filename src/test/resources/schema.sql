create table "movie" (
     "id" bigint auto_increment,
     "api_name" varchar(255),
     "title" varchar(255),
     "year" varchar(255),
     primary key ("id")
);

create table "movie_entity_director" (
     "movie_entity_id" bigint not null,
     "director" varchar(255)
);

create table "query" (
     "id" bigint auto_increment,
     "api_name" varchar(255) not null,
     "query_" varchar(255) not null,
     primary key ("id")
);


alter table "query"
    add constraint "UKi6qmwp1hr96d58g9nvhcq14n8" unique ("query_", "api_name");

alter table "movie_entity_director"
    add constraint "FKradfeejhy6gm4bs0i355b3637"
        foreign key ("movie_entity_id")
            references "movie";

