package Pagos;

public interface PaymentGatewaySimulator {
    String getName();
    PaymentResult processPayment(CreditCardInfo cardInfo, double amount);
}

