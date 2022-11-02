CREATE TABLE IF NOT EXISTS todo_list(
	id			int 			NOT NULL	auto_increment,
    name		varchar(60)		NOT NULL,
    description	varchar(255)	NULL,
    user		varchar(60) 	NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS items(
	id			  int 			NOT NULL	auto_increment,
    name		  varchar(60)	NOT NULL,
    finished	  boolean		NOT NULL default false,
    created_at    datetime      NOT	NULL default current_timestamp,
    list_id		  int			NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (list_id) REFERENCES todo_list(id) ON DELETE CASCADE
);