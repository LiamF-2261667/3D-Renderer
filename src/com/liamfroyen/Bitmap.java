package com.liamfroyen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Bitmap {

    private final int width;
    private final int height;
    private final byte components[];

    public Bitmap(String fileName) throws IOException {
        int width_ = 0;
        int height_ = 0;
        byte[] components_ = null;

        BufferedImage image = ImageIO.read(new File(fileName));

        width_ = image.getWidth();
        height_ = image.getHeight();

        int imgPixels[] = new int[width_ * height_];
        image.getRGB(0, 0, width_, height_, imgPixels, 0, width_);
        components_ = new byte[width_ * height_ * 4];

        for (int i = 0; i < width_ * height_; i++) {
            int pixel = imgPixels[i];

            components_[i * 4]     = (byte)((pixel >> 24) & 0xFF); // A
            components_[i * 4 + 1] = (byte)((pixel      ) & 0xFF); // B
            components_[i * 4 + 2] = (byte)((pixel >> 8 ) & 0xFF); // G
            components_[i * 4 + 3] = (byte)((pixel >> 16) & 0xFF); // R
        }

        width = width_;
        height = height_;
        components = components_;
    }

    public Bitmap(int width_, int height_) {
        width       = width_;
        height      = height_;
        components  = new byte[width * height * 4];
    }

    public void Clear(byte shade) { Arrays.fill(components, shade); }

    /*
    public void Clear(byte r, byte g, byte b) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                components[(i + j * width)    ] = (byte)0xFF;
                components[(i + j * width) + 1] = b;
                components[(i + j * width) + 2] = g;
                components[(i + j * width) + 3] = r;
            }
        }
    }*/

    public void DrawPixel(int x, int y, byte a, byte b, byte g, byte r) {
        int index = (x + y * width) * 4;
        components[index    ] = a;
        components[index + 1] = b;
        components[index + 2] = g;
        components[index + 3] = r;
    }

    public void CopyToByteArray(byte[] dest) {
        for (int i = 0; i < width * height; i++) {
            dest[i * 3    ] = components[i * 4 + 1];
            dest[i * 3 + 1] = components[i * 4 + 2];
            dest[i * 3 + 2] = components[i * 4 + 3];
        }
    }

    public int GetWidth()  {return width;}
    public int GetHeight() {return height;}
}
