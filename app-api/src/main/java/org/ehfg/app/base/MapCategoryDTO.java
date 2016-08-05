package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.MapCategoryRepresentation;

/**
 * @author poberbichler
 * @since 08.2016
 */
public class MapCategoryDTO implements MapCategoryRepresentation {
    private String id;
    private String name;
    private String cssClass;
    private String imageUrl;

    public MapCategoryDTO() {
        // needed by various frameworks
    }

    public MapCategoryDTO(String id, String name, String cssClass, String imageUrl) {
        this.id = id;
        this.name = name;
        this.cssClass = cssClass;
        this.imageUrl = imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCssClass() {
        return cssClass;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
