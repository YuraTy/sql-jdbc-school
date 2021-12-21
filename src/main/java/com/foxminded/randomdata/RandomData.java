package com.foxminded.randomdata;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class RandomData {

    public String[] curses = {"mathematics","rhetoric", "drawing", "drafting", "astronomy", "ecology", "philosophy", "jurisprudence", "economics", "natural science", "chemistry", "physics", "geometry", "algebra", "informatics", "biology", "history", "geography", "technology", "foreign language"};

private String randomGroup (){
    return RandomStringUtils.randomAlphabetic(2) +"-"+ randomNumber(10,99);
}
public List<String > randomGroups () {
    List<String> list = new ArrayList<>();
    for (int i = 0 ; i <20 ; i ++){
        list.add(randomGroup());
    }
    return list;
}

public List<String > randomStudent () {
    String [] nameStudent = {"Vitaly", "Umar", "Renat", "Imran", "Artyom", "Salim", "Islam", "Yaroslav", "Ethan", "Peter" , "Eva" , "Eileen", "Tina", "Lera", "Anastasia", "Zoya", "Damira", "Yana", "Evelina", "Victoria"};
    String [] surnameStudent = {"Akimenko" , "Aleksandrenko" , "Alekseenko", "Andrievskiy", "Andriyashev", "Ardashev", "Artemenko", "Babarika", "Babich", "Vasilevskiy", "Vasilyuk", "Gavrilyuk" , "Daragan" , "Derevyanko", "Dzyuba", "Efimenko", "Zheleznyak", "Ischenko", "Kalenichenko", "Karpenko"};
    List<String> studentsList = new ArrayList<>();
    for (int i = 0 ; i <200 ; i ++){
        studentsList.add(nameStudent[randomNumber(0,20)] + " " + surnameStudent[randomNumber(0,20)]);
    }
    return studentsList;
}

private int randomNumber (int start, int finale){
    return start + (int)(Math.random()*finale);
}

}
