package c3.cosmetic.repository;

import c3.cosmetic.entity.ZzCosmetic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzCosmeticRepository extends JpaRepository<ZzCosmetic, Long> {
    @Query("SELECT z FROM ZzCosmetic z where substring(z.cosmeticName, 10) = substring(:cosmeticName, 10)")
    Optional<ZzCosmetic> findByCosmeticName(@Param("cosmeticName") String cosmetic_name);

}
