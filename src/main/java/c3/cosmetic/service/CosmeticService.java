package c3.cosmetic.service;

import c3.cosmetic.dto.response.RankingResponse;
import c3.cosmetic.entity.Ranking;
import c3.cosmetic.entity.ZzCosmetic;
import c3.cosmetic.repository.RankingRepository;
import c3.cosmetic.repository.ZzCosmeticRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CosmeticService {

    private final RankingRepository rankingRepository;
    private final ZzCosmeticRepository zzCosmeticRepository;

    // 메인페이지 상품 리스트 반환
    public List<RankingResponse> getAllRankings() {
        // DB에서 상품 정보를 조회하고, DTO로 변환하여 반환
        List<Ranking> rankings = rankingRepository.findAll();
        return rankings.stream().map(ranking -> {RankingResponse response = RankingResponse.from(ranking);

            String lowest_price = ranking.getSalePrice();
            String price_source = "OY";

            Optional<ZzCosmetic> zzCosmetic = zzCosmeticRepository.findByCosmeticName(ranking.getCosmeticName());

            if (zzCosmetic.isPresent()) {
                String zz_salePrice = zzCosmetic.get().getSalePrice();

                if (Integer.parseInt(zz_salePrice) < Integer.parseInt(ranking.getSalePrice())) {
                    lowest_price = zz_salePrice;
                    price_source = "ZZ";
                }

                response.setZzCosmeticInfo(zz_salePrice, lowest_price, price_source);
            }
                else {
                    response.setZzCosmeticInfo(null, lowest_price, price_source);
                }
                return response;
            })
            .collect(Collectors.toList());
    }
}
