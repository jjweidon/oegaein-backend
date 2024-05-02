package com.likelion.oegaein.profile.repository;

import com.likelion.oegaein.domain.member.entity.review.Review;
import com.likelion.oegaein.domain.member.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_FindAllByReceiver_ReturnSaveReview() {

        // Arrange
        Review review = Review.builder()

                .build();

        // Act

        // Assert

    }
}
