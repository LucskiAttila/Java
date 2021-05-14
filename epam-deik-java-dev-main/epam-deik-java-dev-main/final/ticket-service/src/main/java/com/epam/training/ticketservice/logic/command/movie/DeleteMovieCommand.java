package com.epam.training.ticketservice.logic.command.movie;

public class DeleteMovieCommand extends AdminAbstractMovieCommand {

    private final String title;

    public DeleteMovieCommand(String title) {
        this.title = title;
    }

    @Override
    public String operate() {
        if(isValid()) {
            delete();
            return deleteTrue();
        }
        else {
            return deleteFalse();
        }
    }
}
