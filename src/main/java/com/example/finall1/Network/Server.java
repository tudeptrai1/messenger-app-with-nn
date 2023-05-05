
package Network;

import BUS.ClassBUS;
import com.example.finall1.DTO.UserDTO;
import Func.Hyrid_Encryption;
import Func.fileFunc;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Server implements Runnable {

    ServerSocket serversocket = null;
    PrivateKey privateKey = null;
    PublicKey publicKey = null;
    ArrayBlockingQueue<Runnable> workQueue;
    RejectedExecutionHandler handler;
    ThreadPoolExecutor executor;
    HashMap<String, Socket> listSocket = new HashMap<>();

    String PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbPndbAp25koChNaXO9XfZHLBEVKWedG5c2Inio657AePBaYzYISc2ucXwHDzn+xJsFbthGzyt+CYsnVdrtwpVB3Pv7TpWnj2W2l0yG5vrOjsUERVBaC+6Mk1+RNXRimqxCJDtJTtXeB9/bZGXBe4WcPXUhwIB563JPyAGTyeVnwIDAQAB";
    String PRIVATE_KEY_STRING = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJs+d1sCnbmSgKE1pc71d9kcsERUpZ50blzYieKjrnsB48FpjNghJza5xfAcPOf7EmwVu2EbPK34JiydV2u3ClUHc+/tOlaePZbaXTIbm+s6OxQRFUFoL7oyTX5E1dGKarEIkO0lO1d4H39tkZcF7hZw9dSHAgHnrck/IAZPJ5WfAgMBAAECgYEAkySI8m0vW+W9H49+wgOtfc6QT6O/esm2lS/0uSkVRqfK3NaTVYNO7LL2JphNLj+t/V43xVmQkQAkBqN3abQLCIR961M4eaBwpLAOQtJKALH+fnsiUCCWwbioO3PTfyOpH3injfLvE4NhyoQeazx+AKSkZyro2CG5U/LBsJJWXzECQQDQ6bYILy2WUuqWKwIbGSwDZPdc4T724PFECzdZki1O1gw6PPhdoasUOt0OZrT0rqJ71YF0MdAeykHMYn2PEWtHAkEAvjwOEIgqtFpe6nGNZDiZ+5i/sV5bxW5o/YQWwf106nxR0CQlqfwevrIJvMDUUKs7QTAeMT+pcWnK7eW3DoB+6QJBAICGeA3a8IHd6yKNvRLszo4cDK6giLsbsnK5L8k0TBmHSCiAIBCCiJy+hgb5GvS5h48F0Emq5645ondaVIKzJbsCQQCx4LXF/4zu1xGpZkQvUj2pZEraLsDg+zxw0PH2smiAWX6mgSY2q+iTpyYzuJrOU040xil1I3Hs+l8l04Y3qS8BAkBIHOR887VNtejYOVcwrUHpcKcccVPAKxsoxBBziOxD0alGHtvop7CU1VVfcnQtZ7Dd1sSj4MNgguW92s0/rXNg";

    public Server() {
        try {
            serversocket = new ServerSocket(Config.PORT);
            privateKey = Hyrid_Encryption.getPrivateKeyRSA(PRIVATE_KEY_STRING);
            publicKey = Hyrid_Encryption.getPublicKeyRSA(PUBLIC_KEY_STRING);
            workQueue = new ArrayBlockingQueue<>(100);
            handler = new ThreadPoolExecutor.CallerRunsPolicy();
            executor = new ThreadPoolExecutor(Config.corePoolSize, Config.maximumPoolSize, Config.keepAliveTime, Config.unit, workQueue, handler);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server đã dừng hoạt động");
        }
    }

    public HashMap<String, Socket> getListSocket() {
        return listSocket;
    }

    public void setListSocket(HashMap<String, Socket> listSocket) {
        this.listSocket = listSocket;
    }

    @Override
    public void run() {
        this.executor.execute(new ReadConnect(serversocket, publicKey, privateKey, executor));

        //executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    void Terminal() {
        try {
            this.listSocket = null;
            this.executor.shutdown();
            this.serversocket.close();

        } catch (IOException ex) {
            System.out.println("Lỗi khi dừng chương trình : " + ex.getMessage());
        }
    }

    private class ReadConnect implements Runnable {

        ServerSocket ss;
        PublicKey publicKey;
        PrivateKey privateKey;
        ThreadPoolExecutor executor;

        public ReadConnect(ServerSocket ss, PublicKey publicKey, PrivateKey privateKey, ThreadPoolExecutor exe) {
            this.ss = ss;
            this.publicKey = publicKey;
            this.privateKey = privateKey;
            this.executor = exe;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("Server is running");
                    Socket s = ss.accept();
                    System.out.println("Client connected");

                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

                    executor.execute(new ExecuteCommand(s, input, output));
                    System.out.println("Size list socket : " + listSocket.size());
                }
            } catch (IOException ex) {
                System.out.println("Stop server");
            }
        }
    }

    private class ExecuteCommand implements Runnable {

        BufferedReader input;
        BufferedWriter output;
        ClassBUS bus;
        Socket s;

        public ExecuteCommand(Socket s, BufferedReader input, BufferedWriter output) {
            this.input = input;
            this.output = output;
            this.s = s;
            bus = new ClassBUS();
        }

        @Override
        public void run() {

            try {
                while (true) {
                    //Read data from client
                    String readData = input.readLine();
                    if (readData == null) {
                        break;
                    }

                    HashMap<String, String> readClient = new Gson().fromJson(readData, new TypeToken<HashMap<String, String>>() {
                    }.getType());

                    String encryptKey = readClient.get("key");

                    String valueData = readClient.get("value");
                    //Giai ma key ma hoa
                    String clientKey = Hyrid_Encryption.decryptRSA(encryptKey, privateKey);
                    //Giai ma du lieu dua tren key da giai ma
                    valueData = Hyrid_Encryption.decryptAES(valueData, clientKey);
                    //Execute command
                    String result = executeCommand(valueData, clientKey);

                    //Gan key cho socket
                    listSocket.put(clientKey, s);
                    if (result == null) {
                        return;
                    }
                    //Send result to Client
                    output.write(result);
                    output.newLine();
                    output.flush();
                }
            } catch (Exception ex) {
                try {
                    System.out.println("Socket is close");
                    input.close();
                    output.close();
                } catch (IOException ex1) {
                    System.out.println("Đóng socket");
                }
            }

        }

        public String executeCommand(String readData, String clientKey) {
            // Split data from input
            HashMap<String, String> maps = new Gson().fromJson(readData, new TypeToken<HashMap<String, String>>() {
            }.getType());

            String command = maps.get("command");
            String data = maps.get("data");
            
            System.out.println(command);

            switch (command) {
                case "REGISTER_ACCOUNT": {
                    return registerNewAccount(data, clientKey);
                }
                case "RESEND_OTP": {
                    return sendOTP(data, clientKey);
                }
                case "ACTIVE_ACCOUNT": {
                    return activeAccount(data, clientKey);
                }
                case "LOGIN_ACCOUNT": {
                    return loginAccount(data, clientKey);
                }
                case "CHECK_REGISTER_ACCOUNT": {
                    return checkregisteraccount(data, clientKey);
                }
                case "GET_ALL_GROUPCHAT_AND_USER": {
                    return getAllGroupChatAndUser(data, clientKey);
                }
                case "JOIN_GROUPCHAT": {
                    return joinGroupChat(data, clientKey);
                }
                case "LOAD_MESSAGE_GROUPCHAT": {
                    return loadMessageGroupChat(data, clientKey);
                }
                case "LEAVE_GROUP_CHAT": {
                    return leaveGroupChat(data, clientKey);
                }
                case "ADD_MESSAGE_GROUPCHAT": {
                    return addNewMessage(data, clientKey);
                }
                case "VIEW_ALL_MESSAGE_GROUPCHAT": {
                    return viewAllMessageGroup(data, clientKey);
                }
                case "ADD_FILE_MESAGE": {
                    return addNewFIleMess(data, clientKey);
                }
                case "DOWNLOAD_FILE_MESAGE": {
                    return downloadFile(data, clientKey);
                }
                case "ADD_STIKER_GROUPCHAT": {
                    return addNewStickerGroup(data, clientKey);
                }
                case "LOGOUT_ACCOUNT": {
                    return logoutAccount(data, clientKey);
                }
                case "LOAD_ALL_USER_GROUPCHAT": {
                    return loadAllUserGroup(data, clientKey);
                }
                case "BLOCK_GROUP_CHAT": {
                    return blockGroupChat(data, clientKey);
                }
                case "UNBLOCK_GROUPCHAT": {
                    return unblockGroupChat(data, clientKey);
                }
                case "ADD_MESSAGE_11": {
                    return addNewMessage11(data, clientKey);
                }
                case "ADD_STICKER_11": {
                    return addNewSticker11(data, clientKey);
                }
                case "LOAD_MESSAGE_11": {
                    return loadMessage11(data, clientKey);
                }
                case "VIEW_ALL_MESSAGE_11": {
                    return vewAllMessage11(data, clientKey);
                }
                case "ADD_ROOM": {
                    return add_room(data, clientKey);
                }
                case "ADD_FILE_MESSAGE_11": {
                    return addNewFileMess11(data, clientKey);
                }
                case "LOAD_ALL_FRIEND": {
                    return loadAllFriend(data, clientKey);
                }
                case "BLOCK_USER":{
                    return blockUser(data,clientKey);
                }
                case "LOAD_LIST_USER_BLOCK":{
                    return listBlockUser(data,clientKey);
                }
                case "UNBLOCK_USER":{
                    return unblockUser(data,clientKey);
                }
                case "UNBLOCK_GROUP":{
                    return unblockGroupChat(data, clientKey);
                }
                case "LOAD_LIST_GROUP_BLOCK":{
                    return loadListBlockGroup(data,clientKey);
                }
                case "VIEW_MESSAGE_11":{
                    return viewMess11(data,clientKey);
                }
            }
            return "";
        }

        private String registerNewAccount(String data, String clientKey) {
            UserDTO user = new Gson().fromJson(data, new TypeToken<UserDTO>() {
            }.getType());
            String result = bus.addNewAccount(user);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "REGISTER_ACCOUNT");
            dataMap.put("result", result);

            //Ghi log
            List<String> logUser = fileFunc.readTextFile("./src/file_Server/log.txt");
            logUser.add("[" + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + "] Tài khoản " + user.getEmail() + " được tạo bỏi " + user.getName());

            fileFunc.writeTextFile("./src/file_Server/log.txt", logUser);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String sendOTP(String data, String clientKey) {
            Random rd = new Random();
            int code = rd.nextInt(99999) + 1111;
            String email = data;
            UserDTO u = bus.getUserByEmail(email);
            String title = "Kích hoạt tài khoản";
            String html = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>OTP</title></head><body><div class=\"container\" ><div class=\"alert-box\" style=\"width: 50%;height: 50%;display: block;margin: auto;border: 1px solid gray;\"><div class=\"header\" style=\"background-color: #0052cc;\"><p style=\"text-align: center;padding: 20px 0;bottom: 0%;font-size: 35px;color: white;\">XÁC MINH TÀI KHOẢN</p></div><div class=\"content\" style=\"text-align: center;\"><p style=\"font-size: 20px;\">Xin chào," + u.getName() + "</p><p style=\"font-size: 20px;\">Hãy sử dụng mã xác minh bên dưới để có thể hoàn thành đăng ký tài khoản của bạn: </p><p style=\"font-size: 50px;font-weight: bold;color: red;border: 2px solid gray;width: 50%;margin:auto\">" + String.valueOf(code) + "</p><p style=\"font-size: 20px;\">Mã xác nhận chỉ tồn tại trong 10 phút</p><p style=\"font-size: 15px;\">©ChatApp.</p></div></div></div></body></html>";

            new Thread(new SendEmail(email, title, html)).start();

            HashMap<String, String> maps = new HashMap<>();
            maps.put("command", "RESEND_OTP");
            maps.put("code", String.valueOf(code));

            String returnData = new Gson().toJson(maps);
            return Hyrid_Encryption.encryptAES(returnData, clientKey);
        }

        private String activeAccount(String data, String clientKey) {
            String email = data;
            String result = bus.activeAccout(email);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "ACTIVE_ACCOUNT");
            dataMap.put("result", result);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String loginAccount(String data, String clientKey) {
            String[] str = new Gson().fromJson(data, new TypeToken<String[]>() {
            }.getType());
            String result = bus.checkLogin(str[0], str[1]);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LOGIN_ACCOUNT");
            dataMap.put("result", result);
            dataMap.put("user", new Gson().toJson(bus.getUserByEmail(str[0])));
            //Ghi log
            List<String> logUser = fileFunc.readTextFile("./src/file_Server/log.txt");
            logUser.add("[" + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + "] User " + str[0] + " đăng nhập");

            fileFunc.writeTextFile("./src/file_Server/log.txt", logUser);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String checkregisteraccount(String data, String clientKey) {
            UserDTO user = new Gson().fromJson(data, new TypeToken<UserDTO>() {
            }.getType());
            String result = bus.checkNewAccount(user);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "CHECK_REGISTER_ACCOUNT");
            dataMap.put("result", result);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String getAllGroupChatAndUser(String data, String clientKey) {
            int id = Integer.valueOf(data);
            String result = bus.getAllGroupChatWithoutBlock(id);
            String dataUser = bus.getAllUserWithoutBlock(id);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "GET_ALL_GROUPCHAT_AND_USER");
            dataMap.put("data_group", result);
            dataMap.put("data_user", dataUser);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String joinGroupChat(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.joinGroupChat(str[0], str[1]);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "JOIN_GROUPCHAT");
            dataMap.put("result", result);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String loadMessageGroupChat(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.getMessageGroupChat(str[0], str[1]);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LOAD_MESSAGE_GROUPCHAT");
            dataMap.put("data", result);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String leaveGroupChat(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.leaveMessageGroupChat(str[0], str[1]);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LEAVE_GROUP_CHAT");
            dataMap.put("result", result);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);

        }

        private String addNewMessage(String data, String clientKey) {
            //ADD_MESSAGE_GROUPCHAT
            String[] str = new Gson().fromJson(data, new TypeToken<String[]>() {
            }.getType());
            int idUser = Integer.valueOf(str[0]);
            int idGroup = Integer.valueOf(str[1]);
            String content = str[2];
            bus.addNewGroupMessage(idUser, idGroup, content);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "ADD_MESSAGE_GROUPCHAT");

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String viewAllMessageGroup(String data, String clientKey) {
            //VIEW_ALL_MESSAGE_GROUPCHAT
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            bus.viewAllMessageGroupChat(str[0], str[1]);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "VIEW_ALL_MESSAGE_GROUPCHAT");

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);

        }

        private String addNewFIleMess(String data, String clientKey) {
            //ADD_FILE_MESAGE
            String[] str = new Gson().fromJson(data, new TypeToken<String[]>() {
            }.getType());
            
            for(int i = 0;i < str.length;i++){
                System.out.println(str[i]);
            }
            int idUser = Integer.valueOf(str[0]);
            int idGroup = Integer.valueOf(str[1]);
            String filename = str[2];
            byte[] dataFile = new Gson().fromJson(str[3], new TypeToken<byte[]>() {
            }.getType());

            bus.addNewFileMess(idUser, idGroup, filename, dataFile);
            fileFunc.WriteFile("./src/file_Server/" + filename, dataFile);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "ADD_FILE_MESAGE");

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String downloadFile(String data, String clientKey) {

            File file = new File("./src/file_Server/" + data);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "DOWNLOAD_FILE_MESAGE");
            if (!file.exists() || file.isDirectory()) {
                dataMap.put("message", "File không tồn tại trên hệ thống");
            } else {
                try {
                    byte[] dataFile = fileFunc.convertFileToByte("./src/file_Server/" + data);
                    dataMap.put("result", "SUCCESS");
                    dataMap.put("message", "Tải file thành công. File được  lưu ở './src/file/" + data + "'");
                    dataMap.put("data", new Gson().toJson(dataFile));
                    dataMap.put("filename", data);
                } catch (Exception ex) {
                    //JOptionPane.showMessageDialog(null, ex.printStackTrace());
                    dataMap.put("result", "Lỗi khi tải file" + ex.getMessage());
                    
                   
                }
            }

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String addNewStickerGroup(String data, String clientKey) {
            //ADD_STIKER_GROUPCHAT
            String[] str = new Gson().fromJson(data, new TypeToken<String[]>() {
            }.getType());
            int idUser = Integer.valueOf(str[0]);
            int idGroup = Integer.valueOf(str[1]);
            String stickername = str[2];

            bus.addNewStickerGroupMess(idUser, idGroup, stickername);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "ADD_STIKER_GROUPCHAT");

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String logoutAccount(String data, String clientKey) {
            UserDTO user = new Gson().fromJson(data, new TypeToken<UserDTO>() {
            }.getType());

            bus.logOutAccount(user.getId());
            //LOGOUT_ACCOUNT
            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LOGOUT_ACCOUNT");

            //Ghi log
            List<String> logUser = fileFunc.readTextFile("./src/file_Server/log.txt");
            logUser.add("[" + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + "] User " + user.getEmail() + " đăng xuất");
            fileFunc.writeTextFile("./src/file_Server/log.txt", logUser);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String loadAllUserGroup(String data, String clientKey) {
            //LOAD_ALL_USER_GROUPCHAT
            int idGroup = Integer.valueOf(data);
            String result = bus.getAllUserGroup(idGroup);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LOAD_ALL_USER_GROUPCHAT");
            dataMap.put("data", result);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String blockGroupChat(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.blockMessageGroupChat(str[0], str[1]);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "BLOCK_GROUP_CHAT");
            dataMap.put("result", result);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String unblockGroupChat(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.unblockMessageGroupChat(str[0], str[1]);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "UNBLOCK_GROUPCHAT");
            dataMap.put("result", result);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String addNewMessage11(String data, String clientKey) {
            //ADD NEW MESSAGE 11
            String[] str = new Gson().fromJson(data, new TypeToken<String[]>() {
            }.getType());
            //LẤY DỮ LIỆU TỪ str
            int idSend = Integer.valueOf(str[0]);
            int idReceive = Integer.valueOf(str[1]);
            String content = str[2];
            //Gọi hàm thêm mới tin nhắn 11 từ classbus
            bus.addNew11Message(idSend, idReceive, content);

            //KHỞI TẠO DỮ LIỆU TRẢ VỀ
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "ADD_MESSAGE_11");

            //CHUYỂN DỮ LIỆU TRẢ VỀ THÀNH JSON
            String returnValue = new Gson().toJson(dataMap);
            //MÃ HOÁ DỮ LIỆU
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String addNewSticker11(String data, String clientKey) {
            //ADD NEW STICKER MESSAGE 11
            String[] str = new Gson().fromJson(data, new TypeToken<String[]>() {
            }.getType());

            //LẤY DỮ LIỆU TỪ STR
            int idSend = Integer.valueOf(str[0]);
            int idReceive = Integer.valueOf(str[1]);
            String stickerName = str[2];

            //GỌI HÀM THÊM MỚI TIN NHẮN STICKER TỪ CLASSBUS
            bus.addNewSticker11Mess(idSend, idReceive, stickerName);

            //KHỞI TẠO DỮ LIỆU TRẢ VỀ
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "ADD_STICKER_11");

            //CHUYỂN DỮ LIỆU TRẢ VỀ THÀNH JSON
            String returnValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String loadMessage11(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());
            String result = bus.getMessage11(str[0], str[1]);

            //KHỞI TẠO DỮ LIỆU TRẢ VỀ
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LOAD_MESSAGE_11");
            dataMap.put("data", result);

            //CHUYỂN DỮ LIỆU TRÀ VỀ THÀNH JSON
            String resultValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU TRẢ VỀ
            System.out.println(resultValue);
            return Hyrid_Encryption.encryptAES(resultValue, clientKey);
        }

        private String vewAllMessage11(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            bus.viewAllMessage11(str[0], str[1]);

            //KHỞI TẠO DỮ LIỆU TRẢ VỀ
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "VIEW_ALL_MESSAGE_11");

            //CHUYỂN DỮ LIỆU TRÀ VỀ THÀNH JSON
            String resultValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU TRẢ VỀ
            return Hyrid_Encryption.encryptAES(resultValue, clientKey);
        }

        private String add_room(String data, String clientKey) {
            String[] str = new Gson().fromJson(data, new TypeToken<String[]>() {
            }.getType());
            int IdUser = Integer.valueOf(str[0]);
            String room_name = str[1];
            String result = bus.add_room(IdUser, room_name);
            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "ADD_ROOM");
            dataMap.put("result", result);
            //Ghi log
            List<String> logUser = fileFunc.readTextFile("./src/file_Server/log.txt");
            logUser.add("[" + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + "] User " + str[0] + " tạo phòng chat " + room_name);

            fileFunc.writeTextFile("./src/file_Server/log.txt", logUser);

            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String addNewFileMess11(String data, String clientKey) {
            //ADD_FILE_MESAGE
            String[] str = new Gson().fromJson(data, new TypeToken<String[]>() {
            }.getType());
            int idUser = Integer.valueOf(str[0]);
            int idReceive = Integer.valueOf(str[1]);
            String filename = str[2];
            byte[] dataFile = new Gson().fromJson(str[3], new TypeToken<byte[]>() {
            }.getType());

            bus.addNewFileMess11(idUser, idReceive, filename, dataFile);
            fileFunc.WriteFile("./src/file_Server/" + filename, dataFile);

            //KHOI TAO DU LIEU TRA VE
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "ADD_FILE_MESSAGE_11");
            //Chuyen thanh json
            String returnValue = new Gson().toJson(dataMap);
            //Ma hoa du lieu
            return Hyrid_Encryption.encryptAES(returnValue, clientKey);
        }

        private String loadAllFriend(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.getAllFriendById(str[0]);

            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LOAD_ALL_FRIEND");
            dataMap.put("data", result);

            //CHUYỂN DỮ LIỆU TRÀ VỀ THÀNH JSON
            String resultValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU TRẢ VỀ
            return Hyrid_Encryption.encryptAES(resultValue, clientKey);

        }

        private String blockUser(String data, String clientKey) {
            //BLOCK_USER
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.blockUser(str[0],str[1]);

            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "BLOCK_USER");
            dataMap.put("result", result);

            System.out.println(dataMap);
            //CHUYỂN DỮ LIỆU TRÀ VỀ THÀNH JSON
            String resultValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU TRẢ VỀ
            return Hyrid_Encryption.encryptAES(resultValue, clientKey);
        }

        private String listBlockUser(String data, String clientKey) {
            //LOAD_LIST_USER_BLOCK
            int id = Integer.valueOf(data);
            String result = bus.getListBlockUser(id);

            //KHỞI TẠO DỮ LIỆU TRẢ VỀ
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LOAD_LIST_USER_BLOCK");
            dataMap.put("data", result);

            //CHUYỂN DỮ LIỆU TRÀ VỀ THÀNH JSON
            String resultValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU TRẢ VỀ
            return Hyrid_Encryption.encryptAES(resultValue, clientKey);
        }
        
        private String unblockUser(String data, String clientKey) {
            //BLOCK_USER
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.unblockUser(str[0],str[1]);

            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "UNBLOCK_USER");
            dataMap.put("result", result);

            System.out.println(dataMap);
            //CHUYỂN DỮ LIỆU TRÀ VỀ THÀNH JSON
            String resultValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU TRẢ VỀ
            return Hyrid_Encryption.encryptAES(resultValue, clientKey);
        }

        private String loadListBlockGroup(String data, String clientKey) {
            int id = Integer.valueOf(data);
            String result = bus.getListBlockGroup(id);

            //KHỞI TẠO DỮ LIỆU TRẢ VỀ
            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "LOAD_LIST_GROUP_BLOCK");
            dataMap.put("data", result);

            //CHUYỂN DỮ LIỆU TRÀ VỀ THÀNH JSON
            String resultValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU TRẢ VỀ
            return Hyrid_Encryption.encryptAES(resultValue, clientKey);
        }

        private String viewMess11(String data, String clientKey) {
            int[] str = new Gson().fromJson(data, new TypeToken<int[]>() {
            }.getType());

            String result = bus.viewAllMess11(str[0],str[1]);

            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("command", "VIEW_MESSAGE_11");
            dataMap.put("result", result);

            System.out.println(dataMap);
            //CHUYỂN DỮ LIỆU TRÀ VỀ THÀNH JSON
            String resultValue = new Gson().toJson(dataMap);

            //MÃ HOÁ DỮ LIỆU TRẢ VỀ
            return Hyrid_Encryption.encryptAES(resultValue, clientKey);
        }

    }

    public static void main(String[] args) {
        new Server();
    }
}
