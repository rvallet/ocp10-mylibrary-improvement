package com.library.msbatch.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
@ConfigurationProperties("config-ms-batch")
public class ApplicationPropertiesConfig {

    private String template;

    private String object;

    public String getTemplate() {
        return StringUtils.toEncodedString(template.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getObject() {
        return StringUtils.toEncodedString(object.getBytes(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"));
    }

    public void setObject(String object) {
        this.object = object;
    }

}
