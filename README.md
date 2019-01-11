# Projeto Spring Boot e Ionic
Projeto Java usando Spring Boot, Hibernate, REST e Ionic - Curso Udemy

Esse projeto está sendo feito para estudar essas tecnologias. 
Sendo assim, aqui farei anotações de coisas importantes para não esquecer durante o aprendizado.

## Controllers

Os Controllers ficam dentro da pasta "resource". E acima do nome tem a anotação `@RestController` e `@RequestMapping(value="nome-do-endpoins-rest")`

Cada método também tem uma anotação identificando qual é o método HTTP dele. Isso pode ser feito de duas maneiras. Por exemplo
`@GetMapping` ou `@RequestMapping(method=RequestMethod.GET)`
