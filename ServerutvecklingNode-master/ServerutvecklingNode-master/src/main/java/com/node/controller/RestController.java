package com.node.controller;



import com.node.db.DbInterface;
import com.node.db.DbManager;
import com.node.objects.Users;
import com.node.objects.canvasImage;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Controller handling REST calls
 */
@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="/demo", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST}, produces = { "application/json; charset=utf-8" })
public class RestController {

    @Autowired
    DbInterface DbManager = new DbManager();

    @GetMapping(path="/usernames")
    public @ResponseBody ArrayList<Users> getUsernames(@RequestParam String uuid){
        return DbManager.getUsernames(uuid);
    }

    @PostMapping(path="/saveImage")
    public @ResponseBody String saveImage(@RequestParam String uuid, @RequestParam String img, @RequestParam String name){
        return DbManager.saveImage(uuid, img, name);
    }

    @GetMapping(path="/images")
    public @ResponseBody ArrayList<String> getImageNames(@RequestParam String uuid){
        return DbManager.getImageNames(uuid);
    }

    @GetMapping(path="/loadImage")
    public @ResponseBody
    canvasImage loadImage(@RequestParam String uuid, @RequestParam String name){
        return DbManager.loadImage(uuid, name);
    }

    @PostMapping(path="/createFolder")
    public @ResponseBody String createFolder(@RequestParam String uuid){
        return DbManager.createFolder(uuid);
    }

}
