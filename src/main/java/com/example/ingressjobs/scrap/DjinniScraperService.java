package com.example.ingressjobs.scrap;

import com.example.ingressjobs.enums.JobType;
import com.example.ingressjobs.model.Job;
import com.example.ingressjobs.model.Location;
import com.example.ingressjobs.enums.WorkType;
import com.example.ingressjobs.repository.JobRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DjinniScraperService {

    private final JobRepository jobRepository;

    public DjinniScraperService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void scrapeAndSaveJobs() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://djinni.co/jobs/?keywords=remote");
        Thread.sleep(3000);
        WebDriverManager.chromedriver().setup();


        List<WebElement> jobElements = driver.findElements(By.cssSelector(".job-list-item"));
        for (WebElement jobElement : jobElements) {
            String title = jobElement.findElement(By.cssSelector("a.job-list-item__link")).getText();
            String companyName = jobElement.findElement(By.cssSelector(".company-name")).getText();
            String locationText = jobElement.findElement(By.cssSelector(".location-text")).getText();
            String postedDateText = jobElement.findElement(By.cssSelector(".text-muted")).getText();
            String jobLink = jobElement.findElement(By.cssSelector("a.job-list-item__link")).getAttribute("href");

            DjinniJobDto jobDto = DjinniJobDto.builder()
                    .title(title)
                    .companyName(companyName)
                    .locationText(locationText)
                    .postedDate(postedDateText)
                    .jobLink(jobLink)
                    .build();

            Job job = Job.builder()
                    .jobTittle(jobDto.getTitle())
                    .companyName(jobDto.getCompanyName())
                    .location(parseLocationFromText(jobDto.getLocationText()))
                    .jobType(JobType.REMOTE)
                    .postedDate(parseDate(jobDto.getPostedDate()))
                    .source("djinni.co")
                    .jobDescription("Scraped from Djinni")
                    .tags("djinni")
                    .build();

            jobRepository.save(job);
        }

        driver.quit();

    }

    private Location parseLocationFromText(String locationText) {
        Location location = new Location();
        location.setCountry(extractCountry(locationText));
        location.setWorkType(WorkType.REMOTE);
        return location;
    }

    private String extractCountry(String locationText) {
        if (locationText.contains("Azerbaijan")) {
            return "Azerbaijan";
        }
        return "Unknown";
    }

    private LocalDate parseDate(String rawText) {
        try {
            if (rawText.contains("days ago")) {
                int daysAgo = Integer.parseInt(rawText.split(" ")[0]);
                return LocalDate.now().minusDays(daysAgo);
            }
        } catch (Exception e) {
            return LocalDate.now();
        }
        return LocalDate.now();
    }
}
