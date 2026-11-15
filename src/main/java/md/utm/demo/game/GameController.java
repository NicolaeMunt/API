package md.utm.demo.game;

import jakarta.validation.Valid;
import md.utm.demo.game.dto.CreateGameRequest;
import md.utm.demo.game.dto.GameResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public Page<GameResponse> list(@RequestParam(required = false) Genre genre,
                                   Pageable pageable) {
        return service.findAll(genre, pageable);
    }

    @GetMapping("/{id}")
    public GameResponse get(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponse create(@Valid @RequestBody CreateGameRequest request) {
        return service.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}