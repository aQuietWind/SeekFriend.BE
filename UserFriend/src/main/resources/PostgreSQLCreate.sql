CREATE DATABASE seek_friend_userfriend;
-- 这里需要自己找方法切换数据库
drop table if exists "user_friend_connection";
CREATE TABLE "user_friend_connection" (
                        "connection_id" bigint NOT NULL,
                        "applicant_user_id" bigint NOT NULL,
                        "respondent_user_id" bigint NOT NULL,
                        "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        "is_accept" boolean,
                        "is_delete" boolean NOT NULL DEFAULT false,
                        PRIMARY KEY ("connection_id")
);
COMMENT ON TABLE "user_friend_connection" IS '用户好友关系表';
COMMENT ON COLUMN "user_friend_connection"."connection_id" IS '该关系的id';
COMMENT ON COLUMN "user_friend_connection"."applicant_user_id" IS '发出该申请的用户id';
COMMENT ON COLUMN "user_friend_connection"."respondent_user_id" IS '响应请求的用户id';
COMMENT ON COLUMN "user_friend_connection"."create_time" IS '创建时间';
COMMENT ON COLUMN "user_friend_connection"."is_accept" IS '是否被接受';
COMMENT ON COLUMN "user_friend_connection"."is_delete" IS '是否被删除';
-- 普通索引（pg CREATE INDEX 放在表外面）
CREATE INDEX "apply_index" ON "user_friend_connection"(applicant_user_id);
CREATE INDEX "respond_index" ON "user_friend_connection"(respondent_user_id);































