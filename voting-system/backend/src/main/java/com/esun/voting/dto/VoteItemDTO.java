package com.esun.voting.dto;

public class VoteItemDTO {
    private Integer itemId;
    private String itemName;
    private String description;
    private Long totalVotes;

    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getTotalVotes() { return totalVotes; }
    public void setTotalVotes(Long totalVotes) { this.totalVotes = totalVotes; }
}