package learning.Liso.moviecatalogservices.resources.resources;


import learning.Liso.moviecatalogservices.resources.models.CatalogItem;
import learning.Liso.moviecatalogservices.resources.models.Movie;
import learning.Liso.moviecatalogservices.resources.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

//    public ResponseEntity<List<CatalogItem>> getCatalog(String userId){
//        ResponseEntity<List<CatalogItem>> re = new ResponseEntity<List<CatalogItem>>(Collections.singletonList(
//                 new CatalogItem("Shpia e endrrave", "test", 4)
//        ),HttpStatus.OK);
//        return re;
//    }

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        
        //get all rated movie IDs
        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            //for each movie ID, call movie info service and get details
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" +rating.getMovieId(), Movie.class);
            //put all together
            return new CatalogItem(movie.getName(), movie.getMovieId(), rating.getRating());
        })
        .collect(Collectors.toList());

        //first
//        return Collections.singletonList(
//                new CatalogItem("Beni eci vet", "test", 4)
//        );
    }
}
//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .block();
