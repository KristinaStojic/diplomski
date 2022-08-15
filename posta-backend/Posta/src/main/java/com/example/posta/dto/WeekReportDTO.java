package com.example.posta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WeekReportDTO {
    private String startDate;
    private String endDate;
}
