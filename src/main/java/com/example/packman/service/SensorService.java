package com.example.packman.service;

import com.example.packman.model.view.SensorView;
import com.example.packman.model.Sensor;
import com.example.packman.repository.SensorRepository;
import com.example.packman.request.SensorRequest;
import com.example.packman.response.DataGroupResponse;
import com.example.packman.response.DataResponse;
import com.example.packman.response.DetailDateResponse;
import com.example.packman.util.DateUtil;
import com.example.packman.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
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
                .date(new Date()).build();
        sensorRepository.save(sensor);
        return "Data added";
    }

    public List<DataResponse> getListData() {
        List<Sensor> sensorList = sensorRepository.findAll();
        return sensorList.stream().map(sensor -> DataResponse.builder()
                .id(sensor.getId())
                .ph(NumberUtil.convertAfterComma(sensor.getPh(), 2))
                .temperature(NumberUtil.convertAfterComma(sensor.getTemperature(), 2))
                .dateTime(sensor.getDate())
                .build()).collect(Collectors.toList());
    }

    public List<DataGroupResponse> getListDataGroupByDate() {
        List<SensorView> sensorViewList = sensorRepository.findAllGroupByDate();
        return sensorViewList.stream().map(sensor -> DataGroupResponse.builder()
                .ph(NumberUtil.convertAfterComma(sensor.getPh(), 2))
                .temperature(NumberUtil.convertAfterComma(sensor.getTemp(), 2))
                .dateTime(sensor.getTime())
                .build()).collect(Collectors.toList());
    }

    public DetailDateResponse getDetailDate(String dateString) {
        Date parsedDate = DateUtil.convertStringToDate(dateString);
        SensorView sensorView = sensorRepository.findGroupByDate(parsedDate);
        List<Sensor> sensorList = sensorRepository.findAllByDate(parsedDate);
        List<DataResponse> sensorData = sensorList.stream().map(data -> DataResponse.builder()
                .id(data.getId())
                .dateTime(data.getDate())
                .ph(NumberUtil.convertAfterComma(data.getPh(), 2))
                .temperature(NumberUtil.convertAfterComma(data.getTemperature(), 2))
                .build()).collect(Collectors.toList());
        return DetailDateResponse.builder()
                .dateTime(sensorView.getTime())
                .ph(NumberUtil.convertAfterComma(sensorView.getPh(), 2))
                .temperature(NumberUtil.convertAfterComma(sensorView.getTemp(), 2))
                .data(sensorData).build();
    }
}
