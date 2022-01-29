package com.liamfroyen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Display extends Canvas{

        private final JFrame            frame;
        private final BufferedImage     displayImage;
        private final RenderContext     frameBuffer;
        private final byte[]            displayComponents;
        private final BufferStrategy    bufferStrategy;
        private final Graphics          graphics;

        //setup display
        public Display(int width, int height, String title) {
            //set canvas dimensions
            Dimension size = new Dimension(width, height);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);

            //setup bitmap
            frameBuffer = new RenderContext(width, height);
            displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            displayComponents = ((DataBufferByte) displayImage.getRaster().getDataBuffer()).getData();

            //set bg color
            frameBuffer.Clear((byte)0x00);

            //setup frame
            frame = new JFrame();
            frame.add(this);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setTitle(title);

            //show frame
            frame.setVisible(true);

            //create buffer
            createBufferStrategy(1);
            bufferStrategy = getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();
        }

        //set buffer to current display
        public void SwapBuffers() {
            frameBuffer.CopyToByteArray(displayComponents);
            graphics.drawImage(displayImage, 0, 0, frameBuffer.GetWidth(), frameBuffer.GetHeight(), null);
            bufferStrategy.show();
        }

        public RenderContext GetFrameBuffer() {return frameBuffer;}

}
