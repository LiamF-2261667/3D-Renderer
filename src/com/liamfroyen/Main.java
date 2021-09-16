package com.liamfroyen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Main {

    public static float delta;

    public static void main(String[] args) {
        StartDisplay();
        StartStarsProgram();

        long previousTime = System.nanoTime();
        while (true) {
            long currentTime = System.nanoTime();
            delta = (float) ((currentTime - previousTime)/1000000000.0);
            previousTime = currentTime;

            Update();
        }
    }

    public static Display display;
    public static Bitmap target;

    public static void StartDisplay() {
        display = new Display(800, 600, "Rendering Engine");
        target = display.GetFrameBuffer();
    }

    public static void Update() {
        stars.UpdateAndRender(target, delta);
        display.SwapBuffers();
    }

    public static Stars3D stars;
    public static void StartStarsProgram() {
        stars = new Stars3D(4096, 64.0f, 10.0f, 80);
    }
}
