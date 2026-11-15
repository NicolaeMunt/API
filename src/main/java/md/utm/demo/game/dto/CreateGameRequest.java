package md.utm.demo.game.dto;

import jakarta.validation.constraints.*;
import md.utm.demo.game.Genre;

public record CreateGameRequest(
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 120) String developer,
        @NotNull Genre genre,
        @Min(1958) @Max(2100) int releaseYear
) { }