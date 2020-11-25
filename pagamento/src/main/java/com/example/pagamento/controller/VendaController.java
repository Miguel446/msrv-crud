package com.example.pagamento.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pagamento.dto.VendaDto;
import com.example.pagamento.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Value("${server.port}")
	private String port;

	private VendaService vendaService;
	private final PagedResourcesAssembler<VendaDto> assembler;

	@Autowired
	public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaDto> assembler) {
		this.vendaService = vendaService;
		this.assembler = assembler;
	}

	@RequestMapping("/mostrarPorta")
	public String mostrarPorta() {
		return port;
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public VendaDto findById(@PathVariable("id") Long id) {
		VendaDto vendaDto = vendaService.findById(id);
		vendaDto.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
		return vendaDto;
	}

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<PagedModel<EntityModel<VendaDto>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "data"));
		Page<VendaDto> vendas = vendaService.findAll(pageable);
		vendas.stream().forEach(p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel()));
		PagedModel<EntityModel<VendaDto>> pagedModel = assembler.toModel(vendas);
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public VendaDto create(@RequestBody VendaDto vendaDto) {
		VendaDto vDto = vendaService.create(vendaDto);
		vDto.add(linkTo(methodOn(VendaController.class).findById(vDto.getId())).withSelfRel());
		return vDto;
	}

}
