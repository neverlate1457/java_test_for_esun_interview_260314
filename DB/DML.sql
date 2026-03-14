CREATE OR REPLACE FUNCTION sp_get_all_vote_items()
RETURNS TABLE(item_id INT, item_name VARCHAR, description VARCHAR, total_votes BIGINT) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        i.item_id, 
        i.item_name, 
        i.description, 
        COUNT(r.record_id)
    FROM Vote_Items i
    LEFT JOIN Vote_Records r ON i.item_id = r.item_id
    WHERE i.is_deleted = 0
    GROUP BY i.item_id, i.item_name, i.description;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE sp_cast_vote(p_item_id INT, p_voter_info VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM Vote_Items WHERE item_id = p_item_id AND is_deleted = 0) THEN
        INSERT INTO Vote_Records (item_id, voter_info)
        VALUES (p_item_id, p_voter_info);
    ELSE
        RAISE EXCEPTION 'Vote item % does not exist or is deleted.', p_item_id;
    END IF;
END;
$$;

CREATE OR REPLACE PROCEDURE sp_insert_vote_item(p_item_name VARCHAR, p_description VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO Vote_Items (item_name, description, is_deleted, created_at, updated_at)
    VALUES (p_item_name, p_description, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
END;
$$;
CREATE OR REPLACE PROCEDURE sp_delete_vote_item(p_item_id INT)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM Vote_Records WHERE item_id = p_item_id;
    DELETE FROM Vote_Items WHERE item_id = p_item_id;
END;
$$;
CREATE OR REPLACE PROCEDURE sp_update_vote_item(
    p_item_id INT,
    p_item_name VARCHAR,
    p_description VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE "vote_items" 
    SET item_name = p_item_name,
        description = p_description,
        updated_at = CURRENT_TIMESTAMP
    WHERE item_id = p_item_id AND is_deleted = 0;
END;
$$;