-- DROP INDEX uniq_setting_index;
-- CREATE UNIQUE INDEX uniq_setting_index ON settings USING btree ( "id", "version", "name", "slug", "description",  "thumbnail");
-- INSERT INTO settings ( "id", "version", "name", "slug", "description",  "thumbnail" )
-- VALUES
-- ( 40, 1, 'script-setting-1', 'script-setting-1', 'script-setting-1', 'script-setting-1.png' )
-- ON CONFLICT ( "id", "version", "name", "slug", "description",  "thumbnail" ) DO
--     UPDATE SET description = EXCLUDED.description || ';' || settings.description;


INSERT INTO settings ("id", "version", "name", "slug", "description", "thumbnail")
VALUES
(1, 0, 'script-setting-1', 'script-setting-1', 'script-setting-1', 'script-setting-1.png'),
(2, 0, 'script-setting-2', 'script-setting-2', 'script-setting-2', 'script-setting-2.png'),
(3, 0, 'script-setting-3', 'script-setting-3', 'script-setting-3', 'script-setting-3.png')
ON CONFLICT ON CONSTRAINT settings_pkey DO
    UPDATE SET description = EXCLUDED.description || '; Record ' || settings.id || ' updated at ' || NOW();


INSERT INTO setting_items ("id", "version", "name", "slug", "description", "thumbnail", "setting")
VALUES
(1, 1, 'script-setting-item-1', 'script-setting-item-1', 'script-setting-item-1', 'script-setting-item-1.png', 1),
(2, 1, 'script-setting-item-2', 'script-setting-item-2', 'script-setting-item-2', 'script-setting-item-2.png', 1),
(3, 1, 'script-setting-item-3', 'script-setting-item-3', 'script-setting-item-3', 'script-setting-item-3.png', 1),
(4, 1, 'script-setting-item-4', 'script-setting-item-4', 'script-setting-item-4', 'script-setting-item-4.png', 2),
(5, 1, 'script-setting-item-5', 'script-setting-item-5', 'script-setting-item-5', 'script-setting-item-5.png', 2),
(6, 1, 'script-setting-item-6', 'script-setting-item-6', 'script-setting-item-6', 'script-setting-item-6.png', 2),
(7, 1, 'script-setting-item-7', 'script-setting-item-7', 'script-setting-item-7', 'script-setting-item-7.png', 2)
ON CONFLICT ON CONSTRAINT setting_items_pkey DO
    UPDATE SET description = EXCLUDED.description || '; Record ' || setting_items.id || ' updated at ' || NOW();

-- List Index And Filter Index ...
-- SELECT * FROM pg_class CL JOIN pg_namespace NS ON NS.oid = CL.relnamespace
-- WHERE NS.nspname = 'public' AND CL.relname LIKE '%setting%' ORDER BY CL.relname;
--
-- SELECT
--     t.relname as table_name,
--     i.relname as index_name,
--     a.attname as column_name
-- FROM
--     pg_class t,
--     pg_class i,
--     pg_index ix,
--     pg_attribute a
-- WHERE
--   t.oid = ix.indrelid
--   AND i.oid = ix.indexrelid
--   AND a.attrelid = t.oid
--   AND a.attnum = ANY(ix.indkey)
--   AND t.relkind = 'r'
--   AND t.relname LIKE 'setting%'
-- ORDER BY t.relname, i.relname;

-- INSERT INTO settings ("id", "version", "name", "slug", "description", "thumbnail")
-- VALUES
-- (1, 0, 'script-setting-1', 'script-setting-1', 'script-setting-1', 'script-setting-1.png'),
-- (2, 0, 'script-setting-2', 'script-setting-2', 'script-setting-2', 'script-setting-2.png')
-- ON CONFLICT ON CONSTRAINT settings_pkey DO NOTHING;
--
-- INSERT INTO setting_items ("id", "version", "name", "slug", "description", "thumbnail", "setting") VALUES
-- (1, 1, 'script-setting-item-1', 'script-setting-item-1', 'script-setting-item-1', 'script-setting-item-1.png', 1),
-- (2, 1, 'script-setting-item-2', 'script-setting-item-2', 'script-setting-item-2', 'script-setting-item-2.png', 1),
-- (3, 1, 'script-setting-item-3', 'script-setting-item-3', 'script-setting-item-3', 'script-setting-item-3.png', 1),
-- (4, 1, 'script-setting-item-4', 'script-setting-item-4', 'script-setting-item-4', 'script-setting-item-4.png', 2),
-- (5, 1, 'script-setting-item-5', 'script-setting-item-5', 'script-setting-item-5', 'script-setting-item-5.png', 2),
-- (6, 1, 'script-setting-item-6', 'script-setting-item-6', 'script-setting-item-6', 'script-setting-item-6.png', 2),
-- (7, 1, 'script-setting-item-7', 'script-setting-item-7', 'script-setting-item-7', 'script-setting-item-7.png', 2)
-- ON CONFLICT ON CONSTRAINT setting_items_pkey DO NOTHING;