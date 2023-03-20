package nl.techiteasy.Techiteasy_part2.Services;

import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.WallBracketDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Models.WallBracket;
import nl.techiteasy.Techiteasy_part2.Repositories.WallBracketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WallBracketService {
    private final WallBracketRepository repos;

    public WallBracketService(WallBracketRepository repos) {
        this.repos = repos;
    }

    public List<WallBracketDto> getAllWallBrackets() {
        Iterable<WallBracket> allWallBrackets = repos.findAll();
        List<WallBracketDto> allWallBracketsDto = new ArrayList<>();

        for (WallBracket t : allWallBrackets) {
            WallBracketDto wallBracketDto;
            wallBracketDto = makeTheDto(t);
            allWallBracketsDto.add(wallBracketDto);
        }
        return allWallBracketsDto;
    }

    public WallBracketDto getWallBracketById(Long id) {
        Optional<WallBracket> wallBracketOptional = repos.findById(id);

        if (wallBracketOptional.isEmpty()){
            throw new RecordNotFoundException("No CI module with this ID in record");
        }
        WallBracket t = wallBracketOptional.get();
        WallBracketDto wallBracketDto;
        wallBracketDto = makeTheDto(t);
        return wallBracketDto;
    }

    public WallBracketDto createWallBracket(WallBracketDto wallBracketDto) {

        //mapping
        WallBracket t = new WallBracket();
        t = setOrUpdateCIModelObject(wallBracketDto, t);
        repos.save(t);
        return makeTheDto(t);
    }

    public WallBracketDto updateWallBracket (@PathVariable Long id, WallBracketDto wallBracketDto) {
        Optional<WallBracket> optionalWallBracket = repos.findById(id);
        if (optionalWallBracket.isPresent()) {
            WallBracket t = optionalWallBracket.get();
            t = setOrUpdateCIModelObject(wallBracketDto, t);
            repos.save(t);
            return makeTheDto(t);

        } else {
            throw new RecordNotFoundException("geen Wall Bracket met dit ID gevonden");
        }
    }

    public boolean deleteWallBracket(@RequestBody Long id) {
        if (repos.findById(id).isPresent()) {
            repos.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No WallBracket found with this id");
        }
    }

    public WallBracketDto makeTheDto (WallBracket t) {
        WallBracketDto wallBracketDto = new WallBracketDto();
        wallBracketDto.id = t.getId();
        wallBracketDto.size=t.getSize();
        wallBracketDto.adjustable=t.getAdjustable();
        wallBracketDto.name = t.getName();
        wallBracketDto.price = t.getPrice();
        return wallBracketDto;
    }

    public WallBracket setOrUpdateCIModelObject (WallBracketDto wallBracketDto, WallBracket t) {
        t.setSize(wallBracketDto.size);
        t.setAdjustable(wallBracketDto.adjustable);
        t.setName(wallBracketDto.name);
        t.setPrice(wallBracketDto.price);

        return t;
    }
}
