package c3.cosmetic.controller;

import c3.cosmetic.dto.response.RankingResponse;
import c3.cosmetic.service.CosmeticService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CosmeticController {

    private final CosmeticService cosmeticService;

    // 메인페이지 상품 목록 API
    @GetMapping("/api/v1")
    public ResponseEntity<List<RankingResponse>> getAllRankings() {
        List<RankingResponse> ranking = cosmeticService.getAllRankings();
        return ResponseEntity.ok(ranking);
    }
}
