package com.example.pagamento.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pagamento.dto.ProdutoDto;
import com.example.pagamento.entity.Produto;
import com.example.pagamento.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public ProdutoDto create(ProdutoDto produtoDto) {
		return dto(produtoRepository.save(entity(produtoDto)));
	}

	private ProdutoDto dto(Produto produto) {
		return new ModelMapper().map(produto, ProdutoDto.class);
	}

	private Produto entity(ProdutoDto produtoDto) {
		return new ModelMapper().map(produtoDto, Produto.class);
	}

}
