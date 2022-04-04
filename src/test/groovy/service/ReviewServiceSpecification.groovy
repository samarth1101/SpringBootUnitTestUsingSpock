package service

import review.dao.ReviewRepository
import review.domain.Review
import review.domain.ReviewEntry
import review.service.ReviewService
import review.service.ReviewServiceImpl
import spock.lang.Specification

class ReviewServiceSpecification extends Specification {

        ReviewRepository reviewRepo
        ReviewService reviewService

        def setup() {
            reviewRepo = Stub(ReviewRepository)
            reviewService = new ReviewServiceImpl(reviewRepo)
        }

        def "should be able to Save Review"() {
            // given -> setup -> Arrange , Mock the review
            given: "A review with some mock data"

            def review = new Review("R1", 1, 1)
            def reviewEntry = new ReviewEntry("John", new Date(), "Product is useful")
            def entries = new ArrayList()
            entries.add(reviewEntry)
            review.setEntries(entries)

            and: "an Review Repository that always returns the mock review"
            reviewRepo.save(review) >> review

            // when -> Stimulus -> Act
            when: "the review is saved"
            def reviewResponse = reviewService.save(review)

            // then -> Response -> Assert
            then: "the same review should be returned"
            //1 * reviewRepo.save(review)
            reviewResponse.id == "R1"
        }

    def "should be able to find review" () {

        given : "A review with some mock data"
        def mockReview = new Review("R1", 1, 1)
        Date now = new Date()
        mockReview.getEntries().add(new ReviewEntry("test-user", now , "Great Product"))

        and: "an Review Repository that always returns the mock review"
        reviewRepo.findById("R1") >> Optional.of(mockReview)

        when: "the given review is requested"
        def returnedReview = reviewService.findById("R1")

        then: "the requested review is returned "
        returnedReview.isPresent() == true
        returnedReview.get() == mockReview

    }

    def "should not be able to find review"() {
        given: "review does not exist"
        reviewRepo.findById("1") >> Optional.empty()


        when: "service is executed to find review"
        def returnedReview =  reviewService.findById("1")

        then: "service should return empty review"
        returnedReview.isPresent() == false
    }

    def "should be able to find all reviews"() {
        given:
        def mockReview1 = new Review("R1", 1, 1)
        def mockReview2 = new Review("R2", 2, 1)
        reviewRepo.findAll() >> Arrays.asList(mockReview1, mockReview2)

        when:
        def returnedReviews = reviewService.findAll()

        then:
        returnedReviews.size() == 2
    }
}
