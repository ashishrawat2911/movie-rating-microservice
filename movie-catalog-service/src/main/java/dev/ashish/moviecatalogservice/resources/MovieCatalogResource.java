package dev.ashish.moviecatalogservice.resources;

import dev.ashish.moviecatalogservice.model.CatalogItem;
import dev.ashish.moviecatalogservice.model.Movie;
import dev.ashish.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    RestTemplate restTemplate;
//    @Autowired
//    WebClient.Builder webClient;

    @RequestMapping("/{userId}")
    List<CatalogItem> getCatalog(@PathVariable("userId") String userId) throws Exception {

        UserRating userRating = restTemplate.getForObject("http://localhost:8083/rating/users/" + userId, UserRating.class);

        return userRating.getRatings().
                stream()
                .map(rating ->
                {
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movie/" + rating.getMovieId(), Movie.class);
//                    Movie movie = webClient
//                            .build()
//                            .get()
//                            .uri("http://localhost:8082/movie/" + rating.getMovieId())
//                            .retrieve()
//                            .bodyToMono(Movie.class)
//                            .block();
                    return new CatalogItem(movie.getMovieName(), "", rating.getRating());
                })
                .collect(Collectors.toList());
    }

}
