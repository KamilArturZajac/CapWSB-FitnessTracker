package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingRestController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDTO> getAllActiveTrainings() {
        return trainingService.getAllActiveTrainings()
                .stream()
                .map(trainingMapper::todto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingDTO> getAllUserTrainingsByUserId(@PathVariable long userId) {
        return trainingService.getAllUserTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::todto)
                .toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDTO> getAllTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.getAllTrainingsByActivityType(activityType)
                .stream()
                .map(trainingMapper::todto)
                .toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDTO> getAllFinishedTrainings(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date afterTime) {
        return trainingService.getAllFinishedTrainings(afterTime)
                .stream()
                .map(trainingMapper::todto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training createNewTraining(@RequestBody com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO trainingDto) {
        return trainingService.createNewTraining(trainingDto);
    }

    @PutMapping("/{trainingId}")
    public Training updateExistingTraining(@RequestBody com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO trainingDto, @PathVariable long trainingId) {
        return trainingService.updateExistingTraining(trainingDto, trainingId);
    }

}
