package com.c3.cosmetic.repository;

import com.c3.cosmetic.entity.MssCosmetic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MssCosmeticRepository extends JpaRepository<MssCosmetic, Long> {
    List<MssCosmetic> findByCategory(String category);
}
