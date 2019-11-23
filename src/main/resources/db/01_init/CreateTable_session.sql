-- セッション
drop table if exists t_session cascade;

create table t_session (
  primary_id CHAR(36) NOT NULL
  , session_id CHAR(36) not null comment 'セッションID'
  , creation_time BIGINT(20) not null
  , last_access_time BIGINT(20) not null
  , max_inactive_interval INT(11) not null
  , expiry_time BIGINT(20) not null
  , principal_name VARCHAR(20) default NULL
  , constraint t_session_PKC primary key (primary_id)
) comment 'セッション' ;

create index expiry_time
  on t_session(expiry_time);
create index principal_name
  on t_session(principal_name);

-- セッションアトリビュート
drop table if exists t_session_ATTRIBUTES cascade;

create table t_session_ATTRIBUTES (
  session_primary_id CHAR(36) not null comment 'セッションID'
  , attribute_name VARCHAR(200) not null
  , attribute_bytes BLOB
  , constraint t_session_attributes_PKC primary key (session_primary_id,attribute_name)
  , constraint session_attributes_fk foreign key (session_primary_id) references t_session (primary_id) ON DELETE CASCADE
) comment 'セッションアトリビュート' ;
