package com.likelion.oegaein.domain.news.util;

import com.likelion.oegaein.domain.news.dto.CreateNewsData;
import com.likelion.oegaein.domain.news.dto.CreateNewsRequest;
import com.likelion.oegaein.domain.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsCrawler {
    private WebDriver webDriver;
    private final NewsService newsService;
    private static final String url = "https://builder.hufs.ac.kr/user/indexSub.action?codyMenuSeq=103803938&siteId=mhdorm3&menuType=T&uId=13&sortChar=A&linkUrl=5_1.html&mainFrame=right";

    @Scheduled(cron = "0 * * * * *") // 초 분 시 일 월 년
    public void newsCrawling(){
        // webdriver config
        System.setProperty("webdriver.chrome.driver", "/app/chrome/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        webDriver = new ChromeDriver(options);

        try{
            log.info("Crawling Dorm News");
            getDormNews();
        }catch (InterruptedException e) {
            log.error("Crawler Error");
        }
        webDriver.close();
        webDriver.quit();
    }

    private void getDormNews() throws InterruptedException{
        List<CreateNewsData> dto = new ArrayList<>();
        webDriver.get(url);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(1));
        List<WebElement> noElements = webDriver.findElements(By.cssSelector("#board-container * table td.no > *"));
        List<WebElement> titleElements = webDriver.findElements(By.cssSelector("#board-container * table td.title a"));
        List<WebElement> dateElements = webDriver.findElements(By.cssSelector("#board-container * table tr td:nth-child(4)"));
        for (int idx = 0; idx < titleElements.size();idx++) {
            WebElement noElement = noElements.get(idx);
            WebElement titleElement = titleElements.get(idx);
            WebElement dateElement = dateElements.get(idx);

            // 공지글 제거
            if(noElement.getTagName().equals("img")) continue;

            String no = noElement.getText();
            String title = titleElement.getText();
            String url = titleElement.getAttribute("href");
            LocalDate createdAt = LocalDate.parse(dateElement.getText(), DateTimeFormatter.ISO_DATE);

            CreateNewsData createNewsData = CreateNewsData.builder()
                    .title(title)
                    .url(url)
                    .createdAt(createdAt)
                    .build();
            dto.add(createNewsData);
        }
        CreateNewsRequest request = new CreateNewsRequest(dto);
        updateNews(request);
    }

    private void updateNews(CreateNewsRequest createNewsRequest){
        newsService.removeNewsAll();
        newsService.createNewsAll(createNewsRequest);
    }
}