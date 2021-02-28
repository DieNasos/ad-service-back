CREATE TABLE categories (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (255),
    reqname VARCHAR (255),
    deleted BOOLEAN,
    UNIQUE (name),
    UNIQUE (reqname)
);

CREATE TABLE banners (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (255),
    price DECIMAL (8,2),
    category_id INTEGER,
    content TEXT,
    deleted BOOLEAN,
    FOREIGN KEY (category_id) REFERENCES categories (id),
    UNIQUE (name)
);

CREATE TABLE requests (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    banner_id INTEGER,
    user_agent TEXT,
    ip_address VARCHAR (255),
    date DATE,
    FOREIGN KEY (banner_id) REFERENCES banners (id)
);