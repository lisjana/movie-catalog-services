package learning.Liso.moviecatalogservices.resources.resources;


import learning.Liso.moviecatalogservices.resources.models.CatalogItem;
import learning.Liso.moviecatalogservices.resources.models.Movie;
import learning.Liso.moviecatalogservices.resources.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    public ResponseEntity<List<CatalogItem>> getCatalog(String userId){
//        ResponseEntity<List<CatalogItem>> re = new ResponseEntity<List<CatalogItem>>(Collections.singletonList(
//                 new CatalogItem("Shpia e endrrave", "test", 4)
//        ),HttpStatus.OK);
//        return re;
//    }

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        //get all rated movie IDs
        List<Rating> ratings = Arrays.asList(
            new Rating("1234", 4),
            new Rating("2345", 3),
            new Rating("174734", 2)
        );
        //for each movie ID, call movie info service and get details
        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" +rating.getMovieId(), Movie.class);
                return new CatalogItem(movie.getName(), "blah blah", rating.getRating());
        })
        .collect(Collectors.toList());



        //put them all together


        //first
//        return Collections.singletonList(
//                new CatalogItem("Beni eci vet", "test", 4)
//        );
    }
}
