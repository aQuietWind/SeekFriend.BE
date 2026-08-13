CREATE DATABASE seek_friend_ai_chat;
-- 这里需要自己找方法切换数据库
drop table if exists "chat_room";
CREATE TABLE "chat_room" (
                        "ai_friend_id" bigint NOT NULL,
                        "user_id" bigint NOT NULL,
                        "lastest_chat_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        "able_chat" boolean NOT NULL DEFAULT true,
                        PRIMARY KEY ("able_chat")
);
COMMENT ON TABLE "chat_room" IS '聊天室表';
COMMENT ON COLUMN "chat_room"."ai_friend_id" IS '该ai好友的id';
COMMENT ON COLUMN "chat_room"."user_id" IS '用户的id';
COMMENT ON COLUMN "chat_room"."lastest_chat_time" IS '最新聊天时间';
COMMENT ON COLUMN "chat_room"."create_time" IS '创建时间';
COMMENT ON COLUMN "chat_room"."able_chat" IS '是否可以聊天,即是否两人为好友关系';
-- 普通索引（pg CREATE INDEX 放在表外面）
CREATE INDEX "user_index" ON "chat_room"(user_id);
CREATE INDEX "chat_time_index" ON "chat_room"(able_chat,lastest_chat_time);




drop table if exists "chat_record";
CREATE TABLE "chat_record" (
                             "record_id" bigint NOT NULL,
                             "ai_friend_id" bigint NOT NULL,
                             "user_id" bigint NOT NULL,
                             "description" varchar(500) NOT NULL,
                             "file_addr" varchar(50) unique,
                             "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             "is_ai" boolean NOT NULL,
                             PRIMARY KEY ("record_id")
);
COMMENT ON TABLE "chat_record" IS '聊天记录表';
COMMENT ON COLUMN "chat_record"."record_id" IS '该聊天记录的id';
COMMENT ON COLUMN "chat_record"."ai_friend_id" IS '该聊天记录对应的ai好友id';
COMMENT ON COLUMN "chat_record"."user_id" IS '发送该聊天记录的用户id';
COMMENT ON COLUMN "chat_record"."description" IS '该聊天的内容';
COMMENT ON COLUMN "chat_record"."file_addr" IS '可能附带的文件的地址';
COMMENT ON COLUMN "chat_record"."create_time" IS '创建时间';
COMMENT ON COLUMN "chat_record"."is_ai" IS '该聊天记录的发送者身份是否是ai';
-- 普通索引（pg CREATE INDEX 放在表外面）
CREATE INDEX "room_index" ON "chat_record"(ai_friend_id);
CREATE INDEX "record_user_index" ON "chat_record"(user_id);




























