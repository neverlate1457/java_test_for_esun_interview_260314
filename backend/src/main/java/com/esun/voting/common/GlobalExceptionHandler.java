package com.esun.voting.common; // 👈 這裡必須跟你的資料夾路徑完全對應！

import com.esun.voting.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 使用 @RestControllerAdvice 確保所有回傳都會被轉為 JSON
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleAllExceptions(Exception ex) {
        // 在 Console 印出錯誤，方便你自己看原因
        System.err.println("【共用層攔截到錯誤】: " + ex.getMessage());
        
        // 回傳你自己定義的格式
        return new ApiResponse<>(
            "伺服器發生異常: " + ex.getMessage(), 
            null
        );
    }
}