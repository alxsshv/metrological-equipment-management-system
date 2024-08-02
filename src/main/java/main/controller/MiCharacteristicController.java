package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.rest.MiCharacteristicDto;
import main.service.ServiceMessage;
import main.service.interfaces.MiCharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/mi-characteristics")
public class MiCharacteristicController {
    @Autowired
    private MiCharacteristicService miCharacteristicService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getMiCharacteristic(@PathVariable("id") long id){
        MiCharacteristicDto dto = miCharacteristicService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> addMiCharacteristic (@RequestBody MiCharacteristicDto miCharacteristicDto){
        miCharacteristicService.save(miCharacteristicDto);
        String okMessage = "Характеристика СИ " + miCharacteristicDto.getTitle() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMiCharacteristic(@RequestBody MiCharacteristicDto miCharacteristicDto){
        miCharacteristicService.update(miCharacteristicDto);
        String okMessage = "Характеристика " + miCharacteristicDto.getTitle() + " обновлена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMiCharacteristic(@PathVariable("id") long id){
        miCharacteristicService.delete(id);
        String okMessage ="Запись о характеристике № " + id + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
