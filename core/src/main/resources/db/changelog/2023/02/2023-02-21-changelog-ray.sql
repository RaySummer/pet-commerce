CREATE TABLE "public"."configuration"
(
    "id"           BIGINT GENERATED BY DEFAULT AS IDENTITY primary key,
    "uid"          uuid                                        NOT NULL DEFAULT uuid_generate_v4(),
    "created_time" timestamptz(6) NOT NULL,
    "created_by"   varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
    "updated_time" timestamptz(6),
    "updated_by"   varchar(100) COLLATE "pg_catalog"."default",
    "deleted_time" timestamptz(6),
    "deleted_by"   varchar(100) COLLATE "pg_catalog"."default",
    "key"          varchar(255)                                NOT NULL,
    "value"        varchar(255),
    "description"  varchar(255),
    "platform"     varchar(255)
);

CREATE
INDEX "index_configuration_id" ON "public"."configuration" (
  "id",
  "key"
);

-- media 表
CREATE TABLE "public"."media"
(
    "id"           BIGINT GENERATED BY DEFAULT AS IDENTITY primary key,
    "created_time" timestamptz(6) NOT NULL,
    "created_by"   varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "updated_time" timestamptz(6),
    "updated_by"   varchar(255) COLLATE "pg_catalog"."default",
    "deleted_time" timestamptz(6),
    "deleted_by"   varchar(255) COLLATE "pg_catalog"."default",
    "uid"          uuid                                        NOT NULL DEFAULT uuid_generate_v4(),
    "type"         varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "original"     varchar(255) COLLATE "pg_catalog"."default",
    "reduce"       varchar(255) COLLATE "pg_catalog"."default",
    "thumbnail"    varchar(255) COLLATE "pg_catalog"."default",
    "size"         int8,
    "blog_id"      int8                                        NOT NULL,
    "product_id"   int8                                        NOT NULL,
    "user_id"      int8                                        NOT NULL,
    "member_id"    int8                                        NOT NULL
);

CREATE
INDEX "index_media_id" ON "public"."media" (
  "id",
  "uid",
  "type",
  "blog_id",
  "product_id",
  "user_id",
  "member_id"
);