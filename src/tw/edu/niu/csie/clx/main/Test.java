package tw.edu.niu.csie.clx.main;

import tw.edu.niu.csie.clx.gui.QRCodeGenerator;

public class Test {
    public static void main(String[] args) {
        QRCodeGenerator generator = new QRCodeGenerator("https://www.google.com");
        System.out.println(generator.getAsBase64());
    }
}
