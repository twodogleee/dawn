package com._54year.dawn.admin.entity;

import java.io.Serializable;

/**
 * <p>
 * dawn-资源表
 * </p>
 *
 * @author Andersen
 * @since 2020-09-04
 */
public class DawnResources implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private String id;

    /**
     * 资源名
     */
    private String resourcesName;

    /**
     * 资源路径
     */
    private String resourcesUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourcesName() {
        return resourcesName;
    }

    public void setResourcesName(String resourcesName) {
        this.resourcesName = resourcesName;
    }

    public String getResourcesUrl() {
        return resourcesUrl;
    }

    public void setResourcesUrl(String resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

    @Override
    public String toString() {
        return "DawnResources{" +
        "id=" + id +
        ", resourcesName=" + resourcesName +
        ", resourcesUrl=" + resourcesUrl +
        "}";
    }
}
