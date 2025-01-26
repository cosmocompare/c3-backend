package com.c3.cosmetic.dto.response;

import com.c3.cosmetic.entity.MssCosmetic;
import lombok.Builder;
import lombok.Data;

@Data
public class CosmeticResponse {
    private Long id;
    private String brand;
    private String cosmeticName;
    private String salePrice;
    private String cosmeticUrl;
    private String imageUrl;

    // entity -> response
    public static CosmeticResponse from(MssCosmetic mssCosmetic) {
        return CosmeticResponse.builder().
                       mssCosmetic(mssCosmetic)
                       .build();
    }

    @Builder
    private CosmeticResponse(MssCosmetic mssCosmetic) {
        this.id = mssCosmetic.getId();
        this.brand = mssCosmetic.getBrand();
        this.cosmeticName = mssCosmetic.getCosmeticName();
        this.salePrice = mssCosmetic.getSalePrice();
        this.cosmeticUrl = mssCosmetic.getCosmeticUrl();
        this.imageUrl = mssCosmetic.getImageUrl();
    }
}
