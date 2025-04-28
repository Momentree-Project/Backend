package com.momentree.global.app;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// 사이트 URL, 쿠키 도메인 등 환경설정값을 관리하는 AppConfig 클래스
@Configuration
public class AppConfig {
    @Getter
    private static String siteFrontUrl;
    @Getter
    private static String siteBackUrl;
    @Getter
    private static String siteCookieDomain;

    @Value("${custom.site.frontUrl}")
    public void setSiteFrontUrl(String siteFrontUrl) {
        this.siteFrontUrl = siteFrontUrl;
    }

    @Value("${custom.site.backUrl}")
    public void setSiteBackUrl(String siteBackUrl) {
        this.siteBackUrl = siteBackUrl;
    }

    @Value("${custom.site.cookieDomain}")
    public void setSiteCookieDomain(String siteCookieDomain) {
        this.siteCookieDomain = siteCookieDomain;
    }
}