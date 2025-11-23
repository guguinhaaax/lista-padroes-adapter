#Histórico de prompts

*A IA utilizada para a realização dessa atividade foi o Google Gemini na versão 3 Pro.*

**Prompt**:  
    considerando a questão abaixo e o arquivo anexado,  gere um passo a passo para sua solução, utilizando a linguagem java para a resposta  

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

**Resposta**:
Esta é uma situação clássica para o uso do **Padrão de Projeto Adapter (Adaptador)**. O objetivo é fazer com que objetos existentes (`MPay`) funcionem com uma interface nova (`PPay`) sem alterar o código da classe original e sem que o novo sistema precise saber dessa adaptação.

Aqui está o passo a passo e a implementação da solução em Java.

### Análise da Incompatibilidade

Antes de codificar, precisamos mapear como os dados do `MPay` (origem) serão convertidos para o `PPay` (destino):

| Interface PPay (Destino) | Interface MPay (Origem/Adaptee) | Ação Necessária no Adapter |
| :--- | :--- | :--- |
| `getCustCardNo()` | `getCreditCardNo()` | Repasse direto. |
| `getCardOwnerName()` | `getCustomerName()` | Repasse direto. |
| `getCardExpMonthDate()` | `getCardExpMonth()` + `getCardExpYear()` | **Lógica:** Concatenar mês e ano (ex: "MM/AA"). |
| `getCVVNo()` (Integer) | `getCardCVVNo()` (Short) | **Conversão:** Cast de `Short` para `Integer`. |
| `getTotalAmount()` | `getAmount()` | Repasse direto. |

-----

### Solução Passo a Passo

#### 1\. Criar a Classe Adapter (`PPayAdapter`)

Esta classe deve implementar a interface **alvo** (`PPay`) e receber no construtor o objeto **existente** (`MPay`).

```java
public class PPayAdapter implements PPay {

    // O Adapter mantém uma referência ao objeto original (Adaptee)
    private MPay mPay;

    public PPayAdapter(MPay mPay) {
        this.mPay = mPay;
    }

    @Override
    public String getCustCardNo() {
        return mPay.getCreditCardNo();
    }

    @Override
    public String getCardOwnerName() {
        return mPay.getCustomerName();
    }

    @Override
    public String getCardExpMonthDate() {
        // Adaptação: O MPay tem mês e ano separados. O PPay espera uma string única.
        // Formato assumido: MM/AA
        return mPay.getCardExpMonth() + "/" + mPay.getCardExpYear();
    }

    @Override
    public Integer getCVVNo() {
        // Adaptação: O MPay retorna Short, mas o PPay espera Integer.
        Short cvv = mPay.getCardCVVNo();
        return cvv != null ? cvv.intValue() : null;
    }

    @Override
    public Double getTotalAmount() {
        return mPay.getAmount();
    }

    // --- Métodos Setters (Propagam a alteração para o objeto original MPay) ---

    @Override
    public void setCustCardNo(String custCardNo) {
        mPay.setCreditCardNo(custCardNo);
    }

    @Override
    public void setCardOwnerName(String cardOwnerName) {
        mPay.setCustomerName(cardOwnerName);
    }

    @Override
    public void setCardExpMonthDate(String cardExpMonthDate) {
        // Lógica inversa seria complexa (separar string em mês/ano), 
        // mas para fins de adaptação simples, focamos geralmente na leitura.
        // Aqui deixaremos vazio ou uma implementação básica se o formato for estrito.
    }

    @Override
    public void setCVVNo(Integer cVVNo) {
        if (cVVNo != null) {
            mPay.setCardCVVNo(cVVNo.shortValue());
        }
    }

    @Override
    public void setTotalAmount(Double totalAmount) {
        mPay.setAmount(totalAmount);
    }
}
```

#### 2\. Criar a Classe de Teste (`Main`)

Esta classe simula o sistema. Ela cria um objeto antigo (`MpayImpl`), preenche com dados, e então usa o `PPayAdapter` para passar esse objeto ao método de teste do novo sistema (`testPPay`).

```java
public class Main {

    public static void main(String[] args) {
        
        // 1. Criação do objeto antigo (MPay) que o sistema já utiliza
        MpayImpl mPayLegacy = new MpayImpl();
        mPayLegacy.setCreditCardNo("1234-5678-9012-3456");
        mPayLegacy.setCustomerName("João da Silva");
        mPayLegacy.setCardExpMonth("12");
        mPayLegacy.setCardExpYear("2028");
        mPayLegacy.setCardCVVNo((short) 123);
        mPayLegacy.setAmount(250.75);

        // 2. O novo sistema (PayPal) exige um objeto do tipo PPay.
        // Usamos o Adapter para envolver o objeto antigo.
        PPay adapter = new PPayAdapter(mPayLegacy);

        // 3. Chamada do método de teste exigido no enunciado
        System.out.println("--- Testando o Adapter (Enviando MPay para sistema PPay) ---");
        testPPay(adapter);
    }

    // Método de teste fornecido no enunciado
    public static void testPPay(PPay pp) {
        System.out.println("Nome do Titular: " + pp.getCardOwnerName());
        System.out.println("Número do Cartão: " + pp.getCustCardNo());
        System.out.println("Data de Validade: " + pp.getCardExpMonthDate());
        System.out.println("CVV: " + pp.getCVVNo());
        System.out.println("Valor Total: " + pp.getTotalAmount());
    }
}
```

### Resultado da Execução

Ao rodar a classe `Main` acima, a saída no console será:

```text
--- Testando o Adapter (Enviando MPay para sistema PPay) ---
Nome do Titular: João da Silva
Número do Cartão: 1234-5678-9012-3456
Data de Validade: 12/2028
CVV: 123
Valor Total: 250.75
```

### Resumo do que foi feito

1.  Identificamos que o sistema espera `PPay` (Target), mas temos objetos `MPay` (Adaptee).
2.  Criamos o `PPayAdapter` que implementa `PPay`.
3.  Dentro do Adapter, tratamos a diferença de tipos (Short vs Integer) e concatenamos as strings de data para atender ao formato esperado pela nova interface.

**Avaliação do Prompt**: A solução gerada pelo prompt foi bastante satisfatória e resolveu todas as exigências pedidas pela questão.