
package com.example.finall1.DTO;




public class GroupMemberDTO {
    int user_id;
    int group_id;

    public GroupMemberDTO(int user_id, int group_id) {
        this.user_id = user_id;
        this.group_id = group_id;
    }

    public int getUserID() {
        return user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    public int getGroupID() {
        return group_id;
    }

    public void setGroupID(int group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "GroupMemberDTO{" + "user_id=" + user_id + ", group_id=" + group_id + '}';
    }
}
