drop table if exists `test`;
create table `test` (
    `id` bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    `password` varchar(50) comment '密码',
    primary key (`id`)
) engine=innodb default  charset=utf8mb4 comment='测试';

insert into `test` (id, name, password) VALUES (1, '测试', 'password');

# 电子书表
drop table if exists `ebook`;
create table `ebook` (
  `id` bigint not null comment 'id',
  `name` varchar(50) comment '名称',
  `category1_id` bigint comment '分类1',
  `category2_id` bigint comment '分类2',
  `description` varchar(200) comment '描述',
  `cover` varchar(200) comment '封面',
  `doc_count` int not null default 0 comment '文档数',
  `view_count` int not null default 0 comment '阅读数',
  `vote_count` int not null default 0 comment '点赞数',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='电子书';

insert into `ebook` (id, name, description) values (1, 'Spring Boot 入门教程', '零基础入门 Java 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (2, 'Vue 入门教程', '零基础入门 Vue 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (3, 'Python 入门教程', '零基础入门 Python 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (4, 'Mysql 入门教程', '零基础入门 Mysql 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (5, 'Oracle 入门教程', '零基础入门 Oracle 开发，企业级应用开发最佳首选框架');


# 定时任务表
drop table if exists `job_scheduler`;
create table `job_scheduler` (
  `job_id` varchar(200) not null comment '任务id',
  `job_name` varchar(50) comment '任务名称',
  `description` varchar(500) comment '任务描述',
  `cron_expression` varchar(20) not null comment 'cron表达式',
  `status` varchar(1) not null comment '状态' default 1,
  primary key (`job_id`)
) engine=innodb default charset=utf8mb4 comment='定时任务表';

insert into `job_scheduler` values ('123', 'com.avalon.wiki.job.DynamicJobTest', '动态定时任务1', '*/3 * * * * ?', '1');
insert into `job_scheduler` values ('456', 'com.avalon.wiki.job.DynamicJobTest2', '动态定时任务2', '*/5 * * * * ?', '1');
