package com.example.crud.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.crud.dto.ProdutoDto;
import com.example.crud.entity.Produto;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.message.ProdutoSendMessage;
import com.example.crud.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private static final String MSG = "Nenhum produto encontrado para esse ID";

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoSendMessage produtoSendMessage;

	public ProdutoDto create(ProdutoDto produtoDto) {
		ProdutoDto pDto = dto(produtoRepository.save(entity(produtoDto)));
		produtoSendMessage.sendMessage(pDto);
		return pDto;
	}

	public Page<ProdutoDto> findAll(Pageable pageable) {
		Page<Produto> page = produtoRepository.findAll(pageable);
		return page.map(this::dto);
	}

	public ProdutoDto findById(Long id) {
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG));
		return dto(produto);
	}

	public ProdutoDto update(ProdutoDto produtoDto) {
		final Optional<Produto> optionalProduto = produtoRepository.findById(produtoDto.getId());
		if (!optionalProduto.isPresent()) {
			throw new ResourceNotFoundException(MSG);
		}

		return dto(produtoRepository.save(entity(produtoDto)));
	}

	public void delete(Long id) {
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG));
		produtoRepository.delete(produto);
	}

	private ProdutoDto dto(Produto produto) {
		return new ModelMapper().map(produto, ProdutoDto.class);
	}

	private Produto entity(ProdutoDto produtoDto) {
		return new ModelMapper().map(produtoDto, Produto.class);
	}

}
