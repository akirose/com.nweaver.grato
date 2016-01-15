package com.nweaver.grato.message;

import java.util.List;

import com.nweaver.grato.message.impl.DefaultMessageResource;

/**
 * Factory for creating message resource implementation
 * 
 * @author Min-ki,Cho
 */
public class MessageResourceFactory {

    private List<String> languages;

    private String resourcePath;

    /**
     * Create an {@link MessageResource} based on the configuration on this factory
     * @return The {@link MessageResource} instance
     */
    public MessageResource createMessageResource() {
        return new DefaultMessageResource(languages, resourcePath);
    }
    
    /**
     * The languages for which messages are available 
     * @return The list of available languages
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * Set the languages for which messages are available 
     * @param languages The list of available languages
     */
    
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    /**
     * The directory where message bundles can be located
     * @return The directory with message bundles
     */
    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * Set the directory where message bundles can be located
     * @param resourcePath The directory with message bundles
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
}
