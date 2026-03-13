package com.esun.voting.controller;

import com.esun.voting.dto.VoteItemDTO;   // 確保這行存在
import com.esun.voting.dto.VoteRequest;   // 確保這行存在
import com.esun.voting.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/votes") // 符合 RESTful 風格的命名
@CrossOrigin(origins = "*")    // 允許前端 Vue.js 跨域存取
public class VoteController {

    @Autowired
    private VoteService voteService;

    /**
     * 功能 1：顯示所有投票項目 (後台/前台共用)
     */
    @GetMapping("/items")
    public ResponseEntity<?> getAllVoteItems() {
        return ResponseEntity.ok(voteService.getAllItems());
    }

    /**
     * 功能 1：新增投票項目 (後台)
     */
    @PostMapping("/items")
    public ResponseEntity<?> createVoteItem(@RequestBody VoteItemDTO itemDto) {
        voteService.addItem(itemDto);
        return ResponseEntity.ok("項目新增成功");
    }

    /**
     * 功能 2：使用者投票 (多選)
     * 使用者傳送一組 ID 列表進行投票
     */
    @PostMapping("/submit")
    public ResponseEntity<?> submitVote(@RequestBody VoteRequest request) {
        // 呼叫 Service 並由 Service 實作 @Transactional
        voteService.processMultipleVotes(request);
        return ResponseEntity.ok("投票已成功送出");
    }
    /**
     * 功能：刪除投票項目 (後台)
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteVoteItem(@PathVariable("id") Integer id) {
        try {
            System.out.println("準備刪除項目 ID: " + id); // 可以在這裡加個 Log 確認 Java 有收到 ID
            voteService.deleteItem(id);
            return ResponseEntity.ok().body("{\"message\": \"刪除成功\"}"); // 回傳 JSON 格式更穩定
        } catch (Exception e) {
            e.printStackTrace(); // 在 IntelliJ 控制台印出完整報錯，這很重要
            return ResponseEntity.status(500).body("刪除失敗：" + e.getMessage());
        }
    }
}