package com.omar.spring.websocket.handler;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2019/4/26
 * Description:
 */
@Component("sshShellHandler")
public class SshShellHandler extends AbstractWebSocketHandler {
    JSch jsch ;
    PrintStream commander;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
         jsch = new JSch();
        Session sshSession = jsch.getSession("vafs", "10.225.1.90", 22);
        sshSession.setPassword("vafs");
        sshSession.setConfig("StrictHostKeyChecking", "no");
        sshSession.setTimeout(60000);
        sshSession.connect();
        Channel channel = sshSession.openChannel("shell");
        ((ChannelShell) channel).setPtyType("xterm");
        InputStream outFromChannel = channel.getInputStream();
        OutputStream inputToChannel = channel.getOutputStream();
        PrintStream commander = new PrintStream(inputToChannel, true);
        Runnable run = new SecureShellTask(outFromChannel,session);
        Thread thread = new Thread(run);
        thread.start();
        channel.connect();
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        commander.println(new String(message.asBytes()));
        commander.flush();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
