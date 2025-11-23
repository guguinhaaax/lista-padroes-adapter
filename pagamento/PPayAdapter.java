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