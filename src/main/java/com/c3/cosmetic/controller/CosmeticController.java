package com.c3.cosmetic.controller;

import com.c3.cosmetic.dto.response.CosmeticResponse;
import com.c3.cosmetic.service.CosmeticService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CosmeticController {

    private final CosmeticService cosmeticService;

    // 메인페이지 상품 목록 API
    @GetMapping("")
    public ResponseEntity<List<CosmeticResponse>> getAllCosmetics() {
        List<CosmeticResponse> cosmetics = cosmeticService.getAllCosmetics();
        return ResponseEntity.ok(cosmetics);
    }

    // 카테고리별 상품 목록 API
    @GetMapping("/{categoryNumber}")
    public ResponseEntity<List<CosmeticResponse>> getCosmeticsByNumber(
            @PathVariable(name = "categoryNumber", required = true) Integer categoryNumber) {
        List<CosmeticResponse> cosmetics = cosmeticService.getCosmeticsByCategoryNumber(categoryNumber);
        return ResponseEntity.ok(cosmetics);
    }



}
