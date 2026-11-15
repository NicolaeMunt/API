package md.utm.demo.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import md.utm.demo.common.DuplicateGameException;
import md.utm.demo.common.NotFoundException;
import md.utm.demo.game.dto.CreateGameRequest;
import md.utm.demo.game.dto.GameResponse;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Page<GameResponse> findAll(Genre genre, Pageable pageable) {
        Page<Game> games = (genre == null)                  
                ? repository.findAll(pageable)
                : repository.findByGenre(genre, pageable);

        return games.map(GameResponse::from);                
    }

    public GameResponse findById(String id) {
        return repository.findById(id)
                .map(GameResponse::from)
                .orElseThrow(() -> new NotFoundException("Game " + id + " not found"));
    }                                                        

    public GameResponse create(CreateGameRequest request) {
        if (repository.existsByTitleIgnoreCaseAndDeveloperIgnoreCase(
                request.title(), request.developer())) {
            throw new DuplicateGameException(request.title());    
        }

        Game game = new Game(request.title(), request.developer(),
                             request.genre(), request.releaseYear());

        return GameResponse.from(repository.save(game));
    }

    public GameResponse update(String id, CreateGameRequest request) {
        Game game = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game " + id + " not found"));

        game.update(request.title(), request.developer(),
                    request.genre(), request.releaseYear());

        return GameResponse.from(repository.save(game));     
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Game " + id + " not found");
        }
        repository.deleteById(id);
    }
}