package com.example.packman.repository;

import com.example.packman.model.view.SensorView;
import com.example.packman.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, String> {

    @Query("SELECT DATE(s.date) as time, AVG(s.ph) as ph, AVG(s.temperature) as temp FROM Sensor s GROUP BY DATE(s.date)")
    List<SensorView> findAllGroupByDate();

    @Query("SELECT DATE(s.date) as time, AVG(s.ph) as ph, AVG(s.temperature) as temp FROM Sensor s WHERE DATE(s.date) = :date GROUP BY DATE(s.date)")
    SensorView findGroupByDate(@Param("date") Date date);


    @Query("SELECT S FROM Sensor S WHERE DATE(S.date) = :date ")
    List<Sensor> findAllByDate(Date date);

    @Query("SELECT S FROM Sensor S ORDER BY S.date DESC LIMIT 10")
    List<Sensor> findLastData();
}
