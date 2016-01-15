package com.nweaver.grato.message.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.nweaver.grato.ServerConfigurationException;
import com.nweaver.grato.message.MessageResource;

public class DefaultMessageResource implements MessageResource {
		
	private final String resourcePath;
	
	private List<String> languages;
	
	private Map<String, Properties> messages;

	public DefaultMessageResource(List<String> languages, String resourcePath) {
		this.resourcePath = resourcePath;
		
		if(languages != null) {
			this.languages = Collections.unmodifiableList(languages);
		}
		
		messages = new HashMap<String, Properties>();
		if(languages != null) {
			for(String language : languages) {
				Properties properties = createProperties(language);
				messages.put(language, properties);
			}
		}
		Properties properties = createProperties(null);
        messages.put(null, properties);
	}
	
    private Properties createProperties(String lang) {
    	Properties properties = new Properties();

        // load resource
        String defaultResourceName;
        if (lang == null) {
            defaultResourceName = resourcePath + "Messages.properties";
        } else {
            defaultResourceName = resourcePath + "Messages_" + lang + ".properties";
        }
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream(defaultResourceName);
            if (in != null) {
                try {
                	properties.load(in);
                } catch (IOException e) {
                    throw new ServerConfigurationException("Failed to load messages from \"" + defaultResourceName + "\", file not found in classpath");
                }
            } else {
                throw new ServerConfigurationException("Failed to load messages from \"" + defaultResourceName + "\", file not found in classpath");
            }
        } finally {
            if(in != null) {
            	try {
            		in.close();
            	} catch (Exception ex) {
            	}
            }
        }

        return properties;
    }
	
	public List<String> getAvailableLanguages() {
        if (languages == null) {
            return null;
        } else {
            return Collections.unmodifiableList(languages);
        }
	}
	
	public String getMessage(int code, String language) {
		return getMessage(code, null, language);
	}

	public String getMessage(int code, String subId, String language) {
		// find the message key
		String key = String.valueOf(code);
		if(subId != null) {
			key += "." + subId;
		}
		
		// get language specific value
		String value = null;
		Properties properties = null;
		if(language != null) {
            language = language.toLowerCase();
            properties = messages.get(language);
            if (properties != null) {
            	value = properties.getProperty(key);
            }
		}
		
        // if not available get the default value
        if (value == null) {
        	properties = messages.get(null);
            if (properties != null) {
            	value = properties.getProperty(key);
            }
        }
        
		return value;
	}

	public Map<String, String> getMessages(String language) {
        Properties messages = new Properties();

        // load properties sequentially
        Properties properties = this.messages.get(null);
        if (properties != null) {
            messages.putAll(properties);
        }
        if (language != null) {
            language = language.toLowerCase();
            properties = this.messages.get(language);
            if (properties != null) {
                messages.putAll(properties);
            }
        }
        
        Map<String, String> result = new HashMap<String, String>();
        for(Object key : messages.keySet()) {
            result.put(key.toString(), messages.getProperty(key.toString()));
        }
        
        return Collections.unmodifiableMap(result);
	}
	
    /**
     * Dispose component - clear all maps.
     */
    public void dispose() {
        Iterator<String> it = messages.keySet().iterator();
        while (it.hasNext()) {
            String language = it.next();
            Properties properties = messages.get(language);
            properties.clear();
        }
        messages.clear();
    }
    
    @Override
    public String toString() {
    	return " { " + messages.toString();
    }
}
