
CREATE DATABASE seek_friend_ai_friend;
-- 这里需要自己找方法切换数据库
drop table if exists "ai_friend";
CREATE TABLE "ai_friend" (
                             "ai_friend_id" bigint NOT NULL,
                             "user_id" bigint NOT NULL,
                             "name" varchar(20) not null ,
                             "description" varchar(300),
                             "hobbies" varchar(300) ,
                             "characteristic" varchar(300) ,
                             "encounter_reason" varchar(300) ,
                             "like_score" int not null default 10,
                             "character_history" varchar(2100) ,
                             "header_image_addr" varchar(50) unique ,
                             "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             "is_complete" boolean NOT NULL DEFAULT false,
                             "is_delete" boolean NOT NULL DEFAULT false,
                             PRIMARY KEY ("ai_friend_id")
);
COMMENT ON TABLE "ai_friend" IS 'ai好友表';
COMMENT ON COLUMN "ai_friend"."ai_friend_id" IS '该ai好友的id';
COMMENT ON COLUMN "ai_friend"."user_id" IS '所属用户的id';
COMMENT ON COLUMN "ai_friend"."name" IS 'ai的名称';
COMMENT ON COLUMN "ai_friend"."description" IS '该ai的描述';
COMMENT ON COLUMN "ai_friend"."hobbies" IS '该ai的爱好';
COMMENT ON COLUMN "ai_friend"."characteristic" IS '特点，个性等等';
COMMENT ON COLUMN "ai_friend"."like_score" IS '对你的喜爱分数';
COMMENT ON COLUMN "ai_friend"."header_image_addr" IS '头像地址';
COMMENT ON COLUMN "ai_friend"."character_history" IS 'ai角色的历史背景，由ai自己生成';
COMMENT ON COLUMN "ai_friend"."create_time" IS '创建时间';
COMMENT ON COLUMN "ai_friend"."is_complete" IS '是否完成创建';
COMMENT ON COLUMN "ai_friend"."is_delete" IS '是否被删除';
-- 普通索引（pg CREATE INDEX 放在表外面）
CREATE INDEX "user_index" ON "ai_friend"(user_id);
CREATE INDEX "complete_index" ON "ai_friend"(is_complete);
CREATE INDEX "delete_index" ON "ai_friend"(is_delete);































