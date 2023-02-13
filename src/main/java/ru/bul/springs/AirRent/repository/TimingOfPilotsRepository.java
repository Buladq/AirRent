package ru.bul.springs.AirRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bul.springs.AirRent.models.TimingOfPilots;

@Repository
public interface TimingOfPilotsRepository extends JpaRepository<TimingOfPilots,Integer> {
}
