# Projeto Spring Boot e Ionic
Projeto Java usando Spring Boot, Hibernate, REST e Ionic - Curso Udemy [aqui](https://www.udemy.com/spring-boot-ionic/)

Esse projeto está sendo feito ao longo do curso para aprender essas tecnologias.
Sendo assim, aqui farei anotações de coisas importantes (e de forma bem didática) para não esquecer durante o aprendizado.

 - [Controller]
 - [Domain]
 	- [hashCode e equals]
 	- [serializable]
 	- [associação many-to-many]
 	- [@JsonManagedReference e @JsonBackReference]
 - [Repository]
 - [Service]
 - [Exceptions]
 - [Arquitetura] 

## Controller

Os Controllers ficam dentro da pasta "resource". E acima do nome tem a anotação `@RestController` e `@RequestMapping(value="nome-do-endpoins-rest")`. Essa segunda anotação é para mapear a classe com um endpoint.

Cada método também tem uma anotação identificando qual é o método HTTP dele. Isso pode ser feito de duas maneiras. Por exemplo:
`@GetMapping` ou `@RequestMapping(method=RequestMethod.GET)`

Nessas classes é necessário instanciar a interface Service correspondente. No Spring existe a annotation `@Autowired` para isso, que instancia automaticamente.

Então cria um objeto repository com a anotação em cima:
```java
@Autowired
private CategoriaService service;
```

## Domain

As classes de domínio possuem atributos, construtores, getters e setters, hashCode e equals, e serializable (padrão: 1L)

#### > hashCode e equals
 
 Esses métodos são necessários para que o objeto seja comparado pelo seu conteúdo e não pelo ponteiro de memória. Ou seja, são operaçes para comparar objetos por valor.

Geralmente um objeto é comparado apenas pelo o id. Se o id for diferente, os objetos tbm são difentes. Então pode escolher só ele parar criar o método.

#### > serializable

É uma interface que diz que a classe que está implementado ela, pode ser convertida em uma sequência de bytes.

Isso serve para que os objetos possam ser gravados em artigos, trafegar em redes.... isso é uma exigência do Java.

--

Nas classes de domínio também é preciso colocar as anotações para mapear essas informações no banco de dados. Anotações como: `@Entity`, `@Id`, `@GeneratedValue(strategy=GenerationType.IDENTITY)`....

Além disso é preciso colocar no pom.xml a dependência do banco de dados que será utilizado.

> As informaçes de conexão com o banco ficam no arquivo `application.properties`

> As dependências ficam no arquivo `pom.xml`

--

#### > associação many to many
Quando entre duas tabelas existe uma associação many to maxy, é preciso informar isso através de annotations.

Exemplo de um relacionamento Produtos com Categoria:

Na classe Produto faz a seguinte annotation:
```java
@JsonBackReference
@ManyToMany
@JoinTable(name = "PRODUTO_CATEGORIA",
           joinColumns = @JoinColumn(name = "produto_id"),
           inverseJoinColumns = @JoinColumn(name = "categoria_id")
)
private List<Categoria> categorias = new ArrayList<>();
```
Essa anottation está informando que é uma relação many to many, a tabela intermediária que será criada no banco será chamada de "PRODUTO_CATEGORIA", o nome da foreign key da classe em que está será "produto_id", e por fim, o nome da foreign key da outra classe "categoria_id".

Depois disso, na classe Categoria precisa ter a annotation:
```java
@JsonManagedReference
@ManyToMany(mappedBy="categorias")
private List<Produto> produtos = new ArrayList<>();
```
Essa annotation diz que o mapeamento já foi feito no atributo "categorias" da outra Classe.

#### > associação one to one
Aqui no projeto, as entidades Pedido e Pagamento possuem relacionamento um para um e por isso, o id do pagamento será o mesmo do id do pedido correspondente.

O mapeamento em casos assim, fica do seguinte modo:

Classe Pagamento:
```java
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Pagamento implements Serializable{
	@Id
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId
	private Pedido pedido;
	...
```
- No atributo *id* não coloca a annotation `@GeneratedValue(strategy=GenerationType.IDENTITY)` porque ele não vai ser gerado automaticamente. Ao contrário, ele vai ser o mesmo que o id da entidade Pedido.

- No atributo *pedido* a annotation `@MapsId` garante que o id do pagamento seja o mesmo do id do pedido e que cada Pagamento pertença a apenas 1 pedido (one to one).

- A annotation `@Inheritance(strategy=InheritanceType.JOINED)` na classe indica que é para criar uma tabela para cada classe filha.

Classe Pedido:
```java
@Entity
public class Pedido implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;
	...
```
- `cascade=CascadeType.ALL` precisa ser informado, para que todas as operações envolvendo o Pedido sejam feitas em cascata. Ou seja, nas operaçes de salvar, deletar, editar realizadas no Pedido, também serão feitas no Pagamento equivalente, garantindo a integridade das informações.


##### > @JsonManagedReference e @JsonBackReference

Quando o sistema vai tentar serializar o objeto Categorias, cai em uma recursão infinita. Isso acontece porque cada categoria tem uma lista de produtos, e cada produto tem uma lista de categorias... indefinidamente.

Para resolver isso, são usadas as annotations @JsonManagedReference e @JsonBackReference.

`@JsonManagedReference` é a parte direta da referência  (aquela que é serializada normalmente).

`@JsonBackReference`  é a parte de trás da referência (ela será omitida da serialização). Então, aqui indica que do outro lado da associação já foram buscados os objetos, então esse lado não precisa buscar mais, omitindo assim, a lista de categorias do objeto de produtos.

## Repository


São interfaces que extendem a interface `JpaRepository` (no caso de usando JPA haha), passando os parâmetros: classe domínio está sendo representada e o tipo do atributo Id.

Em cima da interface, coloca a anotação `@Repository`


## Service

Nessas classes é necessário instanciar a interface Repository correspondente. No Spring existe a annotation `@Autowired` para isso, que instancia automaticamente.

Então cria um objeto repository com a anotação em cima:
```java
@Autowired
private CategoriaRepository repo;
```

## Enums

Contructor de Enum é do tipo `private`, e só possui métodos `get`. Não tem os métodos `set` porque uma vez que instancia um tipo enumerado, não pode mais mudar o nome.


## Exceptions

As exceçes são lançadas nas classes de Service, e a camanda de recurso (Controller) capturam e enviam um json apropriado para a resposta Http.

Para isso, no controller poderia ser inserido um try catch, porém para não ficar muita coisa nessa classe, foi criado uma camada de tratamento (Handler), que intercepta quando ocorre alguma exceção e faz o tratamento.

Nesse projeto, foi criada uma classe de exceção personalizada chamada `ObjectNotFoundException` e o manipulador de exceções do recurso chamado `ResourceExceptionHandler`. Também foi criado a classe `StandardError` para servir como objeto auxiliar, e que possui os atributos do código http, mensagem e instante do erro.

O handler(manipulador de erros) precisa ter a annotation `@ControllerAdvice` e, obrigatoriamente, um método que receba a exceção e as informações da requisição. Dentro no método, instancia a classe auxiliar passando os dados necessários. O método também precisa da annotation `@ExceptionHandler(ObjectNotFoundException.class)` que indica que ele é um tratador de exceção do tipo x.

```java
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class) 
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
 
}
```



## Arquitetura do projeto

![alt text](https://github.com/amandaisabelalima/springboot-ionic/blob/master/arquitetura_projeto.png)

**Camada de Serviço:**  responsável por oferecer operações e consultas para os controladores. Essa camada não tem contato nenhum com alguma tecnologia específica (não tem contado com o banco, com o REST, tela, nada). Aqui ficam as regras de negócio.

**Camada de acesso a dados:** responsável por conversar com o banco de dados. Nela ficam as operações de salvar, excluir, etc. Alguns chamam de Repository ou DAO








---

Referências úteis:
- [Jackson – Bidirectional Relationships](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)



 [Controller]: https://github.com/amandaisabelalima/springboot-ionic#controller
 [hashCode e equals]: https://github.com/amandaisabelalima/springboot-ionic#-hashcode-e-equals
 [serializable]: https://github.com/amandaisabelalima/springboot-ionic#-serializable
 [associação many-to-many]: https://github.com/amandaisabelalima/springboot-ionic#-associa%C3%A7%C3%A3o-many-to-many
 [@JsonManagedReference e @JsonBackReference]: https://github.com/amandaisabelalima/springboot-ionic#-jsonmanagedreference-e-jsonbackreference
 [Domain]: https://github.com/amandaisabelalima/springboot-ionic#domain
 [Repository]: https://github.com/amandaisabelalima/springboot-ionic#repository
 [Service]: https://github.com/amandaisabelalima/springboot-ionic#service
 [Exceptions]: https://github.com/amandaisabelalima/springboot-ionic#exceptions
 [Arquitetura]: https://github.com/amandaisabelalima/springboot-ionic#arquitetura-do-projeto 


