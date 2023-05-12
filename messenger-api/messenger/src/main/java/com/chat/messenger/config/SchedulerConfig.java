package com.chat.messenger.config;

import com.chat.messenger.jobs.ScreenCaptureEventPublishJob;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobFactory jobFactory(ApplicationContext context) {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(context);
        return jobFactory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/application.yaml"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SimpleTriggerFactoryBean resourceAllocationTriggerBean(
            @Qualifier("screenCaptureEventPublishJobDetail") JobDetail jobDetail,
            @Value("${screen-capture-event-publish-job.frequency}") long frequency
    ) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(frequency);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean screenCaptureEventPublishJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(ScreenCaptureEventPublishJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("ScreenCaptureEventPublishJobDetail");
        factoryBean.setDescription("Invoke screen capture event publish job service");
        return factoryBean;
    }
}
