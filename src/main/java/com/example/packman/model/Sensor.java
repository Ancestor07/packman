package com.example.packman.model;

 import jakarta.persistence.*;
 import lombok.*;

 import java.time.LocalDateTime;
 import java.util.Date;

@Entity
@Table(name = "sensor")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "date")
    private Date date;

    @Column(name = "ph")
    private Double ph;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "sensor_id")
    private String sensorId;
}
