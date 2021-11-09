package jms.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class MessageUtil {

    private final Properties props;

    public MessageUtil() throws IOException {
        Resource resource = new ClassPathResource("/messages.properties");
        props = PropertiesLoaderUtils.loadProperties(resource);
    }

    public String getMessage(String key, Object... args) {
        if (key == null) {
            return null;
        }
        String message = props.getProperty(key);
        if (args.length > 0) {
            return String.format(message, args);
        }
        return message;
    }

}
