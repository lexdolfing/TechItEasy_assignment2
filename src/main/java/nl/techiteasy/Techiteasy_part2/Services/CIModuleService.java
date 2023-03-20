package nl.techiteasy.Techiteasy_part2.Services;

import nl.techiteasy.Techiteasy_part2.Controllers.CIModuleController;
import nl.techiteasy.Techiteasy_part2.Dto.InputDto.TelevisionInputDto;
import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.CIModuleDto;
import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.TelevisionDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Models.CIModule;
import nl.techiteasy.Techiteasy_part2.Models.CIModule;
import nl.techiteasy.Techiteasy_part2.Models.Television;
import nl.techiteasy.Techiteasy_part2.Repositories.CIModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CIModuleService {
    private final CIModuleRepository repos;

    public CIModuleService(CIModuleRepository repos) {
        this.repos = repos;
    }

    public List<CIModuleDto> getAllCIModules() {
        Iterable<CIModule> allCIModules = repos.findAll();
        List<CIModuleDto> allCIModulesDto = new ArrayList<>();

        for (CIModule t : allCIModules) {
            CIModuleDto ciModuleDto;
            ciModuleDto = makeTheDto(t);
            allCIModulesDto.add(ciModuleDto);
        }
        return allCIModulesDto;
    }

    public CIModuleDto getCIModuleById(Long id) {
        Optional<CIModule> ciModuleOptional = repos.findById(id);

        if (ciModuleOptional.isEmpty()){
            throw new RecordNotFoundException("No CI module with this ID in record");
        }
        CIModule t = ciModuleOptional.get();
        CIModuleDto ciModuleDto;
        ciModuleDto = makeTheDto(t);
        return ciModuleDto;
    }

    public CIModuleDto createCIModule(CIModuleDto ciModuleDto) {

        //mapping
        CIModule t = new CIModule();
        t = setOrUpdateCIModelObject(ciModuleDto, t);
        repos.save(t);
        return makeTheDto(t);
    }

    public CIModuleDto updateCIModule (@PathVariable Long id, CIModuleDto ciModuleDto) {
        Optional<CIModule> optionalCIModule = repos.findById(id);
        if (optionalCIModule.isPresent()) {
            CIModule t = optionalCIModule.get();
            t = setOrUpdateCIModelObject(ciModuleDto, t);
            repos.save(t);
            return makeTheDto(t);

        } else {
            throw new RecordNotFoundException("geen CI-module met dit ID gevonden");
        }
    }

    public boolean deleteCIModule(@RequestBody Long id) {
        if (repos.findById(id).isPresent()) {
            repos.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No CI-module found with this id");
        }
    }

    public CIModuleDto makeTheDto (CIModule t) {
        CIModuleDto ciModuleDto = new CIModuleDto();
        ciModuleDto.id = t.getId();
        ciModuleDto.type = t.getType();
        ciModuleDto.name = t.getName();
        ciModuleDto.price = t.getPrice();
        return ciModuleDto;
    }

    public CIModule setOrUpdateCIModelObject (CIModuleDto ciModuleDto, CIModule t) {
        t.setType(ciModuleDto.type);
        t.setName(ciModuleDto.name);
        t.setPrice(ciModuleDto.price);
        return t;
    }


}
