package review.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import review.domain.Review;

import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findByProductId(Integer id);
}
