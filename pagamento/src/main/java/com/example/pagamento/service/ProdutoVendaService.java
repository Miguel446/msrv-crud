package com.example.pagamento.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.pagamento.dto.ProdutoVendaDto;
import com.example.pagamento.entity.ProdutoVenda;

@Service
public class ProdutoVendaService {

	public ProdutoVendaDto dto(ProdutoVenda produtoVenda) {
		return new ModelMapper().map(produtoVenda, ProdutoVendaDto.class);
	}

	public ProdutoVenda entity(ProdutoVendaDto produtoVendaDto) {
		return new ModelMapper().map(produtoVendaDto, ProdutoVenda.class);
	}

}
