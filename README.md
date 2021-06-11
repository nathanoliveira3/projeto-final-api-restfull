# trabalho_final_API_restfull
Repositório para o trabalho final da disciplina API Restful da Residência em Software/TIC  do Serratec

## Seu grupo deve criar um aplicativo de E-Commerce atendendo os seguintes requisitos:

- 01 - Utilizar um sistema de login.(JWT)
- 02 - Um Cliente poderá se cadastrar livremente.
- [x] 03 - Para o cadastro de cliente deverá informar os dados mostrados nas tabelas abaixo. 
- [x] 04 - O endereço deverá ser validado através da API Via Cep.
- 05 - Após logado o Cliente poderá fazer as seguintes operações:(Com exceção das duas últimas (11 e 12), todas não poderão ser realizadas sem o envio do token)
- 06 - Atualizar seus próprios dados pessoais (como endereço, telefone, menos CPF).
- 07 - Desativar sua própria conta.
- [x] 08 - Criar um novo Pedido  
- 09 - Editar um pedido  que não esteja com status de finalizado.
- 10 - Finalizar um pedido, alterar seu status para finalizado. Ao finalizar o pedido enviar e-mail para o cliente informando data de entrega, produtos, quantidades e valor final do pedido.
- [x] 11 - Visualizar todas as categorias ou uma específica pelo nome.
- 12 - Visualizar todos os produtos ou um específico pelo nome.

## Recursos que devem estar disponíveis sem o usuário estar logado no sistema:

- Visualizar todas as categorias ou uma específica pelo nome.
- Criar uma nova categoria.
- Editar uma categoria.
- Deletar uma categoria
- Visualizar todos os produtos ou um específico pelo nome.
- Criar um novo produto (Com imagem).
- Editar um produto.
- Deletar um produto.
- Visualizar todos os pedidos.
- Excluir algum pedido.

## Observações:

- CPF deve ser válido.
- Produto não poderá ter valores negativos
- Todas as exceptions devem ser tratadas
- A Api deverá utilizar como documentação a ferramenta do Swagger.

## Desafío Extra (Opcional):
 
- Criar uma opção de esqueci minha senha com envio de um código de verificação para o e-mail e posterior verificação se esse código pertence ao cliente.

## Regras: 

- Ser feito em grupo
- Utilizar GIT
