ALTER TABLE "public"."portal_menu"
    ADD COLUMN "open_type" varchar(255);

ALTER TABLE "public"."portal_menu"
    ADD COLUMN "display_order" int4;
