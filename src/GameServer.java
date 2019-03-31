import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


class GamePlayThread extends Thread {
    private Socket user1Socket;
    private Socket user2Socket;
    private int startXAndFinishYPosition = 0;
    private int startYAndFinishXPosition = 0;
    private int itemXPosition = 0;
    private int itemYPosition = 0;

    public GamePlayThread(Socket user1Socket, Socket user2Socket) {
        this.user1Socket = user1Socket;
        this.user2Socket = user2Socket;

        while (this.startXAndFinishYPosition - this.startYAndFinishXPosition < 3) {
            this.startXAndFinishYPosition = (int) (Math.random() * 10);
            this.startYAndFinishXPosition = (int) (Math.random() * 10);
        }

        while (this.startXAndFinishYPosition - this.itemXPosition < 2 && this.startYAndFinishXPosition - this.itemYPosition < 2) {
            this.itemXPosition = (int) (Math.random() * 10);
            this.itemYPosition = (int) (Math.random() * 10);
        }
    }

    @Override
    public void run() {
        boolean isTurnOfUser1 = true;

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();

        data.put("startXAndFinishYPosition", this.startXAndFinishYPosition);
        data.put("startYAndFinishXPosition", this.startYAndFinishXPosition);
        data.put("itemXPosition", this.itemXPosition);
        data.put("itemYPosition", this.itemYPosition);

        try {
            new PrintWriter(this.user1Socket.getOutputStream(), true).println(mapper.writer().writeValueAsString(data));
            new PrintWriter(this.user2Socket.getOutputStream(), true).println(mapper.writer().writeValueAsString(data));
        } catch(IOException e) {
            e.printStackTrace();
        }

        data = null;

        while(true) {
            try {
                Socket targetSocket;

                if (isTurnOfUser1) {
                    targetSocket = this.user1Socket;
                } else {
                    targetSocket = this.user2Socket;
                }

                InputStream inputStream = targetSocket.getInputStream();
                OutputStream outputStream = targetSocket.getOutputStream();

                new PrintWriter(outputStream, true).println(mapper.writer().writeValueAsString(data));

                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                data = mapper.readValue(br.readLine(), new TypeReference<Map<String, Object>>(){});
                isTurnOfUser1 = !isTurnOfUser1;
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class GameServer {
    public static void main(String[] args) {
//        ServerSocket server;
//        Socket user1Socket;
//        Socket user2Socket;
//
//        try {
//            server = new ServerSocket(3000);
//
//            user1Socket = server.accept();
//            user2Socket = server.accept();
//
//            new GamePlayThread(user1Socket, user2Socket).start();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
    }
}
