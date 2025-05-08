package com.example.liability_scheme.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.liability_scheme.DTO.LiabilityDTO;
import com.example.liability_scheme.Model.LiabilityModel;
import com.example.liability_scheme.Response.ApiResponse;
import com.example.liability_scheme.Service.LiabilityService;

@RestController
@RequestMapping("/v1/liability-scheme-specific-masters")
public class LiabilityController {

    private final LiabilityService liabilityService;

    public LiabilityController(LiabilityService liabilityService) {
        this.liabilityService = liabilityService;
    }

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<LiabilityDTO>> saveOrUpdate(@RequestBody LiabilityDTO liabilityDto) {
        LiabilityDTO result = liabilityService.createOrUpdateLiabilitySchemeSpecificMasterDetails(liabilityDto);

        ApiResponse<LiabilityDTO> response = new ApiResponse<>();
        response.setStatus("successful");
        response.setMessage("Liability saved or updated successfully.");
        response.setData(result);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/liability/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> softDeleteLiability(@PathVariable("id") Long id) {
        LiabilityModel result = liabilityService.softDeletingLiabilitySchemeSpecificMasterDetails(id);

        ApiResponse<Boolean> response = new ApiResponse<>();
        response.setStatus("successful");
        response.setMessage("Liability deleted successfully.");
        response.setData(result.isDelete());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/liability/get/{id}")
    public ResponseEntity<ApiResponse<LiabilityDTO>> getLiabilityDetails(@PathVariable("id") Long id) {
        LiabilityDTO result = liabilityService.getLiabilitySchemeSpecificMasterDetailById(id);

        ApiResponse<LiabilityDTO> response = new ApiResponse<>();
        response.setStatus("successful");
        response.setMessage("Liability details retrieved successfully.");
        response.setData(result);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/liability/get")
    public ResponseEntity<ApiResponse<List<LiabilityDTO>>> getAllLiabilityDetails() {
        List<LiabilityDTO> result = liabilityService.getAllLiabilitySchemeSpecificMasterDetail();

        ApiResponse<List<LiabilityDTO>> response = new ApiResponse<>();
        response.setStatus("successful");
        response.setMessage("All liability details retrieved successfully.");
        response.setData(result);

        return ResponseEntity.ok(response);
    }
}

