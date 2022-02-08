package com.example.platformaticketing.repo;

import com.example.platformaticketing.model.Film;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, Long> {
}
