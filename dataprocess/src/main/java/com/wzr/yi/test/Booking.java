package com.wzr.yi.test;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @autor zhenrenwu
 * @date 2022/5/10 9:10 下午
 */
class Booking implements Serializable
{

    /**
     * Generated serial version ID.
     */

    private final long serialVersionUID = 5316748056989930874L;

    // To hold new booking number.
    private static int newBookingNumber = 0;

    // The booking number.
    private int bookingNumber;


    /*
     * Default serializable fields of a class are defined to be
     * the non-transient and non-static fields. So, we have to
     * write and read the static field separately.
     */
    private void writeObject(ObjectOutputStream oos)
            throws IOException
    {
        oos.defaultWriteObject();
        oos.writeObject(new Integer(newBookingNumber));
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException
    {
        ois.defaultReadObject();
        newBookingNumber = (Integer)ois.readObject();
    }

    public static void main(String[] args) {
        Booking booking = new Booking();
        System.out.println(JSON.toJSONString(booking));
    }

}
