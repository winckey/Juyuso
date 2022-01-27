package com.juyuso.api.controller;

import com.juyuso.api.dto.response.DrinkingHistoryListResDto;
import com.juyuso.api.service.DrinkingHistoryService;
import com.juyuso.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/drinking")
@RestController
@Api( value = "술 먹은 기록")
public class DrinkingHistoryController {


    private final DrinkingHistoryService drinkingService;

    @Autowired
    public DrinkingHistoryController(DrinkingHistoryService drinkingHistoryService) {
        this.drinkingService = drinkingHistoryService;

    }

    @GetMapping("/history")
    @ApiOperation(value = "사용자의 술 커밋기록", notes = "<strong>술커밋</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트 불러오기 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<List<DrinkingHistoryListResDto>> getDrinkingHistoryList (Authentication authentication) {
        User user = (User) authentication.getDetails();
        return ResponseEntity.ok(DrinkingHistoryListResDto.of(drinkingService.findAll(user)));
    }







}
