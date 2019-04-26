/**
 * Copyright 2013 Sean Kavanagh - sean.p.kavanagh6@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.omar.spring.websocket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Task to watch for output read from the ssh session stream
 */
public class SecureShellTask implements Runnable {

    private static Logger log = LoggerFactory.getLogger(SecureShellTask.class);

    InputStream outFromChannel;
    WebSocketSession session;

    public SecureShellTask( InputStream outFromChannel) {

        this.outFromChannel = outFromChannel;
    }

    public SecureShellTask( InputStream outFromChannel,WebSocketSession session) {

        this.outFromChannel = outFromChannel;
        this.session=session;
    }

    public void run() {
        InputStreamReader isr = new InputStreamReader(outFromChannel);
        BufferedReader br = new BufferedReader(isr);
        try {

            char[] buff = new char[1024];
            int read;
            while((read = br.read(buff)) != -1) {
                String message=new String(buff,0,read);
                System.out.println();
                if(session!=null&&message.getBytes().length>0){
                    session.sendMessage(new TextMessage(message.getBytes()));
                }
                Thread.sleep(50);
            }



        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
    }

}
