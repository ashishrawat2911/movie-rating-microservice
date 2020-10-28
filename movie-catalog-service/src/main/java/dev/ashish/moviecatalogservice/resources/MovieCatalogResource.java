package dev.ashish.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import dev.ashish.moviecatalogservice.model.CatalogItem;
import dev.ashish.moviecatalogservice.model.UserRating;
import dev.ashish.moviecatalogservice.service.MovieInfo;
import dev.ashish.moviecatalogservice.service.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MovieInfo movieInfo;
    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "getFallBackCatalog")
    List<CatalogItem> getCatalog(@PathVariable("userId") String userId) throws Exception {
        UserRating userRating = userRatingInfo.getUserRating(userId);
        return userRating.getRatings().
                stream()
                .map(rating ->
                        movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());
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
