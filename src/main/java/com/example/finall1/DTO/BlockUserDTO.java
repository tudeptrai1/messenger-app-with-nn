
package com.example.finall1.DTO;




public class BlockUserDTO {
    int user1_id;
    int user2_id;

    public BlockUserDTO(int user1_id, int user2_id) {
        this.user1_id = user1_id;
        this.user2_id = user2_id;
    }

    public int getUserID1() {
        return user1_id;
    }

    public void setUserID1(int user1_id) {
        this.user1_id = user1_id;
    }

    public int getUserID2() {
        return user2_id;
    }

    public void setUserID2(int user2_id) {
        this.user2_id = user2_id;
    }

    @Override
    public String toString() {
        return "BlockUserDTO{" + "user1_id=" + user1_id + ", user2_id=" + user2_id + '}';
    }
}
