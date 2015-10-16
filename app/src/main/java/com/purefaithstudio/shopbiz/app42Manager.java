package com.purefaithstudio.shopbiz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Response;
import com.shephertz.app42.paas.sdk.android.shopping.Catalogue;
import com.shephertz.app42.paas.sdk.android.shopping.CatalogueService;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;
import com.shephertz.app42.paas.sdk.android.user.User.Profile;
import com.shephertz.app42.paas.sdk.android.user.User.UserGender;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by harsimran singh on 16-10-2015.
 */
public class app42Manager {
    final String APIKEY ="50ca63a022074fdeb10d9e3ca5169e4052a631070d2cb62251dbf8a956b6d76c";
    final String SECRET_KEY="0a6a0814e88d678c2350a9c25c66815fda35ba437c87c7300aa459f93ed007b1";
    //final String ADMIN_KEY="1d86c02b3c89214f53f3b1f08abc4aca6387b9b2e62d16adbf1fc3baf547435e";
    String catalogueName = "jewellery";
    private ArrayList<Catalogue.Category> category;

    //objects
    private CatalogueService catalogueService;
    private Catalogue catalogue;
    UserService userService;
    User user=null;

    public app42Manager(Context cnt)
    {
        App42API.initialize(cnt, APIKEY, SECRET_KEY);
        catalogueService = App42API.buildCatalogueService();
        userService = App42API.buildUserService();
        category= new ArrayList<Catalogue.Category>();
    }

    public boolean authenticate(String userName,String pwd)
    {
        userService.authenticate(userName , pwd, new App42CallBack() {
            public void onSuccess(Object response)
            {
                User user = (User)response;
                System.out.println("userName is " + user.getUserName());
                System.out.println("sessionId is " + user.getSessionId());
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message : "+ex.getMessage());
            }
        });
        if(user!=null)
            return true;
        else
            return false;
    }

    public void logout()
    {
        userService.logout(sessionId,new App42CallBack() {
            public void onSuccess(Object response)
            {
                App42Response app42response = (App42Response)response;
                System.out.println("response is " + app42response) ;
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message " + ex.getMessage());
            }
        });
    }

    public boolean register(String userName,String pwd,String emailId)
    {
        userService.createUser(userName, pwd, emailId, new App42CallBack() {
            public void onSuccess(Object response) {
                user = (User) response;
                System.out.println("userName is " + user.getUserName());
                System.out.println("emailId is " + user.getEmail());
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
            }
        });
        if(user!=null)
            return true;
        else
            return false;
    }

    public void changepass(String userName,String oldPwd,String newPwd)
    {
        userService.changeUserPassword(userName, oldPwd, newPwd, new App42CallBack() {
            public void onSuccess(Object response) {
                App42Response app42response = (App42Response) response;
                System.out.println("response is " + app42response);
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
            }
        });
    }

    public void setProfile(String userName,String pwd,String emailId,Profile userprof) {
        user = userService.createUser(userName, pwd, emailId);
        user.setProfile(userprof);
        //change with user credentials
       /*Profile profile = user.new Profile();
        profile.setFirstName("Nick");
        profile.setLastName("Gill");
        profile.setSex(UserGender.MALE);
        Date date=new Date();
        profile.setDateOfBirth(date);
        profile.setCity("Houston");
        profile.setState("Texas");
        profile.setPincode("74193");
        profile.setCountry("USA");
        profile.setMobile("+1-1111-111-111");
        profile.setHomeLandLine("+1-2222-222-222");
        profile.setOfficeLandLine("+1-33333-333-333");*/
        userService.createOrUpdateProfile(user, new App42CallBack() {
            public void onSuccess(Object response) {
                User user = (User) response;
                System.out.println("userName is " + user.getUserName());
                System.out.println("firstName is " + user.getProfile().getFirstName());
                System.out.println("city is " + user.getProfile().getCity());
                System.out.println("country is " + user.getProfile().getCountry());
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
            }
        });
    }
    public void getItems()
    {
        catalogueService.getItems(catalogueName, new App42CallBack() {
            public void onSuccess(Object response) {
                catalogue = (Catalogue) response;
                System.out.println("catalogue name is" + catalogue.getName());
                for (int i = 0; i < catalogue.getCategoryList().size(); i++)
                {
                        System.out.println("category name is : " + catalogue.getCategoryList().get(i).getName());
                        category.add(catalogue.getCategoryList().get(i));
                        ArrayList<Catalogue.Category.Item> itemList = catalogue.getCategoryList().get(i).getItemList();
                        for (int j = 0; j < itemList.size(); j++) {
                            System.out.println("Item list Name:" + itemList.get(j).getName());
                            System.out.println("Item List Id:" + itemList.get(j).getItemId());
                            System.out.println("Item List  Description:" + itemList.get(j).getDescription());
                            System.out.println("ItemList tiny Url:" + itemList.get(j).getTinyUrl());
                            System.out.println("ItemList url:" + itemList.get(j).getUrl());
                            System.out.println("Price:" + itemList.get(j).getPrice());
                        }
                }
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
            }
        });
    }

    public Bitmap ItemImage(Catalogue.Category.Item itm)
    {
        Bitmap bmp=null;
        try
        {
            bmp = BitmapFactory.decodeStream((InputStream) new URL(itm.getTinyUrl()).getContent());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
            return bmp;
    }

    public Bitmap ItemTinyImage(Catalogue.Category.Item itm)
    {
        Bitmap bmp=null;
        try
        {
            bmp = BitmapFactory.decodeStream((InputStream) new URL(itm.getUrl()).getContent());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return bmp;
    }

    public ArrayList<Catalogue.Category> categories()
    {
        return category;
    }

    public ArrayList<Catalogue.Category.Item> Itemlist(Catalogue.Category cat)
    {
        return cat.getItemList();
    }
}
