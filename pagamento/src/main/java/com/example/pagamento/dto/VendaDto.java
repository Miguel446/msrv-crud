package com.example.pagamento.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({ "id", "data", "produtos", "valor_total" })
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VendaDto extends RepresentationModel<VendaDto> implements Serializable {

	private static final long serialVersionUID = 9117271961322296893L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("data")
	private Date data;

	@JsonProperty("produtos")
	private List<ProdutoVendaDto> produtos;

	@JsonProperty("valor_total")
	private Double valorTotal;

}
