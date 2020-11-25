package com.example.pagamento.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.pagamento.dto.ProdutoDto;
import com.example.pagamento.service.ProdutoService;

@Component
public class ProdutoReceiveMessage {

	@Autowired
	private ProdutoService produtoService;

	@RabbitListener(queues = { "${crud.rabbitmq.queue}" })
	public void receive(@Payload ProdutoDto produtoDto) {
		System.out.println(produtoDto.toString());
		produtoService.create(produtoDto);
	}

}
