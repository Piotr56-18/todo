create table todos (
    id int primary key auto_increment,
    text varchar(100) not null,
    done bit
);