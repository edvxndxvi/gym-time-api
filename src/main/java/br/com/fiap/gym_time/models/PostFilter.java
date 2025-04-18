package br.com.fiap.gym_time.models;

import java.time.LocalDate;

public record PostFilter(
    String content,
    LocalDate date
) {}
    
