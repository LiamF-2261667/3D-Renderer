package com.liamfroyen;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Display display = new Display(1920, 1080, "3D Renderer");
        RenderContext target = display.GetFrameBuffer();
        Stars3D stars = new Stars3D(10000, 64.0f, 8.0f, 120);

        target.Clear((byte)0x00);

        Mesh monkey = new Mesh("./monkey.obj");
        Mesh highResMonkey = new Mesh("./monkeyHighRes.obj");
        Mesh tree = new Mesh("./tree.obj");
        Mesh cube = new Mesh("./cube.obj");
        Mesh sphere = new Mesh("./sphere.obj");

        Matrix4f projection = new Matrix4f().InitPerspective((float)Math.toRadians(70.0f),
                (float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f);

        float rotCounter = 0.0f;
        long previousTime = System.nanoTime();
        while(true)
        {
            long currentTime = System.nanoTime();
            float delta = (float)((currentTime - previousTime)/1000000000.0);
            previousTime = currentTime;

            //stars.UpdateAndRender(target, delta);

            rotCounter += delta;
            Matrix4f translation = new Matrix4f().InitTranslation(0.0f, 0.0f, 3.0f);
            Matrix4f rotation = new Matrix4f().InitRotation(rotCounter, 0.0f, rotCounter);
            //Matrix4f rotation = new Matrix4f().InitRotation(0.0f, rotCounter, 0.0f);
            Matrix4f transform = projection.Mul(translation.Mul(rotation));

            target.Clear((byte)0x8F);
            target.DrawMesh(sphere, transform);

            display.SwapBuffers();
        }
    }
}
