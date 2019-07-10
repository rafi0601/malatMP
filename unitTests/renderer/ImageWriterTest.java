package renderer;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest
{
    @Test
    void viewPlane()
    {
        ImageWriter Image=new ImageWriter("viewPlane",10,10,500,500);
        for (int i = 0; i<500;i++)
            for(int j=0;j<500;j+=50)
            {
                Image.writePixel(i, j, Color.white);
                Image.writePixel(j, i, Color.white);
            }
        Image.writeToImage();
    }

}