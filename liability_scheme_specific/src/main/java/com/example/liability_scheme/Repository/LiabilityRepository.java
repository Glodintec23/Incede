package com.example.liability_scheme.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.liability_scheme.Model.LiabilityModel;

public interface LiabilityRepository extends JpaRepository<LiabilityModel, Long> {

    @Query("SELECT l FROM LiabilityModel l WHERE l.schemeSpecificId = :schemeSpecificId")
	LiabilityModel findExistingLiabilitySchemeSpecificMasterDetails(@Param("schemeSpecificId") Long schemeSpecificId);

    @Query("SELECT l FROM LiabilityModel l WHERE l.schemeSpecificId = :schemeSpecificId AND l.isDelete = false")
    LiabilityModel findLiabilityModelForSoftDeleting(@Param("schemeSpecificId") Long schemeSpecificId);

    @Query("SELECT l FROM LiabilityModel l WHERE l.isDelete = false")
	List<LiabilityModel> findAllLiabilityModel();


}
