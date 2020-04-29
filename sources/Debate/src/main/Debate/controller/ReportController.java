package com.example.Debate.controller;

import com.example.Debate.dto.response.ReportDto;
import com.example.Debate.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getReportById(@PathVariable(value = "id") String id){
        ReportDto reportDto = reportService.getReportById(id);
        if(reportDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(reportDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public HttpStatus addReport(@RequestBody ReportDto reportDto){
        if(reportService.addReport(reportDto))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }

}
