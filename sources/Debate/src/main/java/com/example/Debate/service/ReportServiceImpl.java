package com.example.Debate.service;

import com.example.Debate.dto.response.ReportDto;
import com.example.Debate.model.Report;
import com.example.Debate.repository.ReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements  ReportService{

    private ReportRepository reportRepository;
    private ModelMapper modelMapper;

    public ReportServiceImpl(ReportRepository reportRepository, ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReportDto getReportById(String id) {
        Report report = reportRepository.findById(id).get();
        return modelMapper.map(report,ReportDto.class);
    }

    @Override
    public boolean addReport(ReportDto reportDto)
    {
        Report report = modelMapper.map(reportDto,Report.class);
        reportRepository.save(report);
        return true;
    }

}
