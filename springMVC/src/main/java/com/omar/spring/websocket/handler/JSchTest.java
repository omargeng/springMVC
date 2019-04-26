package com.omar.spring.websocket.handler;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.*;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2019/4/26
 * Description:
 */
public class JSchTest {

    public static void main(String[] args) throws Exception {
        JSch jsch ;
        InputStream outFromChannel;
        OutputStream inputToChannel;
        jsch = new JSch();
        Session sshSession = jsch.getSession("vafs", "10.227.8.122", 22);
        sshSession.setPassword("vafs");
        sshSession.setConfig("StrictHostKeyChecking", "no");
        sshSession.setTimeout(60000);
        sshSession.connect();
        Channel channel = sshSession.openChannel("shell");
        ((ChannelShell) channel).setPtyType("xterm");
        outFromChannel = channel.getInputStream();
        inputToChannel = channel.getOutputStream();


        Runnable run = new SecureShellTask(outFromChannel);
        Thread thread = new Thread(run);
        thread.start();
        channel.connect();
        PrintStream commander = new PrintStream(inputToChannel, true);
        commander.println("ls");
        commander.flush();


    }
}
