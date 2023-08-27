package com.example.yibanke.api.db.pojo;

public class TbRoleWithBLOBs extends TbRole {
    private String permissions;

    private String defaultPermissions;

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions == null ? null : permissions.trim();
    }

    public String getDefaultPermissions() {
        return defaultPermissions;
    }

    public void setDefaultPermissions(String defaultPermissions) {
        this.defaultPermissions = defaultPermissions == null ? null : defaultPermissions.trim();
    }
}