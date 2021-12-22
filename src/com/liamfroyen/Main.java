package com.liamfroyen;

public class Main {

    public static float delta;
    public static Stars3D stars;

    public static void main(String[] args) {
        StartDisplay();
        target.Clear((byte)0x00);

        Vertex p1 = new Vertex(400, 500);
        Vertex p2 = new Vertex(560, 600);
        Vertex p3 = new Vertex(908, 1080);

        target.FillTriangle(p1, p2, p3);

        //stars = new Stars3D(8096, 64.0f, 30.0f, 120);

        long previousTime = System.nanoTime();
        while (true) {
            long currentTime = System.nanoTime();
            delta = (float) ((currentTime - previousTime)/1000000000.0);
            previousTime = currentTime;

            Update();
        }
    }

    public static Display display;
    public static RenderContext target;

    public static void StartDisplay() {
        display = new Display(1920 , 1080, "3D Rendering Engine");
        target = display.GetFrameBuffer();
    }

    public static void Update() {
        //stars.UpdateAndRender(target, delta);
        display.SwapBuffers();
    }

}
