package com.esun.voting.repository;

import com.esun.voting.entity.VoteItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;    
import org.springframework.data.jpa.repository.Query;     
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.data.jpa.repository.query.Procedure;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<VoteItemEntity, Integer> {

    // 1. 呼叫 FUNCTION
    @Query(value = "SELECT * FROM sp_get_all_vote_items()", nativeQuery = true)
    List<Object[]> callGetAllVoteItems();

    // 2. 呼叫 PROCEDURE
    @Transactional
    @Modifying
    @Query(value = "CALL sp_cast_vote(:p_item_id, :p_voter_info)", nativeQuery = true)
    void callCastVote(@Param("p_item_id") Integer itemId, @Param("p_voter_info") String voterInfo);

    // 3. 新增項目
    @Transactional
    @Modifying
    @Query(value = "CALL sp_insert_vote_item(:p_item_name, :p_description)", nativeQuery = true)
    void callInsertVoteItem(@Param("p_item_name") String itemName, @Param("p_description") String description);
    // 3. 刪除項目
    @Transactional
    @Modifying
    @Query(value = "CALL sp_delete_vote_item(CAST(:p_item_id AS INTEGER))", nativeQuery = true)
    void callDeleteVoteItem(@Param("p_item_id") Integer itemId);

    @Procedure(procedureName = "sp_update_vote_item")
    void updateVoteItem(
    @Param("p_item_id") Integer id, 
    @Param("p_item_name") String name, 
    @Param("p_description") String description
    );

}