package com.example.pagamento.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({ "id", "id_produto", "quantidade" })
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProdutoVendaDto extends RepresentationModel<ProdutoVendaDto> implements Serializable {

	private static final long serialVersionUID = 6785181370303143061L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("id_produto")
	private Long idProduto;

	@JsonProperty("quantidade")
	private Integer quantidade;

}
