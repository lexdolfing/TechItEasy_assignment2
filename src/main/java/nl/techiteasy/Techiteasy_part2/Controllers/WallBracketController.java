package nl.techiteasy.Techiteasy_part2.Controllers;

import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.WallBracketDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Services.WallBracketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wallbrackets")
public class WallBracketController {
    private final WallBracketService wallBracketservice;

    public WallBracketController (WallBracketService wallBracketservice) {
        this.wallBracketservice = wallBracketservice;
    }

    @GetMapping()
    public ResponseEntity<List<WallBracketDto>> getAllWallBrackets() {
        List<WallBracketDto> wallBracketDto = wallBracketservice.getAllWallBrackets();
        return ResponseEntity.ok(wallBracketDto);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<WallBracketDto> getWallBracketById(@PathVariable Long id) {
        WallBracketDto wallBracketDto = wallBracketservice.getWallBracketById(id);
        return ResponseEntity.ok(wallBracketDto);
    }

    @PostMapping()
    public ResponseEntity<WallBracketDto> createWallBracket (@RequestBody WallBracketDto dto) {
        WallBracketDto wallBracketDto = wallBracketservice.createWallBracket(dto);
        return ResponseEntity.ok(wallBracketDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeWallBracket (@PathVariable Long id) {
        boolean isDeleted = wallBracketservice.deleteWallBracket(id);
        if(isDeleted) {
            return ResponseEntity.ok().body("Element is deleted");
        } else {
            throw new RecordNotFoundException("No element found with this ID code");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WallBracketDto> updateWallBracket(@PathVariable Long id, @RequestBody WallBracketDto dto) {
        WallBracketDto wallBracketDto = wallBracketservice.updateWallBracket(id, dto);
        return ResponseEntity.ok().body(wallBracketDto);
    }

}
