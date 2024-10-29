package com.example.packman.service;

import com.example.packman.model.view.SensorView;
import com.example.packman.model.Sensor;
import com.example.packman.repository.SensorRepository;
import com.example.packman.request.SensorRequest;
import com.example.packman.response.DataGroupResponse;
import com.example.packman.response.DataResponse;
import com.example.packman.response.DetailDateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    public String addData(SensorRequest sensorRequest) {
        Sensor sensor = Sensor.builder()
                .id(UUID.randomUUID().toString())
                .ph(sensorRequest.getPh())
                .temperature(sensorRequest.getTemperature())
                .date(LocalDateTime.now()).build();
        sensorRepository.save(sensor);
        return "Data added";
    }

    public List<DataResponse> getListData() {
        List<Sensor> sensorList = sensorRepository.findAll();
        return sensorList.stream().map(sensor -> DataResponse.builder()
                .id(sensor.getId())
                .ph(sensor.getPh())
                .temperature(sensor.getTemperature())
                .dateTime(sensor.getDate())
                .build()).collect(Collectors.toList());
    }

    public List<DataGroupResponse> getListDataGroupByDate() {
        List<SensorView> sensorViewList = sensorRepository.findAllGroupByDate();
        return sensorViewList.stream().map(sensor -> DataGroupResponse.builder()
                .ph(sensor.getPh())
                .temperature(sensor.getTemp())
                .dateTime(sensor.getTime())
                .build()).collect(Collectors.toList());
    }

    public DetailDateResponse getDetailDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);
        System.out.println(dateTime);
        SensorView sensorView = sensorRepository.findGroupByDate(dateTime);
        List<Sensor> sensorList = sensorRepository.findAllByDate(dateTime);
        List<DataResponse> sensorData = sensorList.stream().map(data -> DataResponse.builder()
                .id(data.getId())
                .dateTime(data.getDate())
                .ph(data.getPh())
                .temperature(data.getTemperature())
                .build()).collect(Collectors.toList());
        return DetailDateResponse.builder()
                .dateTime(sensorView.getTime())
                .ph(sensorView.getPh())
                .temperature(sensorView.getTemp())
                .data(sensorData).build();
    }
}
