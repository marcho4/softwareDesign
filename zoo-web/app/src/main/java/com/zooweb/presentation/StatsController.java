package com.zooweb.presentation;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.zooweb.domain.IAnimal;
import com.zooweb.domain.IEnclosure;
import com.zooweb.domain.IFeedingSchedule;


@RestController
@RequestMapping("/api/stats")
public class StatsController {
    
    @GetMapping("/animals/all")
    public List<IAnimal> getAnimalStats() {
        return null;
    }
    
    @GetMapping("/animals")
    public ResponseEntity<String> getAnimalsStats() {
        return ResponseEntity.ok("Animals stats");
    }


    @GetMapping("/feeding-schedule")
    public ResponseEntity<String> getFeedingScheduleStats() {
        return ResponseEntity.ok("Feeding schedule stats");
        
    }

    @GetMapping("/feeding-schedule/all")
    public ResponseEntity<List<IFeedingSchedule>> getAllFeedingSchedules() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/enclosures")
    public ResponseEntity<String> getEnclosuresStats() {
        return ResponseEntity.ok("Enclosures stats");
    }

    @GetMapping("/enclosures/all")
    public ResponseEntity<List<IEnclosure>> getAllEnclosures() {
        return ResponseEntity.ok(null);
    }
}
