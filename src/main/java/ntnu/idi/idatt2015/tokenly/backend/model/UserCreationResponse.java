package ntnu.idi.idatt2015.tokenly.backend.model;

public record UserCreationResponse(String username, String email,
                                   String firstname, String lastname) {
}
