package com.purefaithstudio.shopbiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Response;
import com.shephertz.app42.paas.sdk.android.ServiceAPI;
import com.shephertz.app42.paas.sdk.android.shopping.Catalogue;
import com.shephertz.app42.paas.sdk.android.shopping.CatalogueService;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.User.Profile;
import com.shephertz.app42.paas.sdk.android.user.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import com.badlogic.gdx.utils.Json;

/**
 * Created by harsimran singh on 16-10-2015.
 */
public class app42Manager {
    final String APIKEY = "50ca63a022074fdeb10d9e3ca5169e4052a631070d2cb62251dbf8a956b6d76c";
    final String SECRET_KEY = "0a6a0814e88d678c2350a9c25c66815fda35ba437c87c7300aa459f93ed007b1";
    final String ADMIN_KEY = "1d86c02b3c89214f53f3b1f08abc4aca6387b9b2e62d16adbf1fc3baf547435e";
    String catalogueName = "jewellery";
    //objects
    ArrayList<Catalogue.Category.Item> itemList;
    private CatalogueService catalogueService;
    private Catalogue catalogue;
    UserService userService;
    private ArrayList<Catalogue.Category> categoryList;
    ArrayList<Bitmap> itemImage;
    User user = null;
    Bitmap bmp;
    Context context;
    boolean block = false;
    private JSONObject json;
    private StorageService sts;
    private String DBname="CATALOGUE";
    private String collection="items";
    private Json parser;
    public static boolean flag=false;


    public app42Manager(Context cnt) {

        App42API.initialize(cnt, APIKEY, SECRET_KEY);
        catalogueService = App42API.buildCatalogueService();
        sts=App42API.buildStorageService();
        userService=App42API.buildUserService();
        Log.i("harjas", "Getitems calling");
        getItems();
Log.i("harjas", "Getitems called");
        this.context = cnt;
        parser=new Json();

    }

    public boolean authenticate(String userName, String pwd) {
        userService.authenticate(userName, pwd, new App42CallBack() {
            public void onSuccess(Object response) {
                User user = (User) response;
                System.out.println("userName is " + user.getUserName());
                System.out.println("sessionId is " + user.getSessionId());
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message : " + ex.getMessage());
            }
        });
        if (user != null)
            return true;
        else
            return false;
    }

    public void logout() {
        userService.logout(user.sessionId, new App42CallBack() {
            public void onSuccess(Object response) {
                App42Response app42response = (App42Response) response;
                System.out.println("response is " + app42response);
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message " + ex.getMessage());
            }
        });
    }

    public boolean register(String userName, String pwd, String emailId) {
        Log.i("harjas123","register called");
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
        if (user != null)
            return true;
        else
            return false;
    }

    public void changepass(String userName, String oldPwd, String newPwd) {
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

    public void setProfile(String userName, String pwd, String emailId, Profile userprof) {
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

    public void getItems() {
        catalogueService.getItems(catalogueName, new App42CallBack() {
            public void onSuccess(Object response) {
                catalogue = (Catalogue) response;
                System.out.println("catalogue name is" + catalogue.getName());
                categoryList = catalogue.getCategoryList();
                System.out.println("category" + categoryList.get(0).getName());
                for (int i = 0; i < catalogue.getCategoryList().size(); i++) {
                    System.out.println("category name is : " + catalogue.getCategoryList().get(i).getName());

                    itemList = catalogue.getCategoryList().get(i).getItemList();
                    for (int j = 0; j < itemList.size(); j++) {
                        System.out.println("Item list Name:" + itemList.get(j).getName());
                        System.out.println("Item List Id:" + itemList.get(j).getItemId());
                        System.out.println("Item List  Description:" + itemList.get(j).getDescription());//bug here
                        System.out.println("ItemList tiny Url:" + itemList.get(j).getTinyUrl());
                        System.out.println("ItemList url:" + itemList.get(j).getUrl());
                        System.out.println("Price:" + itemList.get(j).getPrice());
                    }
                }
                flag=true;
                Intent intent=new Intent("com.purefaithstudio.shopbiz");
                intent.setAction("com.purefaithstudio.shopbiz.CUSTOM");
                context.sendBroadcast(intent);
                Log.i("harjas", "Broadcast sent");
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
            }
        });
    }

    public ArrayList<Catalogue.Category> categories() {
        return categoryList;
    }

    public ArrayList<Catalogue.Category.Item> Itemlist(Catalogue.Category cat) {
        return this.itemList;
    }


    public void loadImage(Catalogue.Category cat) {
        block = true;
        final ArrayList<Catalogue.Category.Item> itemList = cat.getItemList();
        itemImage = new ArrayList<Bitmap>();
        Thread th = new Thread(new Runnable() {
            public void run() {

                URL url = null;
                InputStream content = null;
                try {
                    for (int i = 0; i < itemList.size(); i++) {
                        final Bitmap mIcon1 = BitmapFactory.decodeStream((InputStream) new URL(itemList.get(i).getUrl()).getContent());
                        itemImage.add(mIcon1);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                block = false;
            }
        });
        th.start();

        do {
        } while (block);
    }
public int noOfImagesPerItem(){
    return itemImage.size();
}
    public Bitmap getImage(int i) {
        return itemImage.get(i);
    }


    public void putItemExtra(String ItemId,String URL1,String URL2, String URL3,int stock,int dis) throws JSONException {
        ItemExtra ie= new ItemExtra(ItemId);
        ie.setURL(0, URL1);
        ie.setURL(1,URL2);
        ie.setURL(2, URL3);
        ie.setStock(stock);
        ie.setDiscount(dis);
        json= new JSONObject();
        json.put("itemID",ItemId);
        json.put("extras", parser.toJson(ie));
       try {
           Storage storage = sts.saveOrUpdateDocumentByKeyValue(DBname, collection, "itemID", ItemId, json.toString());
       }catch (Exception e)
       {
           e.printStackTrace();
       }
    }

    public void getItemExtra(String itemID) throws JSONException {
        json= new JSONObject();
        Storage storage=sts.findDocumentByKeyValue(DBname, collection,"itemID",itemID);
        ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
        for(Storage.JSONDocument jsonDoc : jsonDocList)
        {
            System.out.println("objectId is " + jsonDoc.getDocId());
            json = new JSONObject(jsonDoc.getJsonDoc());
            System.out.println(json.get("extras"));
            ItemExtra ie = parser.fromJson(ItemExtra.class, (String) json.get("extras"));
            System.out.println("stock:"+ie.getStock()+"discount:"+ie.getDiscount()+"\nURL 0:"+ie.getURL(0)+"\n URL 1:"+ie.getURL(1));
        }
    }
}
