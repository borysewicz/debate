package com.example.Debate.service;

import com.example.Debate.dto.response.ReportDto;

public interface ReportService {

        public ReportDto getReportById(String id);
        public boolean addReport( ReportDto reportDto);

}
