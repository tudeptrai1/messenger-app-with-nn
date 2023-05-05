
package com.example.finall1.DTO;


import java.sql.Date;

public class GroupChatDTO {
    int group_id;
    int creator_id;
    String group_name;
    Date created_at;

    public GroupChatDTO(int group_id, int creator_id, String group_name, Date created_at) {
        this.group_id = group_id;
        this.creator_id = creator_id;
        this.group_name = group_name;
        this.created_at = created_at;
    }
    public GroupChatDTO(int group_id, int creator_id, String group_name) {
        this.group_id = group_id;
        this.creator_id = creator_id;
        this.group_name = group_name;
    }



    public int getGroupID() {
        return group_id;
    }

    public void setGroupID(int group_id) {
        this.group_id = group_id;
    }

    public int getCreatorID() {
        return creator_id;
    }

    public void setCreatorID(int creator_id) {
        this.creator_id = creator_id;
    }

    public String getGroupName() {
        return group_name;
    }

    public void setGroupName(String group_name) {
        this.group_name = group_name;
    }

    @Override
    public String toString() {
        return "GroupChatDTO{" + "group_id=" + group_id + ", creator_id=" + creator_id + ", group_name=" + group_name +'}';
    }
}
