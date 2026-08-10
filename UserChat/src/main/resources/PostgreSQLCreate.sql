CREATE DATABASE seek_friend_user_chat;
-- 这里需要自己找方法切换数据库
drop table if exists "chat_room";
CREATE TABLE "chat_room" (
                        "room_id" bigint NOT NULL,
                        "first_user_id" bigint NOT NULL,
                        "second_user_id" bigint NOT NULL,
                        "lastest_chat_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        "able_chat" boolean NOT NULL DEFAULT true,
                        PRIMARY KEY ("room_id")
);
COMMENT ON TABLE "chat_room" IS '聊天室表';
COMMENT ON COLUMN "chat_room"."room_id" IS '该聊天室的id';
COMMENT ON COLUMN "chat_room"."first_user_id" IS '用户1的id';
COMMENT ON COLUMN "chat_room"."second_user_id" IS '用户2的id';
COMMENT ON COLUMN "chat_room"."lastest_chat_time" IS '最新聊天时间';
COMMENT ON COLUMN "chat_room"."create_time" IS '创建时间';
COMMENT ON COLUMN "chat_room"."able_chat" IS '是否可以聊天,即是否两人为好友关系';
-- 普通索引（pg CREATE INDEX 放在表外面）
CREATE INDEX "user_index1" ON "chat_room"(first_user_id);
CREATE INDEX "user_index2" ON "chat_room"(second_user_id);
CREATE INDEX "chat_time_index" ON "chat_room"(able_chat,lastest_chat_time);




drop table if exists "chat_record";
CREATE TABLE "chat_record" (
                             "record_id" bigint NOT NULL,
                             "room_id" bigint NOT NULL,
                             "user_id" bigint NOT NULL,
                             "description" varchar(500) NOT NULL,
                             "image_addr" varchar(50) unique,
                             "able_withdraw_time" timestamp NOT NULL,
                             "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             "is_withdraw" boolean not null default false,
                             PRIMARY KEY ("record_id")
);
COMMENT ON TABLE "chat_record" IS '聊天记录表';
COMMENT ON COLUMN "chat_record"."record_id" IS '该聊天记录的id';
COMMENT ON COLUMN "chat_record"."room_id" IS '该聊天记录的聊天室id';
COMMENT ON COLUMN "chat_record"."user_id" IS '发送该聊天记录的用户id';
COMMENT ON COLUMN "chat_record"."description" IS '该聊天的内容';
COMMENT ON COLUMN "chat_record"."image_addr" IS '可能附带的图片的地址';
COMMENT ON COLUMN "chat_record"."able_withdraw_time" IS '可以撤回的时间';
COMMENT ON COLUMN "chat_record"."create_time" IS '创建时间';
COMMENT ON COLUMN "chat_record"."is_withdraw" IS '是否可以撤回';
-- 普通索引（pg CREATE INDEX 放在表外面）
CREATE INDEX "room_index" ON "chat_record"(room_id);
CREATE INDEX "user_index" ON "chat_record"(user_id);




























