package codebots.gameobjects;

import codebots.controller.Round;

import java.util.Random;

public final class IPAddress {
    private final String address;
    public IPAddress(String address, Round round){
        if (round == null){
            throw new NullPointerException();
        }
        this.address = address;
    }

    public IPAddress(Random random, Round round){
        this(randomIPAddress(random), round);
    }

    public static String randomIPAddress(Random random){
        return random.nextInt(256)+"."+random.nextInt(256)+"."+random.nextInt(256)+"."+random.nextInt(256);
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }
}
