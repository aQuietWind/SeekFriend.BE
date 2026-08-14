CREATE DATABASE seek_friend_user;
-- 这里需要自己找方法切换数据库
drop table if exists "user";
CREATE TABLE "user" (
                        "user_id" bigint NOT NULL,
                        "username" varchar(20) NOT NULL,
                        "phone_number" varchar(30) NOT NULL UNIQUE,
                        "password" varchar(20) NOT NULL,
                        "sex" smallint ,
                        "header_image_addr" varchar(50) UNIQUE,
                        "birthday" date ,
                        "user_friend_amount" int NOT NULL DEFAULT 0,
                        "ai_friend_amount" int NOT NULL DEFAULT 0,
                        "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        "is_delete" boolean NOT NULL DEFAULT false,
                        PRIMARY KEY ("user_id")
);
COMMENT ON TABLE "user" IS '用户表';
COMMENT ON COLUMN "user"."user_id" IS '用户id';
COMMENT ON COLUMN "user"."username" IS '用户名';
COMMENT ON COLUMN "user"."phone_number" IS '手机号';
COMMENT ON COLUMN "user"."password" IS '密码';
COMMENT ON COLUMN "user"."sex" IS '性别';
COMMENT ON COLUMN "user"."header_image_addr" IS '头像地址';
COMMENT ON COLUMN "user"."birthday" IS '生日';
COMMENT ON COLUMN "user"."user_friend_amount" IS '正常的用户朋友量';
COMMENT ON COLUMN "user"."ai_friend_amount" IS 'AI朋友量';
COMMENT ON COLUMN "user"."create_time" IS '创建时间';
COMMENT ON COLUMN "user"."is_delete" IS '是否被删除';
-- 普通索引（pg CREATE INDEX 放在表外面）
CREATE INDEX "phone_index" ON "user"(phone_number);































