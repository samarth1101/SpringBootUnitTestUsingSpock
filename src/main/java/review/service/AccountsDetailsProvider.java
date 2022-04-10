package review.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AccountsDetailsProvider {

    public CompletableFuture<String> retrieveToAccountDetails() {
        return CompletableFuture.supplyAsync(() -> {
            return "ToAccountResponse";
        });
    }

    public CompletableFuture<String> retrieveFromAccountsDetails() {
        return CompletableFuture.supplyAsync(() -> {
            return "FromAccountResponse";
        });
    }
}
