
package com.example.finall1.BUS;

import com.example.finall1.DAO.BlockGroupDAO;
import com.example.finall1.DAO.BlockUserDAO;
import com.example.finall1.DAO.ConnectionDB;
import com.example.finall1.DAO.FriendDAO;
import com.example.finall1.DAO.GroupChatDAO;
import com.example.finall1.DAO.JoinGroupDAO;
import com.example.finall1.DAO.MessageGroupDAO;
import com.example.finall1.DAO.Message_11DAO;
import com.example.finall1.DAO.UserDAO;
import com.example.finall1.DAO.ViewMessage11DAO;
import com.example.finall1.DAO.ViewMessageGroupDAO;
import com.example.finall1.DTO.*;
import com.example.finall1.DTO.MessageDTO;
//import Func.Hyrid_Encryption;
import com.google.gson.Gson;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



public class ClassBUS {

    UserDAO userdao = new UserDAO();
    GroupChatDAO groupchatdao = new GroupChatDAO();
    JoinGroupDAO joingroupdao = new JoinGroupDAO();
    BlockGroupDAO blockgroupdao = new BlockGroupDAO();
    MessageGroupDAO messagegroupdao = new MessageGroupDAO();
    FriendDAO frienddao = new FriendDAO();
    BlockUserDAO blockUserdao = new BlockUserDAO();
    Message_11DAO message11dao = new Message_11DAO();
    ViewMessage11DAO viewmess11dao = new ViewMessage11DAO();
    ViewMessageGroupDAO viewmessgroupdao = new ViewMessageGroupDAO();
    ConnectionDB connect;
    Connection conn;

    public ClassBUS() {
        connect = new ConnectionDB();
        this.conn = connect.getConn();
    }

    public String checkLogin(String email, String pass) {
        UserDTO userDB = userdao.getUserByEmail(email, conn);
        if (userDB == null) {
            return "Tài khoản không tồn tại";
        } else if (userDB.getPassword().compareTo(Hyrid_Encryption.convertToMd5(pass)) != 0) {
            return "Mật khẩu không chính xác";
        } else if (!userDB.isIsActive()) {
            return "Tài khoản chưa đc kích hoạt";
        } else {
            //Chuyen tang thai dang nhap thanh true
            boolean rs = userdao.updateOnlineStatus(email, true, conn);
            if (!rs) {
                return "Không thể đăng nhập lỗi khi thay đổi trạng thái online ";
            }
            return "Đăng nhập thành công. Chào mừng trở lại";
        }
    }

    public UserDTO getUserByEmail(String email) {
        return userdao.getUserByEmail(email, conn);
    }

    public String activeAccout(String email) {
        boolean status = userdao.activeAccout(email, conn);

        return status ? "Kích hoạt tài khoản thành công" : "Không thể kích hoạt";
    }

    public String checkNewAccount(UserDTO user) {
        UserDTO userDB = userdao.getUserByEmail(user.getEmail(), conn);
        if (userDB != null) {
            return "Email đã tồn tại";
        }
        return "SUCCESS";
    }

    public String addNewAccount(UserDTO user) {
        UserDTO userDB = userdao.getUserByEmail(user.getEmail(), conn);
        if (userDB != null) {
            return "Email đã tồn tại";
        }
        boolean add = userdao.addNewAccount(user, conn);

        if (add) {
            return "Đăng ký thành công";
        }
        return "Không thể đăng ký";
    }

    public String getAllGroupChatWithoutBlock(int id) {
        List<GroupChatDTO> listGroup = groupchatdao.getAllGroup(conn);
        List<GroupMemberDTO> listJoinGroup = joingroupdao.getAllJoinGroup(conn);
        List<BlockGroupDTO> listBlockGroup = blockgroupdao.getAllBlockGroup(conn);
        List<MessageGroupDTO> listMessageGroup = messagegroupdao.getAllMessage(conn);
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<GroupChatDTO> listGroupResult = new ArrayList<>();

        //Duyet qua tat ca cac group
        for (GroupChatDTO groupchat : listGroup) {
            boolean check = false;
            if (listBlockGroup != null) {
                //kiem tra phong co bi block hay khong
                for (BlockGroupDTO blockgroup : listBlockGroup) {
                    if (blockgroup.getId_group() == groupchat.getId_group() && blockgroup.getId_user() == id) {
                        check = true;
                        break;
                    }
                }
            }

            if (check) {
                continue;
            }
            groupchat.setIsBlock(false);
            // kiem tra xem nguoi dung co tham gia phong hay chua
            groupchat.setIsJoin(false);
            if (listJoinGroup != null) {
                for (GroupMemberDTO joingroup : listJoinGroup) {
                    if (joingroup.getId_group() == groupchat.getId_group() && joingroup.getId_user() == id) {
                        groupchat.setIsJoin(true);
                        break;
                    }
                }
            }

            //Lay tin nhan cuoi cung cua nhom
            String lastMessage = "";
            for (MessageGroupDTO messagegroup : listMessageGroup) {
                if (messagegroup.getId_group() == groupchat.getId_group()) {
                    //Lay ten nguoi cua tin nha cuoi do
                    for (UserDTO user : listUser) {
                        if (user.getId() == messagegroup.getId_user()) {
                            lastMessage = user.getName() + " : " + (messagegroup.isIsURL() ? "Đã gửi 1 liên kết" : messagegroup.getContent());
                        }
                    }
                }
            }
            groupchat.setNearlyMessage(lastMessage);
            listGroupResult.add(groupchat);
        }

        return new Gson().toJson(listGroupResult);
    }

    public String getAllUserWithoutBlock(int id) {
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<FriendDTO> listFriend = frienddao.getAllFriend(conn);
        List<BlockUserDTO> listBlockUser = blockUserdao.getAllListBlock(conn);
        List<MessageDTO> listMessage11 = message11dao.getAllMessage(conn);
        List<UserDTO> listUserReturn = new ArrayList<>();

        //Loai bo nhung user dang bi block va user dang login
        for (UserDTO user : listUser) {
            //Bo qua user hien tai
            if (user.getId() == id) {
                continue;
            }
            //tra trong bang block
            boolean checkBlock = false;
            if (listBlockUser != null) {
                for (BlockUserDTO block : listBlockUser) {
                    if (block.getId_user() == id && block.getId_usr_block() == user.getId() && user.getId() != id) {
                        checkBlock = true;
                        break;
                    }
                }
            }
            if (checkBlock) {
                continue;
            }

            //Kiem tra xem user hien tai co bi 1 user trong listUser block hay khong ?
            boolean checkIsBlock = false;
            if (listBlockUser != null) {
                for (BlockUserDTO block : listBlockUser) {
                    if (block.getId_user() == user.getId() && block.getId_usr_block() == id && user.getId() != id) {
                        checkIsBlock = true;
                        break;
                    }
                }
            }

            if (checkIsBlock) {
                user.setIsBlock(true);
            }

            //Lay tin nhan gan nhat
            String lastMessage = "";
            if (listMessage11 != null) {
                for (MessageDTO mess : listMessage11) {
                    if (mess.getId_received() == id && mess.getId_sender() == user.getId()) {

                        lastMessage = user.getName() + " : " + (mess.isIsURL() ? "Đã gửi 1 liên kết" : mess.getContent());
                    } else if (mess.getId_received() == user.getId() && mess.getId_sender() == id) {
                        //Lay ten nguoi gui
                        String nameUserSend = "";
                        for (UserDTO subuser : listUser) {
                            if (subuser.getId() == id) {
                                nameUserSend = subuser.getName();
                                break;
                            }
                        }
                        lastMessage = nameUserSend + " : " + (mess.isIsURL() ? "Đã gửi 1 liên kết" : mess.getContent());
                    }
                }
            }

            user.setLastMessage(lastMessage);
            listUserReturn.add(user);
        }
        return new Gson().toJson(listUserReturn);
    }

    public static void main(String[] args) {
        ClassBUS c = new ClassBUS();
        System.out.println(c.getMessage11(1, 2));
    }

    public String joinGroupChat(int idUser, int idGroup) {
        if (joingroupdao.joinToGroup(idUser, idGroup, conn)) {
            return "Tham gia thành công";
        }
        return "Lỗi khi tham gia";
    }

    public String getMessageGroupChat(int idUser, int idGroup) {
        List<MessageGroupDTO> listMessageGroup = messagegroupdao.getAllMessage(conn);
        List<ViewMessageDTO> listViewmessGroup = viewmessgroupdao.getAllViewMessage(conn);
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<MessageGroupDTO> listMessageGroupReturn = new ArrayList<>();

        for (MessageGroupDTO mess : listMessageGroup) {
            if (mess.getId_group() == idGroup) {
                //Tin nhan co phai cua nguoi dang login khong ?
                if (mess.getId_user() == idUser) {
                    mess.setIsOwner(true);
                }
                int idUserMess = mess.getId_user();
                for (UserDTO user : listUser) {
                    if (user.getId() == idUserMess) {
                        mess.setNameUser(user.getName());
                        break;
                    }
                }
                //Lay danh sach nguoi xem
                String viewer = "";
                if (listViewmessGroup != null) {
                    for (ViewMessageDTO view : listViewmessGroup) {
                        if (view.getId_mess() == mess.getId() && view.getId_user() != mess.getId_user()) {
                            int idViewer = view.getId_user();
                            if (idViewer == idUser && mess.getId_user() != idUser) {
                                viewer += "Bạn, ";
                            } else {
                                for (UserDTO user : listUser) {
                                    if (user.getId() == idViewer) {
                                        viewer += user.getName() + ",";
                                    }
                                }
                            }
                        }
                    }
                }

                mess.setViewer(viewer);
                listMessageGroupReturn.add(mess);
            }
        }
        return new Gson().toJson(listMessageGroupReturn);
    }

    public String getMessage11(int idSend, int idReceive) {
        List<MessageDTO> listMess = message11dao.getAllMessage(conn);
        List<ViewMessageDTO> listViewMess = viewmess11dao.getAllViewMessage(conn);
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<MessageDTO> listMessValue = new ArrayList<>();

        if (listMess != null) {
            for (MessageDTO mess : listMess) {
                mess.setIsOwner(false);
                if ((mess.getId_received() == idReceive && mess.getId_sender() == idSend) || mess.getId_received() == idSend && mess.getId_sender() == idReceive) {
                    if (mess.getId_sender() == idSend) {
                        mess.setIsOwner(true);
                    }
                    int idUserMess = mess.getId_sender();
                    for (UserDTO user : listUser) {
                        if (user.getId() == idUserMess) {
                            mess.setNameUser(user.getName());
                            break;
                        }
                    }

                    //Lấy danh sách người xem tin nhắn
                    String viewer = "";
                    if (listViewMess != null) {
                        for (ViewMessageDTO view : listViewMess) {
                            if (view.getId_mess() == mess.getId() && view.getId_user() != mess.getId_sender()) {
                                int idViewer = view.getId_user();
                                if (idViewer == idSend && mess.getId_sender() != idSend) {
                                    viewer += "Bạn, ";
                                } else {
                                    for (UserDTO user : listUser) {
                                        if (user.getId() == idViewer) {
                                            viewer += user.getName() + " ";
                                        }
                                    }
                                }
                            }
                        }
                    }
                    mess.setViewer(viewer);
                    listMessValue.add(mess);
                }
            }
        }
        //Trả lại dữ liệu tin nhắn thành json
        return new Gson().toJson(listMessValue);
    }

    public String leaveMessageGroupChat(int idUser, int idRoom) {
        if (joingroupdao.removeJoinGroup(idUser, idRoom, conn)) {
            return "Rời phòng chat thành công";
        }
        return "Lỗi khi rời phòng chat";
    }

    public void addNewGroupMessage(int idUser, int idGroup, String content) {
        messagegroupdao.addNewMess(idUser, idGroup, content, conn);
    }

    public void addNew11Message(int idSend, int idReceive, String content) {
        message11dao.addNew11Mess(idSend, idReceive, content, conn);
    }

    public void viewAllMessageGroupChat(int idUser, int idGroup) {
        List<ViewMessageDTO> listViewGroup = viewmessgroupdao.getAllViewMessage(conn);
        List<MessageGroupDTO> listMessage = messagegroupdao.getAllMessage(conn);

        for (MessageGroupDTO mess : listMessage) {
            if (mess.getId_group() != idGroup || mess.getId_user() == idUser) {
                continue;
            }
            boolean checkView = false;
            if (listViewGroup != null) {
                for (ViewMessageDTO view : listViewGroup) {
                    if (view.getId_user() == idUser && view.getId_mess() == mess.getId()) {
                        checkView = true;
                        break;
                    }
                }
            }

            if (!checkView) {
                viewmessgroupdao.viewMessage(idUser, mess.getId(), conn);
            }
        }
    }

    public void viewAllMessage11(int idSend, int idReceive) {
        List<ViewMessageDTO> listView11 = viewmess11dao.getAllViewMessage(conn);
        List<MessageDTO> listMess11 = message11dao.getAllMessage(conn);

        for (MessageDTO mess : listMess11) {
            if (mess.getId_sender() == idSend || mess.getId_received() != idReceive) {
                continue;
            }
            boolean checkView = false;
            if (listView11 != null) {
                for (ViewMessageDTO view : listView11) {
                    if (view.getId_user() == idSend && view.getId_mess() == mess.getId()) {
                        checkView = true;
                    }
                }
            }
            if (!checkView) {
                viewmess11dao.addViewMessage(idSend, mess.getId(), conn);
            }
        }
    }

    public void addNewFileMess(int idUser, int idGroup, String filename, byte[] dataFile) {
        MessageGroupDTO mess = new MessageGroupDTO(
                idUser,
                idGroup,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()),
                filename,
                true
        );

        messagegroupdao.addNewMess(mess, conn);
    }

    public void addNewStickerGroupMess(int idUser, int idGroup, String stickername) {
        MessageGroupDTO mess = new MessageGroupDTO(
                idUser,
                idGroup,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()),
                stickername,
                true
        );

        messagegroupdao.addNewMess(mess, conn);
    }

    public void addNewSticker11Mess(int idSend, int idReceive, String stickername) {
        MessageDTO mess = new MessageDTO(
                idSend,
                idReceive,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()),
                stickername,
                true
        );
        message11dao.addNew11Mess(mess, conn);
    }

    public void logOutAccount(int id) {
        userdao.logOutAccount(id, conn);
    }

    public String getAllUserGroup(int idGroup) {
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<GroupMemberDTO> listJoinGroup = joingroupdao.getAllJoinGroup(conn);
        List<UserDTO> listReturn = new ArrayList<>();

        for (UserDTO user : listUser) {
            boolean isJoin = false;
            for (GroupMemberDTO join : listJoinGroup) {
                if (join.getId_group() == idGroup && join.getId_user() == user.getId()) {
                    isJoin = true;
                    break;
                }
            }
            if (isJoin) {
                listReturn.add(user);
            }
        }

        return new Gson().toJson(listReturn);
    }

    public String blockMessageGroupChat(int idUser, int idRoom) {
        if (joingroupdao.blockGroup(idUser, idRoom, conn)) {
            return "Block phòng chat thành công";
        }
        return "Lỗi khi block phòng chat";
    }

    public String unblockMessageGroupChat(int idUser, int idRoom) {
        if (joingroupdao.unblockGroup(idUser, idRoom, conn)) {
            return "Unblock phòng chat thành công";
        }
        return "Lỗi khi unblock phòng chat";
    }

    public String add_room(int IdUser, String room_name) {
        if (groupchatdao.add_room(IdUser, room_name, conn)) {
            return "Add room success";
        }
        return "add room failed";
    }

    public List<UserDTO> getAllOnlineUser() {
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<UserDTO> listResult = new ArrayList<>();

        for (UserDTO user : listUser) {
            if (!user.isIsOnline() || user.isServerBlock() || !user.isIsActive()) {
                continue;
            }
            listResult.add(user);
        }

        return listResult;
    }

    public List<UserDTO> getAllOfflineUser() {
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<UserDTO> listResult = new ArrayList<>();

        for (UserDTO user : listUser) {
            if (user.isIsOnline() || user.isServerBlock() || !user.isIsActive()) {
                continue;
            }
            listResult.add(user);
        }

        return listResult;
    }

    public List<UserDTO> getAllBlockUser() {
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<UserDTO> listResult = new ArrayList<>();

        for (UserDTO user : listUser) {
            if (!user.isServerBlock() || !user.isIsActive()) {
                continue;
            }
            listResult.add(user);
        }

        return listResult;
    }

    public int[] statisticUserAdmin() {
        int[] count = new int[4];
        List<UserDTO> listUser = userdao.getAllUser(conn);

        count[0] = listUser.size();
        for (UserDTO user : listUser) {
            if (user.isServerBlock()) {
                count[3]++;
            } else if (user.isIsOnline()) {
                count[1]++;
            } else {
                count[2]++;
            }

        }

        return count;
    }

    public boolean blockUser(int id) {
        return userdao.blockUnblockUser(id, true, conn);
    }

    public boolean unblockUser(int id) {
        return userdao.blockUnblockUser(id, false, conn);
    }

    public void addAllFriendOfUser(int idUser) {
        List<MessageDTO> listMess = message11dao.getAllMessage(conn);
        List<FriendDTO> listFriend = frienddao.getAllFriend(conn);

        for (MessageDTO mess : listMess) {
            if (mess.getId_sender() == idUser) {
                boolean checkFriend = false;
                if (listFriend != null) {
                    for (FriendDTO friend : listFriend) {
                        if (friend.getID_User() == idUser && mess.getId_received() == friend.getFriendID()) {
                            checkFriend = true;
                            break;
                        }
                    }
                }
                if (!checkFriend) {
                    frienddao.addNewFriend(idUser, mess.getId_received(), conn);
                }
            }
        }
    }

    public void addNewFileMess11(int idUser, int idReceive, String filename, byte[] dataFile) {
        MessageDTO mess = new MessageDTO(
                idUser,
                idReceive,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()),
                filename,
                true
        );
        message11dao.addNew11Mess(mess, conn);
    }

    public String getAllFriendById(int idUser) {
        addAllFriendOfUser(idUser);
        List<MessageDTO> listMess = message11dao.getAllMessage(conn);
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<UserDTO> listResult = new ArrayList<>();

        for (UserDTO user : listUser) {
            if (isExistIdFriend(listMess, idUser, user.getId()) && isExistIdFriend(listMess, user.getId(), idUser)) {
                user.setIsFriend(true);
                listResult.add(user);
            }
        }

        return new Gson().toJson(listResult);
    }

    public boolean isExistIdFriend(List<MessageDTO> list, int id, int idUser2) {
        for (MessageDTO mess : list) {
            if (mess.getId_sender() == id && mess.getId_received() == idUser2) {
                return true;
            }
        }
        return false;
    }

    public String blockUser(int idSender, int idReceive) {
        if (blockUserdao.block(idSender, idReceive, conn)) {
            return "Chặn người dùng thành công";
        }
        return "Lỗi khi chặn";
    }

    public String unblockUser(int idSender, int idReceive) {
        if (blockUserdao.unblock(idSender, idReceive, conn)) {
            return "Bỏ chặn thành công";
        }
        return "Lỗi khi Bỏ chặn người dùng";
    }

    public String getListBlockUser(int id) {
        List<UserDTO> listUser = userdao.getAllUser(conn);
        List<BlockUserDTO> listBlockUser = blockUserdao.getAllListBlock(conn);
        List<UserDTO> listReturn = new ArrayList<>();

        if (listBlockUser != null) {
            for (BlockUserDTO block : listBlockUser) {
                if (block.getId_user() == id) {
                    //Lay thong tin nguoi bi block
                    for (UserDTO user : listUser) {
                        if (user.getId() == block.getId_usr_block()) {
                            listReturn.add(user);
                            break;
                        }
                    }
                }
            }
        }

        return new Gson().toJson(listReturn);
    }

    public String getListBlockGroup(int id) {
        List<GroupChatDTO> listGroup = groupchatdao.getAllGroup(conn);
        List<BlockGroupDTO> listBlockGroup = blockgroupdao.getAllBlockGroup(conn);
        List<GroupChatDTO> listReturn = new ArrayList<>();

        if (listBlockGroup != null) {
            for (BlockGroupDTO block : listBlockGroup) {
                if (block.getId_user() == id) {
                    //Lay thong tin group bi khoa
                    for (GroupChatDTO g : listGroup) {
                        if (g.getId_group() == block.getId_group()) {
                            listReturn.add(g);
                        }
                    }
                }
            }
        }

        return new Gson().toJson(listReturn);
    }

    public String viewAllMess11(int idView, int idSend) {
        //TIm tat ca tin nhan cua nguoi gui
        List<MessageDTO> listMess11 = message11dao.getAllMessage(conn);
        List<ViewMessageDTO> listViewMess = viewmess11dao.getAllViewMessage(conn);
        List<MessageDTO> listMessSend = new ArrayList<>();

        if (listMess11 != null) {
            for (MessageDTO mess : listMess11) {
                if (mess.getId_sender() == idSend && mess.getId_received() == idView) {
                    listMessSend.add(mess);
                }
            }
            
            for(MessageDTO mess : listMessSend){
                boolean isExist = false;
                
                for(ViewMessageDTO view : listViewMess){
                    if(view.getId_user() == idView && view.getId_mess() == mess.getId()){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    viewmess11dao.addViewMessage(idView, mess.getId(), conn);
                }
            }
            
        }
        return "";
    }

}
