package nl.techiteasy.Techiteasy_part2.Services;

import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.RemoteControllerDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Models.RemoteController;
import nl.techiteasy.Techiteasy_part2.Repositories.RemoteControllerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RemoteControllerService {
    private final RemoteControllerRepository repos;

    public RemoteControllerService(RemoteControllerRepository repos) {
        this.repos = repos;
    }

    public List<RemoteControllerDto> getAllRemoteControllers() {
        Iterable<RemoteController> allRemoteControllers = repos.findAll();
        List<RemoteControllerDto> allRemoteControllersDto = new ArrayList<>();

        for (RemoteController t : allRemoteControllers) {
            RemoteControllerDto remoteControllerDto;
            remoteControllerDto = makeTheDto(t);
            allRemoteControllersDto.add(remoteControllerDto);
        }
        return allRemoteControllersDto;
    }

    public RemoteControllerDto getRemoteControllerById(Long id) {
        Optional<RemoteController> remoteControllerOptional = repos.findById(id);

        if (remoteControllerOptional.isEmpty()){
            throw new RecordNotFoundException("No CI module with this ID in record");
        }
        RemoteController t = remoteControllerOptional.get();
        RemoteControllerDto remoteControllerDto;
        remoteControllerDto = makeTheDto(t);
        return remoteControllerDto;
    }

    public RemoteControllerDto createRemoteController(RemoteControllerDto remoteControllerDto) {

        //mapping
        RemoteController t = new RemoteController();
        t = setOrUpdateCIModelObject(remoteControllerDto, t);
        repos.save(t);
        return makeTheDto(t);
    }

    public RemoteControllerDto updateRemoteController (@PathVariable Long id, RemoteControllerDto remoteControllerDto) {
        Optional<RemoteController> optionalRemoteController = repos.findById(id);
        if (optionalRemoteController.isPresent()) {
            RemoteController t = optionalRemoteController.get();
            t = setOrUpdateCIModelObject(remoteControllerDto, t);
            repos.save(t);
            return makeTheDto(t);

        } else {
            throw new RecordNotFoundException("geen CI-module met dit ID gevonden");
        }
    }

    public boolean deleteRemoteController(@RequestBody Long id) {
        if (repos.findById(id).isPresent()) {
            repos.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No CI-module found with this id");
        }
    }

    public RemoteControllerDto makeTheDto (RemoteController t) {
        RemoteControllerDto remoteControllerDto = new RemoteControllerDto();
        remoteControllerDto.id = t.getId();
        remoteControllerDto.compatibleWith = t.getCompatibleWith();
        remoteControllerDto.batteryType = t.getBatteryType();
        remoteControllerDto.brand = t.getBrand();
        remoteControllerDto.originalStock = t.getOriginalStock();
        remoteControllerDto.name = t.getName();
        remoteControllerDto.price = t.getPrice();
        return remoteControllerDto;
    }

    public RemoteController setOrUpdateCIModelObject (RemoteControllerDto remoteControllerDto, RemoteController t) {
        t.setCompatibleWith(remoteControllerDto.compatibleWith);
        t.setBatteryType(remoteControllerDto.batteryType);
        t.setName(remoteControllerDto.name);
        t.setBrand(remoteControllerDto.brand);
        t.setPrice(remoteControllerDto.price);
        t.setOriginalStock(remoteControllerDto.originalStock);
        return t;
    }


}
