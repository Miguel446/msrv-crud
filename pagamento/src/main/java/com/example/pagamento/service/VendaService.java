package com.example.pagamento.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pagamento.dto.VendaDto;
import com.example.pagamento.entity.ProdutoVenda;
import com.example.pagamento.entity.Venda;
import com.example.pagamento.exception.ResourceNotFoundException;
import com.example.pagamento.repository.ProdutoVendaRepository;
import com.example.pagamento.repository.VendaRepository;

@Service
public class VendaService {

	private static final String MSG = "Nenhuma venda encontrada para esse ID";

	@Autowired
	private VendaRepository vendaRepository;
	@Autowired
	private ProdutoVendaRepository pvRepository;

	@Autowired
	private ProdutoVendaService pvService;

	public VendaDto create(VendaDto vendaDto) {
		Venda venda = vendaRepository.save(entity(vendaDto));

		List<ProdutoVenda> produtosSalvos = new ArrayList<>();
		vendaDto.getProdutos().forEach(p -> {
			ProdutoVenda pv = pvService.entity(p);
			pv.setVenda(venda);
			produtosSalvos.add(pvRepository.save(pv));
		});
		venda.setProdutos(produtosSalvos);
		return dto(venda);
	}

	public Page<VendaDto> findAll(Pageable pageable) {
		Page<Venda> page = vendaRepository.findAll(pageable);
		return page.map(this::dto);
	}

	public VendaDto findById(Long id) {
		Venda venda = vendaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MSG));
		return dto(venda);
	}

	private VendaDto dto(Venda venda) {
		return new ModelMapper().map(venda, VendaDto.class);
	}

	private Venda entity(VendaDto vendaDto) {
		return new ModelMapper().map(vendaDto, Venda.class);
	}

}
