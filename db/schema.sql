# DB 생성
DROP DATABASE IF EXISTS sb_c_2022_1st;
CREATE DATABASE sb_c_2022_1st;
USE sb_c_2022_1st;

# 게시물(article) 테이블 생성
CREATE TABLE article (
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

# 회원(member) 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨 (3=일반, 7=관리자)', # 권한레벨 컬럼, COMMENT하고 ''안에 쓸 말을 적어 주석으로 달 수 있음, 3과 7이라는 양수만 사용할 것이므로 UNSIGNED 속성 추가 
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellphoneNo CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부 (0=탈퇴전, 1=탈퇴)', # 삭제상태 컬럼
    delDate DATETIME COMMENT '탈퇴날짜' # 탈퇴날짜 컬럼, 탈퇴를 해야만 생기는 컬럼이므로 탈퇴를 안하면 비어있을 수 있기 때문에 not null로 안해놓음
);

# 회원 테스트 데이터 생성 (관리자 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authLevel = 7,
`name` = '관리자',
nickname = '관리자',
cellphoneNo = '01011111111',
email = 'yuuuuuni930302@gmail.com';

# 회원 테스트 데이터 생성 (일반 회원)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '사용자1',
nickname = '사용자1',
cellphoneNo = '01011111111',
email = 'yuuuuuni930302@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = '사용자2',
nickname = '사용자2',
cellphoneNo = '01011111111',
email = 'yuuuuuni930302@gmail.com';

# 게시물(article) 테이블에 회원번호 컬럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;
SELECT * FROM article;

# 기존 게시물의 작성자를 2번 회원으로 지정
UPDATE article
SET memberId = 2
WHERE memberId = 0;

# 게시판(board) 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free1(자유게시판1), free2(자유게시판2), ...',
    `name` CHAR(50) NOT NULL  UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부 (0=삭제전, 1=삭제)', # 삭제상태 컬럼
    delDate DATETIME COMMENT '삭제날짜' # 삭제날짜 컬럼, 삭제를 해야만 생기는 컬럼이므로 삭제를 안하면 비어있을 수 있기 때문에 not null로 안해놓음
);

# 기본 게시판(board) 데이터 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유게시판';

# 게시물(article) 테이블에 boardId 컬럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

# 1, 2번 게시물(article)을 공지사항 게시물로 지정
UPDATE article
SET boardId = 1
WHERE id IN (1, 2);

# 3번 게시물(article)을 자유게시판 게시물로 지정
UPDATE article
SET boardId = 2
WHERE id IN (3);