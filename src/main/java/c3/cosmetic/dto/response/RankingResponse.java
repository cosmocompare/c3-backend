package c3.cosmetic.dto.response;

import c3.cosmetic.entity.Ranking;
import lombok.Builder;
import lombok.Data;

@Data // 클래스에 자동으로 getter, setter 등 추가
public class RankingResponse {

    private Long id; // Ranking의 id
    private String brand; // 제품 브랜드
    private String cosmetic_name; // 제품명
    private String price; // 정가
    private String oy_salePrice; // 할인가
    private String cosmetic_url; // 제품 링크
    private String image_url; // 제품 이미지
    private String zz_salePrice;
    private String lowest_salePrice;
    private String price_source;

    // Ranking 엔티티 객체를 받아서 RankingResponse 객체로 변환하는 메소드
    public static RankingResponse from(Ranking ranking) {
        return RankingResponse.builder().
                       ranking(ranking)
                       .build();
    }

    // Builder를 사용하여 RankingResponse 객체를 생성하는 생성자
    @Builder
    private RankingResponse(Ranking ranking) {
        this.id = ranking.getId();
        this.brand = ranking.getBrand();
        this.cosmetic_name = ranking.getCosmeticName();
        this.price = ranking.getPrice();
        this.oy_salePrice= ranking.getSalePrice();
        this.cosmetic_url = ranking.getCosmeticUrl();
        this.image_url = ranking.getImageUrl();
    }

    public void setZzCosmeticInfo(String zz_salePrice, String lowest_salePrice, String price_source) {
        this.zz_salePrice = zz_salePrice;
        this.lowest_salePrice = lowest_salePrice;
        this.price_source = price_source;
    }
}
