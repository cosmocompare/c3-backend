package c3.cosmetic.repository;

import c3.cosmetic.entity.Ranking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findAll();

}
