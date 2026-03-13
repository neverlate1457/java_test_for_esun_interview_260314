package com.esun.voting.dto;
import java.util.List;

public class VoteRequest {
    private List<Integer> itemIds;
    private String voterInfo;

    public List<Integer> getItemIds() { return itemIds; }
    public void setItemIds(List<Integer> itemIds) { this.itemIds = itemIds; }
    public String getVoterInfo() { return voterInfo; }
    public void setVoterInfo(String voterInfo) { this.voterInfo = voterInfo; }
}