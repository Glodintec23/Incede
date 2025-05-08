package com.example.liability_scheme.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LiabilityDTO {
	
	 
 	private Long schemeSpecificId;
	
 	private Long schemeId;
	
 	private String configKey;
	
 	private String configValue;
	
 	private String configValueType ;
	
	private boolean isDelete;

}
