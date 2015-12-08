package codebots.gameobjects;

import java.util.Random;

public final class IPAddress {
    private final String address;
    public IPAddress(String address){
        this.address = address;
    }

    public IPAddress(Random random){
        this(randomIPAddress(random));
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
