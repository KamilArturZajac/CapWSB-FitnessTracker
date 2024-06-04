package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {

    default List<Training> getAllActiveTrainings() {
        return findAll().stream().toList();
    }

    default List<Training> getAllUserTrainingsByUserId(long userId) {
        return findAll().stream()
                .filter(training -> training.getUser().getId() == userId)
                .toList();
    }

    default List<Training> getAllTrainingsByActivityType(ActivityType activityType) {
        return findAll().stream()
                .filter(training -> training.getActivityType() == activityType)
                .toList();
    }

    default List<Training> getAllFinishedTrainings(Date afterTime) {
        return findAll().stream()
                .filter(training -> training.getEndTime().compareTo(afterTime) > 0)
                .toList();
    }
}
