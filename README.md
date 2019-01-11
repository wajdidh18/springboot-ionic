# Projeto Spring Boot e Ionic
Projeto Java usando Spring Boot, Hibernate, REST e Ionic - Curso Udemy [aqui](https://www.udemy.com/spring-boot-ionic/)

Esse projeto está sendo feito para estudar essas tecnologias. 
Sendo assim, aqui farei anotações de coisas importantes para não esquecer durante o aprendizado.

## Controllers

Os Controllers ficam dentro da pasta "resource". E acima do nome tem a anotação `@RestController` e `@RequestMapping(value="nome-do-endpoins-rest")`. Essa segunda anotação é para mapear a classe com um endpoint.

Cada método também tem uma anotação identificando qual é o método HTTP dele. Isso pode ser feito de duas maneiras. Por exemplo:
`@GetMapping` ou `@RequestMapping(method=RequestMethod.GET)`


## Domínios

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


## Repository

São interfaces que extendem a interface `JpaRepository` (no caso de usando JPA haha), passando os parâmetros: classe domínio está sendo representada e o tipo do atributo Id.

Em cima da interface, coloca a anotação `@Repository`


## Arquitetura do projeto

![alt text](https://github.com/amandaisabelalima/springboot-ionic/blob/master/arquitetura_projeto.png)

**Camada de Serviço:**  responsável por oferecer operações e consultas para os controladores. Essa camada não tem contato nenhum com alguma tecnologia específica (não tem contado com o banco, com o REST, tela, nada). Aqui ficam as regras de negócio.

**Camada de acesso a dados:** responsável por conversar com o banco de dados. Nela ficam as operações de salvar, excluir, etc. Alguns chamam de Repository ou DAO


