package c3.cosmetic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "zzcosmetic")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ZzCosmetic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String brand;

    @Column(name = "cosmetic_name")
    private String cosmeticName;

    private String price;

    @Column(name = "sale_price")
    private String salePrice;

    @Column(name = "cosmetic_url", columnDefinition = "TEXT")
    private String cosmeticUrl;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
