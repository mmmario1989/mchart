-- auto-generated definition
create table t_chatter
(
  id       int auto_increment
    primary key,
  account  varchar(50)                        not null,
  password varchar(50)                        not null,
  nickname varchar(20)                        null,
  created  datetime default CURRENT_TIMESTAMP null,
  modified datetime default CURRENT_TIMESTAMP null,
  constraint uk_account
  unique (account)
);

-- auto-generated definition
create table t_group
(
  id       int auto_increment
    primary key,
  name     varchar(50)                        not null,
  notice   varchar(512)                       null,
  created  datetime default CURRENT_TIMESTAMP null,
  modified datetime default CURRENT_TIMESTAMP null
  on update CURRENT_TIMESTAMP,
  admin    varchar(50)                        null
);

-- auto-generated definition
create table t_group_chatter
(
  group_id   int default '0'                    not null,
  chatter_id int default '0'                    not null,
  created    datetime default CURRENT_TIMESTAMP null,
  modified   datetime default CURRENT_TIMESTAMP null,
  primary key (group_id, chatter_id),
  constraint fk_chatterid
  foreign key (chatter_id) references t_chatter (id)
    on update cascade
    on delete cascade,
  constraint fk_groupid
  foreign key (group_id) references t_group (id)
    on update cascade
    on delete cascade
);

