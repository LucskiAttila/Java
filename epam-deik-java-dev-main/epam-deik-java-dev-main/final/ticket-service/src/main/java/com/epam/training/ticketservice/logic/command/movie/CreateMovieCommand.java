package com.epam.training.ticketservice.logic.command.movie;


public class CreateMovieCommand extends AdminAbstractMovieCommand {

    private final String title;
    private final String genre;
    private final String durationInMinutes;

    public CreateMovieCommand(String title, String genre, String durationInMinutes) {
        this.title = title;
        this.genre = genre;
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    protected String operate() {
        if(isConvert()) {
            if (!isValid()) {
                setComponents();
                save();
                return createTrue();
            } else {
                return createFalse();
            }
        } else {
            return invalidUpdate(getBad_string());
        }
    }
}
