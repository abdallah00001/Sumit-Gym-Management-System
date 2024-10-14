package com.summit.gym.Sumit_Gym_Management_System.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QrCodeService {

//    private final Path qrSaveFolderPath = Paths.get("QrCodes");
    private final Path qrSaveFolderPath = Paths.get("C:\\summit-gym\\QrCodes");

    public String saveQrCodeToFile(Long value) throws WriterException, IOException {
        // Ensure the QrCodes directory exists
        if (Files.notExists(qrSaveFolderPath)) {
            Files.createDirectories(qrSaveFolderPath); // Creates the directory if it doesn't exist
        }

        // Construct the file path for the QR code image
        Path filePath = qrSaveFolderPath.resolve(value.toString() + ".png");

        // Generate the QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(value.toString(), BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        // Write the image to the specified file
        ImageIO.write(image, "png", filePath.toFile());

        // Return the absolute path of the created file
        return filePath.toString();
    }




    /*    public String generateQrCode(Long value) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(value.toString(), BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return encodeImageToBase64(image);
    }*/

    /*    private String encodeImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] qrCodeBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(qrCodeBytes);
    }*/



    public static void main(String[] args) throws IOException, WriterException {
        QrCodeService qrCodeService = new QrCodeService();

        System.out.println(qrCodeService.qrSaveFolderPath);

        qrCodeService.saveQrCodeToFile(1L);

    }

}

