package com.detrasdelcodigo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder(builderMethodName = "updatePostDtoBuilder")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto extends CrearPostDto {
	
	private Long idpost;
	
	

}
