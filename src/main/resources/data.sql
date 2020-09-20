-- Goal: 新增 網頁功能資料
-- Create: Hevin 08/23
CREATE TABLE web_features (
id BIGINT NOT NULL AUTO_INCREMENT,
NAME VARCHAR(50) NOT NULL ,
detail VARCHAR(65535)
src VARCHAR(65535),
create_Date DATE,
update_date DATE,
PRIMARY KEY(`id`)
)

INSERT INTO web_features(NAME,create_Date)
VALUES("簡介",NOW());

INSERT INTO web_features(NAME,create_Date)
VALUES("會員資料",NOW());

INSERT INTO web_features(NAME,create_Date)
VALUES("留言板",NOW());
-- Goal:
-- Create: