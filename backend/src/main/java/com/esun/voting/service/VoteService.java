package com.esun.voting.service;
import java.util.List;
import java.util.ArrayList;

import com.esun.voting.dto.VoteItemDTO;
import com.esun.voting.dto.VoteRequest;

import com.esun.voting.repository.VoteRepository;
import com.esun.voting.entity.VoteItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public List<VoteItemDTO> getAllItems() {
        List<Object[]> results = voteRepository.callGetAllVoteItems();
        List<VoteItemDTO> dtoList = new ArrayList<>();
        for (Object[] row : results) {
            VoteItemDTO dto = new VoteItemDTO();
            dto.setItemId((Integer) row[0]);
            dto.setItemName((String) row[1]);
            dto.setDescription((String) row[2]);
            dto.setTotalVotes(((Number) row[3]).longValue());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void processMultipleVotes(VoteRequest request) {
        if (request.getItemIds() != null) {
            for (Integer itemId : request.getItemIds()) {
                voteRepository.callCastVote(itemId, request.getVoterInfo());
            }
        }
    }
    
    @Transactional
    public void addItem(VoteItemDTO dto) {
        voteRepository.callInsertVoteItem(dto.getItemName(), dto.getDescription());
    }
    @Transactional
    public void deleteItem(Integer itemId) {
    voteRepository.callDeleteVoteItem(itemId);
    }
    @Transactional
    public void updateItem(Integer id, VoteItemEntity item) {
        
        voteRepository.updateVoteItem(id, item.getItemName(), item.getDescription());
}
}