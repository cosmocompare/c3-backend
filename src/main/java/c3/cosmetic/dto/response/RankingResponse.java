package c3.cosmetic.dto.response;

import c3.cosmetic.entity.Ranking;
import lombok.Builder;
import lombok.Data;

@Data // 클래스에 자동으로 getter, setter 등 추가
public class RankingResponse {

    private Long id;
    private String brand; // 제품 브랜드
    private String cosmetic_name; // 제품명
    private String price; // 정가
    private String sale_price; // 할인가
    private String cosmetic_url; // 제품 링크
    private String image_url; // 제품 이미지

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
        this.cosmetic_name = ranking.getCosmetic_name();
        this.price = ranking.getPrice();
        this.sale_price = ranking.getSale_price();
        this.cosmetic_url = ranking.getCosmetic_url();
        this.image_url = ranking.getImage_url();
    }
}
