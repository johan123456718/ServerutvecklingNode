package com.node.db;


import com.node.objects.Users;
import com.node.objects.canvasImage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Interface declaring methods used for handling communication with a database
 */
@Component
public interface DbInterface {

    public ArrayList<Users> getUsernames(String uuid);

    String saveImage(String uuid, String image64, String nameOfImage) ;

    ArrayList<String> getImageNames(String uuid);

    canvasImage loadImage(String uuid, String name) ;

    String createFolder(String uuid);
}
