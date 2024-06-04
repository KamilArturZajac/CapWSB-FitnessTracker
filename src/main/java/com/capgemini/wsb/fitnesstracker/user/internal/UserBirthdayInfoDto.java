package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

record UserBirthdayInfoDto(Long id, @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthday) {
}
