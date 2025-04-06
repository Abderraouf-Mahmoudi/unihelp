package unihelp.example.groupe.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @MessageMapping("/chat") // Reçoit de /app/chat
    @SendTo("/topic/messages") // Envoie à tous les abonnés
    public String handleMessage(String message) {
        System.out.println("📨 WebSocket reçu: " + message);
        return message;
    }
    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public String typingNotification(String senderName) {
        return senderName; // simplement renvoyer le nom
    }

}