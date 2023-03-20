package nl.techiteasy.Techiteasy_part2.Controllers;

import nl.techiteasy.Techiteasy_part2.Dto.InputDto.TelevisionInputDto;
import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.CIModuleDto;
import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.TelevisionDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Services.CIModuleService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cimodules")
public class CIModuleController {
    private final CIModuleService ciModuleservice;

    public CIModuleController (CIModuleService ciModuleservice) {
        this.ciModuleservice = ciModuleservice;
    }

    @GetMapping()
    public ResponseEntity<List<CIModuleDto>> getAllCIModules() {
        List<CIModuleDto> ciModuleDto = ciModuleservice.getAllCIModules();
        return ResponseEntity.ok(ciModuleDto);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<CIModuleDto> getCIModuleById(@PathVariable Long id) {
        CIModuleDto ciModuleDto = ciModuleservice.getCIModuleById(id);
        return ResponseEntity.ok(ciModuleDto);
    }

    @PostMapping()
    public ResponseEntity<CIModuleDto> createCIModule (@RequestBody CIModuleDto dto) {
        CIModuleDto ciModuleDto = ciModuleservice.createCIModule(dto);
        return ResponseEntity.ok(ciModuleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeCIModule (@PathVariable Long id) {
        boolean isDeleted = ciModuleservice.deleteCIModule(id);
        if(isDeleted) {
            return ResponseEntity.ok().body("Element is deleted");
        } else {
            throw new RecordNotFoundException("No element found with this ID code");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CIModuleDto> updateCIModule(@PathVariable Long id, @RequestBody CIModuleDto dto) {
        CIModuleDto ciModuleDto = ciModuleservice.updateCIModule(id, dto);
        return ResponseEntity.ok().body(ciModuleDto);
    }




}
