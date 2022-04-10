package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import review.dao.ReviewRepository;
import review.domain.CustomerAccountsDetails;
import review.domain.Review;

import java.sql.Struct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private AccountsDetailsProvider accountsDetailsProvider;

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Optional<Review> findById(String id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Optional<Review> findByProductId(Integer id) {
        return reviewRepository.findByProductId(id);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review save(Review review) {
        review.setVersion(1);
        return reviewRepository.save(review);
    }

    public Review update(Review review) {
        review.setVersion(review.getVersion()+1);
        return reviewRepository.save(review);
    }

    public void delete(String id) {
        reviewRepository.deleteById(id);
    }

    public CustomerAccountsDetails getResposne() {
        CustomerAccountsDetails custAccountDetails = new CustomerAccountsDetails();
        CompletableFuture<String> toAccountResponse = accountsDetailsProvider.retrieveToAccountDetails();
        CompletableFuture<String> fromAccountResponse = accountsDetailsProvider.retrieveFromAccountsDetails();
        CustomerAccountsDetails accountsDetails = CompletableFuture.allOf(toAccountResponse, fromAccountResponse).thenApply(accounts -> {
            String toAccountDetails = toAccountResponse.join();
            String fromAccountDetails = fromAccountResponse.join();
            custAccountDetails.setToAccounts(toAccountDetails);
            custAccountDetails.setFromAccounts((fromAccountDetails));
            return custAccountDetails;
        }).join();
        return accountsDetails;
    }

}
