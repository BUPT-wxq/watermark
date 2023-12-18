create table if NOT EXISTS user_info(
    uid VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255),
    department VARCHAR(255) NOT NULL
);


CREATE TABLE if NOT EXISTS wmtemplate (
                            templateId INT AUTO_INCREMENT PRIMARY KEY,
                            uid VARCHAR(255),
                            targetFingerprint varchar(1024),
                            content VARCHAR(255),
                            fontColor VARCHAR(255),
                            fontSize INT,
                            frameSize INT,
                            alpha DOUBLE,
                            angle INT,
                            key1 VARCHAR(255) NOT NULL
);