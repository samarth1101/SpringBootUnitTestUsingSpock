package api

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import review.api.ReviewController
import review.domain.Review
import review.service.ReviewService
import spock.lang.Specification

class ReviewControllerTest extends Specification {

    ReviewController reviewController
    ReviewService reviewService
    MockMvc mockMvc

    def setup() {
        reviewService = Mock(ReviewService)
        reviewController = new ReviewController(reviewService)
        mockMvc =  MockMvcBuilders.standaloneSetup(reviewController).build()
    }


    def "should be able to return review when exist"() {

        given:
        def mockReview = new Review("R1", 1, 1)
        reviewService.findById("R1") >> Optional.of(mockReview)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/review/{id}", "R1")).andReturn()

        then:
        result.response.status == 200

    }


}
