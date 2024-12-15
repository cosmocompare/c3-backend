package c3.cosmetic.service;

import c3.cosmetic.dto.response.RankingResponse;
import c3.cosmetic.repository.CosmeticRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CosmeticService {

    private final CosmeticRepository cosmeticRepository;

    // 메인페이지 상품 리스트 반환
    public List<RankingResponse> getCosmetics() {
        // DB에서 상품 정보를 조회하고, DTO로 변환하여 반환
        return cosmeticRepository.findAll().stream()
                       .map(RankingResponse::from)
                       .collect(Collectors.toList());
    }
}
