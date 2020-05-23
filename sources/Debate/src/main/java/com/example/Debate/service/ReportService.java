package com.example.Debate.service;

import com.example.Debate.dto.response.ReportDto;

public interface ReportService {
        ReportDto getReportById(String id);
        boolean addReport(ReportDto reportDto);
}
