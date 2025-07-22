package model;

import java.time.LocalDateTime;

public class AuditLog {
    private int userId;
    private Integer itemId;
    private String actionType;
    private String actionTitle;
    private String detailsJson;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;

    public AuditLog(int userId, Integer itemId, String actionType, String actionTitle, String detailsJson,
                    String ipAddress, String userAgent, LocalDateTime createdAt) {
        this.userId = userId;
        this.itemId = itemId;
        this.actionType = actionType;
        this.actionTitle = actionTitle;
        this.detailsJson = detailsJson;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public String getDetailsJson() {
        return detailsJson;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
