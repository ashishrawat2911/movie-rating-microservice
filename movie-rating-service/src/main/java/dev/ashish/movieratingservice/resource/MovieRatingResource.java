package dev.ashish.movieratingservice.resource;

import dev.ashish.movieratingservice.model.Rating;
import dev.ashish.movieratingservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rating")
public class MovieRatingResource {
    @RequestMapping("/{movieId}")
    Rating getMovieRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("users/{userId}")
    UserRating getUserRating(@PathVariable("userId") String userId) {
        List<Rating> ratingList = Arrays.asList(
                new Rating("aa", 5),
                new Rating("bb", 2)
        );
        return new UserRating(ratingList);

    }
}
