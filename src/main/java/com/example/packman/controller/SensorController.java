package com.example.packman.controller;

import com.example.packman.request.SensorRequest;
import com.example.packman.service.SensorService;
import com.example.packman.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/packman/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping(value = "/save/data")
    ResponseEntity<BaseResponse> saveData(@RequestBody SensorRequest sensorRequest) {
        BaseResponse response = BaseResponse.builder()
                .data(sensorService.addData(sensorRequest))
                .success(true).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/list/data")
    ResponseEntity<BaseResponse> getDataList() {
        BaseResponse response = BaseResponse.builder()
                .data(sensorService.getListData())
                .success(true).build();
        return ResponseEntity.ok(response);
    }
    //Grouped Data by Date
    @GetMapping(value = "/list/data/date")
    ResponseEntity<BaseResponse> getDataByDateList() {
        BaseResponse response = BaseResponse.builder()
                .data(sensorService.getListDataGroupByDate())
                .success(true).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/detail/data/{date}")
    ResponseEntity<BaseResponse> getDataByDate(@PathVariable String date) {
        BaseResponse response = BaseResponse.builder()
                .data(sensorService.getDetailDate(date))
                .success(true).build();
        return ResponseEntity.ok(response);
    }
}
