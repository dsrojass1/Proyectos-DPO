package Pagos;

public class PaymentResult {
    private boolean successful;
    private String errorMessage;

    public PaymentResult(boolean successful) {
        this(successful, null);
    }

    public PaymentResult(boolean successful, String errorMessage) {
        this.successful = successful;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
