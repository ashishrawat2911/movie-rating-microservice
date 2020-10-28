package dev.ashish.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import dev.ashish.moviecatalogservice.model.CatalogItem;
import dev.ashish.moviecatalogservice.model.Movie;
import dev.ashish.moviecatalogservice.model.Rating;
import dev.ashish.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    RestTemplate restTemplate;


    @RequestMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "getFallBackCatalog")
    List<CatalogItem> getCatalog(@PathVariable("userId") String userId) throws Exception {
        UserRating userRating = getUserRating(userId);
        return userRating.getRatings().
                stream()
                .map(rating ->
                        getCatalogItem(rating))
                .collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "getFallBackUserRating")
    UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://movie-rating-service/rating/user/" + userId, UserRating.class);
    }

    @HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
    CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movie/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    CatalogItem getFallBackCatalogItem(Rating rating) {
        return new CatalogItem("", "", 0);
    }

    UserRating getFallBackUserRating(String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(
                new Rating("", 0)
        ));
        return userRating;
    }

    List<CatalogItem> getFallBackCatalog(@PathVariable("userId") String userId) {
        return Collections.singletonList(new CatalogItem("", "", 0));
    }
}

/*

    @Autowired
    WebClient.Builder webClient;

    Movie movie = webClient
                  .build()
                  .get()
                  .uri("http://localhost:8082/movie/" + rating.getMovieId())
                  .retrieve()
                  .bodyToMono(Movie.class)
                  .block();
*/
