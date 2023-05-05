
package com.example.finall1.DTO;




public class FriendDTO {

    int user_id;
    int friend_id;
    boolean is_online;

    public FriendDTO(int user_id, int friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public int getID_User() {
        return user_id;
    }

    public void setUser_ID(int user_id) {
        this.user_id = user_id;
    }

    public int getFriendID() {
        return friend_id;
    }

    public void setFriendID(int friend_id) {
        this.friend_id = friend_id;
    }
    public boolean getStatus(){
        return is_online;
    }
    public void setStatus(boolean is_online) {
        this.is_online = is_online;
    }

    @Override
    public String toString() {
        return "FriendDTO{" + "user_id=" + user_id + ", friend_id=" + friend_id +  ", is_online=" + is_online +'}';
    }
}
