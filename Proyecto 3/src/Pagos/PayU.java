package Pagos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Random;

public class PayU implements PaymentGatewaySimulator {

    private static final String NAME = "PayU";
    private static final String LOG_FILE_NAME = "docs/PayU.log";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public PaymentResult processPayment(CreditCardInfo cardInfo, double amount) {
        // Realizar aquí la lógica necesaria para procesar el pago a través de PayPal
        // En este caso, se genera un resultado aleatorio entre éxito o fracaso
        boolean isSuccess = true;
        String message = isSuccess ? "Payment processed successfully through PayPal" : "Payment failed";
        PaymentResult result = new PaymentResult(isSuccess, message);
        // Registrar la transacción en el archivo de log correspondiente
        String transactionLog = String.format("%s - Amount: %.2f - Result: %s", LocalDateTime.now(), amount, isSuccess ? "SUCCESS" : "FAILURE");
        try {
            Files.write(Paths.get(LOG_FILE_NAME), transactionLog.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing transaction log: " + e.getMessage());
        }
        return result;
    }
}