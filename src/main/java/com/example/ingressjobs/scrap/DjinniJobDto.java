package com.example.ingressjobs.scrap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DjinniJobDto {
    private String title;
    private String companyName;
    private String locationText;
    private String postedDate;
    private String jobLink;
}