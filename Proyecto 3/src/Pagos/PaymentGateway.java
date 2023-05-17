package Pagos;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentGateway {
    private List<PaymentGatewaySimulator> gateways;

    public PaymentGateway() {
        gateways = new ArrayList<>();
        loadGateways();
    }

    private void loadGateways() {
        try {
            List<String> gatewayNames = Files.readAllLines(Paths.get("docs/gateway-config.txt"));
            for (String name : gatewayNames) {
                Class<?> gatewayClass = Class.forName(name);
                PaymentGatewaySimulator gateway = (PaymentGatewaySimulator) gatewayClass.getDeclaredConstructor().newInstance();
                gateways.add(gateway);
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("Error loading gateways: " + e.getMessage());
        }
    }

    public List<String> getGatewayNames() {
        return gateways.stream().map(PaymentGatewaySimulator::getName).collect(Collectors.toList());
    }

    public boolean processPayment(String gatewayName, CreditCardInfo cardInfo, double amount) {
        Optional<PaymentGatewaySimulator> gateway = gateways.stream().filter(g -> g.getName().equals(gatewayName)).findFirst();
        if (gateway.isPresent()) {
            PaymentResult result = gateway.get().processPayment(cardInfo, amount);
            System.out.println("Payment result: " + result);
            return result.isSuccessful();
        } else {
            System.out.println("Invalid gateway name");
            return false;
        }
    }
}
