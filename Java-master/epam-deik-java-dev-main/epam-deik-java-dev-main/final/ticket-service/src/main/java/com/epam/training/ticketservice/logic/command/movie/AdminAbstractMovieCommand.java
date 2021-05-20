package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.logic.command.AdminAbstract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AdminAbstractMovieCommand extends AdminAbstract {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PriceComponentRepository priceComponentRepository;

    @Autowired
    String title;

    @Autowired
    String genre;

    @Autowired
    String durationInMinutes;

    @Autowired
    Set<MovieProperties> changes;

    @Autowired
    String name;

    private List<PriceComponent> components;

    private String type = "movie";

    private Movie movie;

    private PriceComponent priceComponent;

    private int durationInMinutes_int;

    private String invalidError;

    private String badString;

    protected boolean setProperties() {
        components = movie.getComponents();
        if (!components.contains(priceComponent)) {
            genre = movie.getGenre();
            durationInMinutes_int = movie.getDurationInMinutes();
            components.add(priceComponent);
            return true;
        } else {
            return false;
        }
    }

    protected void getComponents() {
        components = movie.getComponents();
    }

    protected void setComponents() {
        components =  new ArrayList<PriceComponent>();
    }

    protected void convertDurationInMinutes() {
        durationInMinutes_int = convertToInt(durationInMinutes);
    }

    protected String getBad_string() {
        return badString;
    }

    protected boolean isConvert() {
        badString = validConvertToInt(durationInMinutes);
        if (emptyString.equals(badString)) {
            convertDurationInMinutes();
            return false;
        }
        return true;
    }

    protected boolean isValid() {
        return movieRepository.findByTitle(title) != null;
    }

    protected boolean isValid_Attachment() {
        invalidError = emptyString;
        movie = movieRepository.findByTitle(title);
        priceComponent = priceComponentRepository.findByName(name);
        if (movie == null) {
            invalidError += MovieProperties.movie.toString();
            if (priceComponent == null) {
                invalidError += " " + MovieProperties.price_component.toString();
            }
            return false;
        } else if(priceComponent == null) {
            invalidError += MovieProperties.price_component.toString();
            return false;
        } else{
            return true;
        }
    }

    protected String getInValidError() {
        return invalidError;
    }

    protected Movie getMovieByTitle() {
        return movieRepository.findByTitle(title);
    }

    protected void delete() {
        movieRepository.delete(movie);
    }

    protected void save() {
        movieRepository.save(new Movie(title, genre, durationInMinutes_int, components));
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    protected String modifiedProperties() {
        StringBuilder result = new StringBuilder();
        for(MovieProperties change : changes) {
            result.append(capitalize((change.toString()))).append(" ");
        }
        return result.toString();
    }
}

