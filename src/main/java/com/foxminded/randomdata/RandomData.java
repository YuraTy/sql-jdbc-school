package com.foxminded.randomdata;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {

public String randomGroups (){
    return RandomStringUtils.randomAlphabetic(2) +"-"+ (10 + (int)(Math.random()*99));
}

}
