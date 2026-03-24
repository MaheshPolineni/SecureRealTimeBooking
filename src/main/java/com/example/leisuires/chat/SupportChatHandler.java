package com.example.leisuires.chat;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SupportChatHandler extends TextWebSocketHandler {

    private volatile WebSocketSession receiver;

    // senderId -> session
    private final Map<String, WebSocketSession> senders =
            new ConcurrentHashMap<>();

@Override
public void afterConnectionEstablished(WebSocketSession session) {

    String role = (String) session.getAttributes().get("role");

    if ("RECEIVER".equals(role)) {

        // 🔒 Allow only ONE active receiver
        if (receiver != null && receiver.isOpen()) {
            try {
                session.close(CloseStatus.POLICY_VIOLATION);
            } catch (Exception ignored) {}
            return;
        }

        // ✅ Now safe to assign
        receiver = session;
        System.out.println("Receiver connected");

        broadcastToSenders("SYSTEM::ADMIN_ONLINE");
    }
    else {
        String senderId = UUID.randomUUID().toString();
        session.getAttributes().put("senderId", senderId);
        senders.put(senderId, session);
        System.out.println("Sender connected: " + senderId);

        // 🔔 Immediately inform sender about admin state
        try {
            if (receiver != null && receiver.isOpen()) {
                session.sendMessage(
                    new TextMessage("SYSTEM::ADMIN_ONLINE")
                );
            } else {
                session.sendMessage(
                    new TextMessage("SYSTEM::ADMIN_OFFLINE")
                );
            }
        } catch (Exception ignored) {}
    }
}


@Override
protected void handleTextMessage(
        WebSocketSession session,
        TextMessage message) throws Exception {

    String role = (String) session.getAttributes().get("role");

    // Message from sender → receiver
    if (!"RECEIVER".equals(role)) {

        String senderId =
            (String) session.getAttributes().get("senderId");

        String payload =
            senderId + "::" + message.getPayload();

        if (receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(payload));
        }

        System.out.println("Sender -> Receiver: " + payload);
    }

    // Message from receiver → sender
    else {
        System.out.println("Receiver sent: " + message.getPayload());

        String[] parts = message.getPayload().split("::", 2);

        if (parts.length != 2) {
            System.out.println("Invalid reply format");
            return;
        }

        String targetSenderId = parts[0];
        String reply = parts[1];

        WebSocketSession senderSession =
                senders.get(targetSenderId);

        if (senderSession != null && senderSession.isOpen()) {
            senderSession.sendMessage(
                new TextMessage("Receiver: " + reply)
            );
            System.out.println("Reply sent to sender: " + targetSenderId);
        } else {
            System.out.println("Sender session not found");
        }
    }
}


    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status) {

        if (session == receiver) {
            receiver = null;
            System.out.println("Receiver disconnected");
            broadcastToSenders("SYSTEM::ADMIN_OFFLINE");
        } else {
            String senderId =
                (String) session.getAttributes().get("senderId");
            if (senderId != null) {
                senders.remove(senderId);
                System.out.println("Sender disconnected: " + senderId);
            }
        }
    }


    private void broadcastToSenders(String message) {
    senders.values().forEach(session -> {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (Exception ignored) {}
    });
}

}
