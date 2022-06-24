package com._54year.dawn.excel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * excel导出记录表
 * </p>
 *
 * @author Andersen
 * @since 2022-06-24
 */
@TableName("tb_excel_export")
public class ExcelExport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据源类型
     */
    private Integer sourceType;

    /**
     * 导出类型:1简单导出 2复杂导出
     */
    private Integer exportType;

    /**
     * 导出简要
     */
    private String exportContent;

    /**
     * 导出文件名
     */
    private String exportFileName;

    /**
     * 导出文件地址
     */
    private String fileDownloadUrl;

    /**
     * 导出条目数
     */
    private Integer exportTotal;

    /**
     * 导出状态
     */
    private Integer exportStatus;

    /**
     * 导出描述,错误原因等内容
     */
    private String exportDesc;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    public String getExportContent() {
        return exportContent;
    }

    public void setExportContent(String exportContent) {
        this.exportContent = exportContent;
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }

    public Integer getExportTotal() {
        return exportTotal;
    }

    public void setExportTotal(Integer exportTotal) {
        this.exportTotal = exportTotal;
    }

    public Integer getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Integer exportStatus) {
        this.exportStatus = exportStatus;
    }

    public String getExportDesc() {
        return exportDesc;
    }

    public void setExportDesc(String exportDesc) {
        this.exportDesc = exportDesc;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ExcelExport{" +
        "id=" + id +
        ", sourceType=" + sourceType +
        ", exportType=" + exportType +
        ", exportContent=" + exportContent +
        ", exportFileName=" + exportFileName +
        ", fileDownloadUrl=" + fileDownloadUrl +
        ", exportTotal=" + exportTotal +
        ", exportStatus=" + exportStatus +
        ", exportDesc=" + exportDesc +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        "}";
    }
}
