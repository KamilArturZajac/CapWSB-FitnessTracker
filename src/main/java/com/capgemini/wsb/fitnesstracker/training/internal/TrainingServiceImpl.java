package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.api.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
class TrainingServiceImpl implements TrainingProvider {

    private static final Logger log = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> getAllActiveTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getAllUserTrainingsByUserId(long userId) {
        return trainingRepository.getAllUserTrainingsByUserId(userId);
    }

    @Override
    public List<Training> getAllTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.getAllTrainingsByActivityType(activityType);
    }

    @Override
    public List<Training> getAllFinishedTrainings(Date afterTime) {
        return trainingRepository.getAllFinishedTrainings(afterTime);
    }

    @Override
    public Training createNewTraining(TrainingDTO trainingDto) {
        log.info("Creating new training {}", trainingDto);
        if (trainingDto.getId() != null) {
            throw new IllegalArgumentException("NOT PERMITETED TO EXECUTE THIS OPERATION! Training already in DB ID");
        }
        User user = userProvider.getUser(trainingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(trainingDto.getUserId()));
        return trainingRepository.save(trainingMapper.toEntity(trainingDto, user));
    }

    @Override
    public Training updateExistingTraining(TrainingDTO trainingDto, long trainingId) {
        log.info("Updating existing training {}", trainingId);
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));

        Training updatedUser = trainingMapper.updateEntity(training, trainingDto);

        return trainingRepository.save(updatedUser);
    }
}
