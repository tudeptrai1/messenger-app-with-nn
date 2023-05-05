
package com.example.finall1.DTO;




public class ViewMessageDTO {
    int id_user;
    int id_mess;

    public ViewMessageDTO(int id_user, int id_mess) {
        this.id_user = id_user;
        this.id_mess = id_mess;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_mess() {
        return id_mess;
    }

    public void setId_mess(int id_mess) {
        this.id_mess = id_mess;
    }

    @Override
    public String toString() {
        return "ViewMessage11DTO{" + "id_user=" + id_user + ", id_mess=" + id_mess + '}';
    }
}
