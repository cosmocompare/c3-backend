package c3.cosmetic.repository;

import c3.cosmetic.entity.Ranking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CosmeticRepository extends JpaRepository<Ranking, Long> {

}
