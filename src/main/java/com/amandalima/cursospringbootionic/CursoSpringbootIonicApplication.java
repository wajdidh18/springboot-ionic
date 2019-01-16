package com.amandalima.cursospringbootionic;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amandalima.cursospringbootionic.domain.Categoria;
import com.amandalima.cursospringbootionic.domain.Cidade;
import com.amandalima.cursospringbootionic.domain.Cliente;
import com.amandalima.cursospringbootionic.domain.Endereco;
import com.amandalima.cursospringbootionic.domain.Estado;
import com.amandalima.cursospringbootionic.domain.ItemPedido;
import com.amandalima.cursospringbootionic.domain.Pagamento;
import com.amandalima.cursospringbootionic.domain.PagamentoComBoleto;
import com.amandalima.cursospringbootionic.domain.PagamentoComCartao;
import com.amandalima.cursospringbootionic.domain.Pedido;
import com.amandalima.cursospringbootionic.domain.Produto;
import com.amandalima.cursospringbootionic.domain.enums.EstadoPagamento;
import com.amandalima.cursospringbootionic.domain.enums.TipoCliente;
import com.amandalima.cursospringbootionic.repositories.CategoriaRepository;
import com.amandalima.cursospringbootionic.repositories.CidadeRepository;
import com.amandalima.cursospringbootionic.repositories.ClienteRepository;
import com.amandalima.cursospringbootionic.repositories.EnderecoRepository;
import com.amandalima.cursospringbootionic.repositories.EstadoRepository;
import com.amandalima.cursospringbootionic.repositories.ItemPedidoRepository;
import com.amandalima.cursospringbootionic.repositories.PagamentoRepository;
import com.amandalima.cursospringbootionic.repositories.PedidoRepository;
import com.amandalima.cursospringbootionic.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoSpringbootIonicApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringbootIonicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
			
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto produto1 = new Produto(null, "Computador", 2000.00);		
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		cat2.getProdutos().addAll(Arrays.asList(produto2));
		
		produto1.getCategorias().addAll(Arrays.asList(cat1));
		produto2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		produto3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
		
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678909", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pgto2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
	
		
		ItemPedido item1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2000.00);
		ItemPedido item2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
		ItemPedido item3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);
		
		pedido1.getItens().addAll(Arrays.asList(item1, item2));
		pedido2.getItens().addAll(Arrays.asList(item3));
		
		produto1.getItens().addAll(Arrays.asList(item1));
		produto2.getItens().addAll(Arrays.asList(item3));
		produto3.getItens().addAll(Arrays.asList(item2));
		
		itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));
	}

}

