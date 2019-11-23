-- Project Name : みんなのクイズ手帳
-- Date/Time    : 2019/11/23 21:54:50
-- Author       : c5apple
-- RDBMS Type   : MySQL
-- Application  : A5:SQL Mk-2

-- 通報
drop table if exists t_ban_report cascade;

create table t_ban_report (
  report_id INT(10) not null AUTO_INCREMENT comment '通報ID'
  , report_target ENUM("ACCOUNT", "QUIZ") not null comment '通報対象'
  , reason ENUM("DISLIKE", "SPAM", "ADULT", "DISCRIMINATION", "OTHER") not null comment '理由'
  , account_id INT(10) comment 'アカウントID'
  , quiz_id BIGINT comment 'クイズID'
  , read_flag TINYINT(1) not null comment '既読フラグ'
  , done_flag TINYINT(1) not null comment '処理済みフラグ'
  , memo VARCHAR(256) comment 'メモ'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_ban_report_PKC primary key (report_id)
) comment '通報' ;

-- 課金ログ
drop table if exists t_charge_log cascade;

create table t_charge_log (
  charge_log_id BIGINT not null AUTO_INCREMENT comment '課金ログID'
  , account_id INT(10) not null comment 'アカウントID'
  , charge INT(10) not null comment '課金額'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_charge_log_PKC primary key (charge_log_id)
) comment '課金ログ' ;

-- フォロー
drop table if exists t_follow cascade;

create table t_follow (
  follow_id BIGINT not null AUTO_INCREMENT comment 'フォローID'
  , account_id INT(10) not null comment 'アカウントID'
  , follow_account_id INT(10) not null comment 'フォローアカウントID'
  , block_flag TINYINT(1) default 0 comment 'ブロックフラグ'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_follow_PKC primary key (follow_id)
) comment 'フォロー' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- グループメンバー
drop table if exists t_group_member cascade;

create table t_group_member (
  group_member_id BIGINT(10) not null AUTO_INCREMENT comment 'グループメンバーID'
  , group_id INT(10) not null comment 'グループID'
  , account_id INT(10) not null comment 'アカウントID'
  , blocked TINYINT(1) default 0 comment 'ブロックフラグ'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_group_member_PKC primary key (group_member_id)
) comment 'グループメンバー' ;

-- クイズいいね
drop table if exists t_quiz_like cascade;

create table t_quiz_like (
  quiz_like_id BIGINT not null AUTO_INCREMENT comment 'クイズいいねID'
  , quiz_id BIGINT not null comment 'クイズID'
  , account_id INT(10) not null comment 'アカウントID'
  , liked TINYINT(1) not null comment 'いいねフラグ'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_quiz_like_PKC primary key (quiz_like_id)
) comment 'クイズいいね' ;

-- クイズタグ
drop table if exists t_quiz_tag cascade;

create table t_quiz_tag (
  quiz_tag_id BIGINT not null AUTO_INCREMENT comment 'タグID'
  , quiz_id BIGINT not null comment 'クイズID'
  , name VARCHAR(50) not null comment 'タグ名'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_quiz_tag_PKC primary key (quiz_tag_id)
) comment 'クイズタグ' ;

alter table t_quiz_tag add unique name (name) ;

-- アカウント
drop table if exists t_account cascade;

create table t_account (
  account_id INT(10) not null AUTO_INCREMENT comment 'アカウントID'
  , login_id VARCHAR(30) not null comment 'ログインID'
  , name VARCHAR(30) not null comment 'アカウント名'
  , password VARCHAR(512) not null comment 'パスワード'
  , password_change_date DATETIME not null comment 'パスワード変更日時'
  , mail VARCHAR(256) comment 'メールアドレス'
  , description VARCHAR(120) comment '自己紹介'
  , img_url VARCHAR(256) comment '画像パス'
  , twitter VARCHAR(30) comment 'Twitterアカウント'
  , facebook VARCHAR(30) comment 'Facebookアカウント'
  , line VARCHAR(30) comment 'LINEアカウント'
  , charged TINYINT(1) comment '課金フラグ'
  , follow_count INT(10) default 0 comment 'フォロー件数'
  , follower_count INT(10) default 0 comment 'フォローワー件数'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_account_PKC primary key (account_id)
) comment 'アカウント' ;

alter table t_account add unique login_id (login_id) ;

-- グループ
drop table if exists t_group cascade;

create table t_group (
  group_id INT(10) not null AUTO_INCREMENT comment 'グループID'
  , name VARCHAR(30) not null comment 'グループ名'
  , account_id INT(10) not null comment '管理者アカウントID'
  , official TINYINT(1) comment '公式フラグ'
  , description VARCHAR(120) comment '自己紹介'
  , img_url VARCHAR(256) comment '画像URL'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_group_PKC primary key (group_id)
) comment 'グループ' ;

alter table t_group add unique name (name) ;

-- クイズ
drop table if exists t_quiz cascade;

create table t_quiz (
  quiz_id BIGINT not null AUTO_INCREMENT comment 'クイズID'
  , account_id INT(10) not null comment 'アカウントID'
  , question VARCHAR(200) not null comment '問題'
  , answer VARCHAR(50) not null comment '答え'
  , hint VARCHAR(50) comment 'ヒント'
  , explanation VARCHAR(100) comment '解説'
  , sound_url VARCHAR(256) comment '音声パス'
  , scope_type ENUM("PRIVATE", "GROUP", "PUBLIC") comment '公開種別'
  , like_count INT(10) default 0 comment 'いいね件数'
  , deleted VARCHAR(1) default '0' not null comment '削除フラグ'
  , created_at DATETIME default CURRENT_TIMESTAMP not null comment '作成日時'
  , created_by VARCHAR(30) not null comment '作成者'
  , updated_at DATETIME default CURRENT_TIMESTAMP not null comment '更新日時'
  , updated_by VARCHAR(30) not null comment '更新者'
  , constraint t_quiz_PKC primary key (quiz_id)
) comment 'クイズ' ;

