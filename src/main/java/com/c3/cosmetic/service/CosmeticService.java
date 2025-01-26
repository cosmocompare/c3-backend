package com.c3.cosmetic.service;

import com.c3.cosmetic.dto.response.CosmeticResponse;
import com.c3.cosmetic.entity.MssCosmetic;
import com.c3.cosmetic.repository.MssCosmeticRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CosmeticService {

    private final MssCosmeticRepository mssCosmeticRepository;

    // 메인페이지 상품 리스트 반환 (무신사 데이터 전체 - 추후 변경 예정)
    public List<CosmeticResponse> getAllCosmetics() {
        try {
            List<MssCosmetic> mssCosmetics = mssCosmeticRepository.findAll();
            return mssCosmetics.stream().map(CosmeticResponse::from).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("상품 목록 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("상품 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public List<CosmeticResponse> getCosmeticsByCategoryNumber(int categoryNumber) {
        try {
            log.info("카테고리 번호 {} 조회 시작", categoryNumber);

            // 카테고리 번호를 문자열로 변환
            String category = convertNumberToCategory(categoryNumber);

            // 화장품 목록 조회
            List<MssCosmetic> mssCosmetics = mssCosmeticRepository.findByCategory(category);
            log.info("조회된 MSS 상품 수: {}", mssCosmetics.size());

            // 조회된 결과가 없는 경우 빈 리스트 반환
            if (mssCosmetics.isEmpty()) {
                log.info("카테고리 {}에 해당하는 MSS 상품이 없습니다.", category);
                return Collections.emptyList();
            }

            // 엔티티를 응답 객체로 변환하여 반환
            return mssCosmetics.stream()
                           .map(CosmeticResponse::from).toList();
        } catch (Exception e) {
            log.error("상품 조회 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("상품 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 카테고리 번호를 문자열로 변환
    private String convertNumberToCategory(int categoryNumber) {
        if (categoryNumber < 1 || categoryNumber > 7) {
            throw new IllegalArgumentException("유효하지 않은 카테고리 번호입니다: " + categoryNumber);
        }

        return switch (categoryNumber) {
            case 1 -> "스킨케어";
            case 2 -> "마스크팩";
            case 3 -> "클렌징";
            case 4 -> "선케어";
            case 5 -> "베이스메이크업";
            case 6 -> "립메이크업";
            case 7 -> "아이메이크업";
            default -> throw new IllegalArgumentException("잘못된 카테고리 번호입니다.");
        };
    }
}
