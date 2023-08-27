package com.example.yibanke.api.db.pojo;

public class TbMeetingWithBLOBs extends TbMeeting {
    private String members;

    private String present;

    private String unpresent;

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members == null ? null : members.trim();
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present == null ? null : present.trim();
    }

    public String getUnpresent() {
        return unpresent;
    }

    public void setUnpresent(String unpresent) {
        this.unpresent = unpresent == null ? null : unpresent.trim();
    }
}