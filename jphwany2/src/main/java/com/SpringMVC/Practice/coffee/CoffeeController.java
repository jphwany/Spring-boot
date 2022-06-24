package com.SpringMVC.Practice.coffee;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; // 제거
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/v1/coffees")
public class CoffeeController {

    @PostMapping
    public ResponseEntity postCoffee(@RequestParam("engName") String engName,
                                     @RequestParam("korName") String korName,
                                     @RequestParam("price") String price){
        System.out.println("# endName: " + engName);
        System.out.println("# korName: " + korName);
        System.out.println("# price: " + price);

        Map<String, String> map = new HashMap<>();
        map.put("engName", engName);
        map.put("korName", korName);
        map.put("price", price);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }


    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId){
        System.out.println("# coffeeId:" + coffeeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees(){
        System.out.println("# get Coffees");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
