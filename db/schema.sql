# DB 생성
DROP DATABASE IF EXISTS sb_c_2022_1st;
CREATE DATABASE sb_c_2022_1st;
USE sb_c_2022_1st;

# 게시물(article) 테이블 생성
CREATE TABLE article
(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL, # 등록일자 컬럼 추가
    updateDate DATETIME NOT NULL, # 수정일자 컬럼 추가
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 1',
`body` = '내용 1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 2',
`body` = '내용 2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 3',
`body` = '내용 3';