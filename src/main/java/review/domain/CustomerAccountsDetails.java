package review.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerAccountsDetails {
    private String toAccounts;
    private String fromAccounts;
}
