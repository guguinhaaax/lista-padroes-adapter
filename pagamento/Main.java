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