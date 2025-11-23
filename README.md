# lista-padroes-adapter

Resposta da terceira questão da Lista Avaliativa II da disciplina Padrões de Objeto, relativo ao padrão Adapter.

------------------------------------------------------------------------------------------------------------------------

Suponha que você trabalha em um site de comércio eletrônico. O site permite que os usuários
comprem e paguem online. O site é integrado a um sistema de pagamento de terceiros, por
meio do qual os usuários podem pagar suas contas com cartão de crédito. Tudo ia bem, até
que surgiu a necessidade de mudar o fornecedor do sistema de pagamento. O problema que
surge aqui é que o site está anexado ao sistema de pagamento MercadoPapo, que recebe
um objeto do tipo MPay. O novo fornecedor, PayPal, permite apenas o tipo de objetos PPay
para permitir o processo de pagamento. Você não deseja alterar todo o conjunto de 100
classes que têm referência a um objeto do tipo MPay. Isso também aumenta o risco do projeto,
que já está rodando em produção. Você também não pode alterar o sistema de pagamentos
de terceiros. O problema ocorre devido às interfaces incompatíveis entre as duas partes
diferentes do código. Para que o processo de pagamento funcione, você precisa encontrar
uma maneira de tornar o código compatível com a API fornecida pelo novo fornecedor.

  ● Usando o padrão Adapter, implemente a solução para o problema de
  incompatibilidade entre serviços de pagamento.
  ● Você deverá usar como base os arquivos fonte presentes no arquivo
  pagamento.zip.
  ● Você deverá criar uma classe que demonstre o correto funcionamento de sua solução,
  ela deverá apresentar o seguinte método de teste:
  
    public static void testPPay(PPay pp){
      System.out.println(pp.getCardOwnerName());
      System.out.println(pp.getCustCardNo());
      System.out.println(pp.getCardExpMonthDate());
      System.out.println(pp.getCVVNo());
      System.out.println(pp.getTotalAmount());
    }
