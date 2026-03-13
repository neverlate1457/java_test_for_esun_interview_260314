-- 1. 建立投票項目表
CREATE TABLE Vote_Items (
    item_id     SERIAL PRIMARY KEY,
    item_name   VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    is_deleted  SMALLINT DEFAULT 0, -- 0:正常, 1:刪除
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PostgreSQL 的註解需獨立撰寫
COMMENT ON COLUMN Vote_Items.item_id IS '項目唯一識別碼';
COMMENT ON COLUMN Vote_Items.is_deleted IS '軟刪除標記(0:正常, 1:刪除)';

-- 2. 建立投票紀錄表
CREATE TABLE Vote_Records (
    record_id   SERIAL PRIMARY KEY,
    item_id     INT NOT NULL,
    voter_info  VARCHAR(100),
    vote_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_vote_item FOREIGN KEY (item_id) REFERENCES Vote_Items(item_id)
);

-- 建立索引
CREATE INDEX idx_vote_item_id ON Vote_Records(item_id);