package com.node.db;

import com.node.objects.Users;
import com.node.objects.canvasImage;
import com.node.repositories.imageRepository;
import com.node.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Implements DbInterface to use a MySQL database
 */
@Component
public class DbManager implements DbInterface {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private imageRepository imgRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    private static final String rootPath = "C://Users/empaf/OneDrive/Server/node/images/";

    public DbManager(){

    }


    /**
     * Gets all usernames except callers
     * @param uuid
     * @return
     */
    @Override
    public ArrayList<Users> getUsernames(String uuid) {
        Iterable<Users> userList = userRepository.findAll();

        ArrayList<Users> usernameList = new ArrayList<>();
        usernameList.addAll((ArrayList) userList);

        for(int i = 0; i < usernameList.size(); i++){
            if(usernameList.get(i).getUUID().equals(uuid)){
                usernameList.remove(i);
            }else{
                usernameList.get(i).setPassword("");
            }
        }
        return usernameList;
    }

    @Override
    public String saveImage(String uuid, String image64, String nameOfImage){

        image64 = image64.replaceAll("\\s", "+");

        try{

            File file = new File(rootPath+uuid, nameOfImage);
            System.out.println(file.getPath());
            if(!file.exists()){
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024*8);
                PrintWriter printWriter = new PrintWriter(bufferedOutputStream);
                printWriter.write(image64);

                printWriter.flush();
                fileOutputStream.flush();
                bufferedOutputStream.flush();
                printWriter.close();
                bufferedOutputStream.close();
                fileOutputStream.close();
            }else{
                return "File already exists";
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        canvasImage img = new canvasImage();
        img.setNameOfImage(nameOfImage);
        img.setCreator(uuid);
        img.setDay_of_the_week(LocalDateTime.now().getDayOfWeek().getValue());
        imgRepository.save(img);
        return img.toString();

    }

    @Override
    public ArrayList<String> getImageNames(String uuid) {
        Iterable<canvasImage> images = imgRepository.findByCreator(uuid);
        ArrayList<String> imgs = new ArrayList<>();
        for (canvasImage i :images) {
            imgs.add(i.getNameOfImage());
        }
        return imgs;
    }

    @Override
    public canvasImage loadImage(String uuid, String name) {
        File filepath = new File(rootPath+uuid, name);
        Iterable<canvasImage> images = imgRepository.findByCreator(uuid);
        if(filepath.exists()){
            canvasImage img = new canvasImage();
            try{
                img.setNameOfImage(new String ( Files.readAllBytes( Paths.get(String.valueOf(filepath)) ) ));
                img.setCreator(uuid);
                for (canvasImage imgArray: images) {
                    if(imgArray.getNameOfImage().equals(name)){
                        img.setDay_of_the_week(imgArray.getDay_of_the_week());
                        break;
                    }
                }
            }
            catch (Exception e ){
                e.printStackTrace();
            }
            return img;
        }return null;
    }

    @Override
    public String createFolder(String uuid) {
        new File(rootPath+uuid).mkdir();
        System.out.println(rootPath+uuid);
        return "Folder created";
    }

    /**
     * Gets the encrypted password
     * @return
     */
    @Bean
    private PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
