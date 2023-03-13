package nl.techiteasy.Techiteasy_part2.Services;

import nl.techiteasy.Techiteasy_part2.Dto.InputDto.TelevisionInputDto;
import nl.techiteasy.Techiteasy_part2.Dto.OutputDto.TelevisionOutputDto;
import nl.techiteasy.Techiteasy_part2.Exceptions.RecordNotFoundException;
import nl.techiteasy.Techiteasy_part2.Model.Television;
import nl.techiteasy.Techiteasy_part2.Repositories.TelevisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelevisionService {

    private final TelevisionRepository repos;

    public TelevisionService (TelevisionRepository repos){
        this.repos = repos;
    }

    public List<TelevisionOutputDto> getAllTelevisions() {
        Iterable<Television> allTelevisions = repos.findAll();
        List<TelevisionOutputDto> allTelevisionsOutputDto = new ArrayList<>();

        for (Television t: allTelevisions) {
            TelevisionOutputDto teleDto;
            teleDto = makeTheDto(t);
            allTelevisionsOutputDto.add(teleDto);
        }
        return allTelevisionsOutputDto;
    }

    public TelevisionOutputDto getTelevisionById(Long id) {
        Optional<Television> teloptional = repos.findById(id);

        if (teloptional.isEmpty()){
            throw new RecordNotFoundException("No television with this ID in record");
        }
        Television t = teloptional.get();
        TelevisionOutputDto teleDto;
        teleDto = makeTheDto(t);
        return teleDto;

    }

    public TelevisionOutputDto createTelevision(TelevisionInputDto teleInDto) {

        //mapping
        Television t = new Television();
        t = setOrUpdateTelevisionObject(teleInDto, t);
        repos.save(t);
        return makeTheDto(t);
    }

    // PUT mapping
    public TelevisionOutputDto updateTelevision (@PathVariable Long id, TelevisionInputDto teleInDto) {
        Optional<Television> optionalTelevision = repos.findById(id);
        if (optionalTelevision.isPresent()) {
            Television t = optionalTelevision.get();
            t = setOrUpdateTelevisionObject(teleInDto, t);
            repos.save(t);
            return makeTheDto(t);

        } else {
            throw new RecordNotFoundException("geen telvisie met dit ID gevonden");
        }
    }

    public boolean deleteTelevision(@RequestBody Long id) {
        if (repos.findById(id).isPresent()) {
            repos.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No television foud with this id");
        }

    }

    public Television setOrUpdateTelevisionObject (TelevisionInputDto teleInDto,Television t) {
        t.setType(teleInDto.type);
        t.setBrand(teleInDto.brand);
        t.setName(teleInDto.name);
        t.setPrice(teleInDto.price);
        t.setAvailableSize(teleInDto.availableSize);
        t.setRefreshRate(teleInDto.refreshRate);
        t.setScreentype(teleInDto.screentype);
        t.setScreenQuality(teleInDto.screenQuality);
        t.setSmartTv(teleInDto.smartTv);
        t.setWifi(teleInDto.wifi);
        t.setVoiceControl(teleInDto.voiceControl);
        t.setHdr(teleInDto.hdr);
        t.setBleutooth(teleInDto.bleutooth);
        t.setAmbilight(teleInDto.ambilight);
        t.setOriginalStock(teleInDto.originalStock);
        t.setSold(teleInDto.sold);
        return t;
    }

    public TelevisionOutputDto makeTheDto (Television t) {
        TelevisionOutputDto teleDto = new TelevisionOutputDto();
        teleDto.id = t.getId();
        teleDto.type = t.getType();
        teleDto.brand = t.getBrand();
        teleDto.name = t.getName();
        teleDto.price = t.getPrice();
        teleDto.availableSize = t.getAvailableSize();
        teleDto.refreshRate = t.getRefreshRate();
        teleDto.screentype = t.getScreentype();
        teleDto.screenQuality = t.getScreenQuality();
        teleDto.smartTv = t.isSmartTv();
        teleDto.wifi = t.isWifi();
        teleDto.voiceControl = t.isVoiceControl();
        teleDto.hdr = t.isHdr();
        teleDto.bleutooth = t.isBleutooth();
        teleDto.ambilight = t.isAmbilight();
        teleDto.originalStock = t.getOriginalStock();
        teleDto.sold = t.getSold();
        return teleDto;
    }
}
