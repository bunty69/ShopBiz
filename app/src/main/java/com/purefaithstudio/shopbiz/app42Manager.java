package com.purefaithstudio.shopbiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.badlogic.gdx.utils.Json;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Response;
import com.shephertz.app42.paas.sdk.android.shopping.Cart;
import com.shephertz.app42.paas.sdk.android.shopping.CartService;
import com.shephertz.app42.paas.sdk.android.shopping.Catalogue;
import com.shephertz.app42.paas.sdk.android.shopping.CatalogueService;
import com.shephertz.app42.paas.sdk.android.shopping.PaymentStatus;
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
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by harsimran singh on 16-10-2015.
 */
public class app42Manager {
    public static boolean flag = false;
    //constants
    final String APIKEY = "50ca63a022074fdeb10d9e3ca5169e4052a631070d2cb62251dbf8a956b6d76c";
    final String SECRET_KEY = "0a6a0814e88d678c2350a9c25c66815fda35ba437c87c7300aa459f93ed007b1";
    final String catalogueName = "jewellery";
    final String DBname = "CATALOGUE";
    final String collection = "items";
    //catalog
    private CatalogueService catalogueService;
    private Catalogue catalogue;
    ArrayList<Catalogue.Category.Item> itemList;
    private ArrayList<Catalogue.Category> categoryList;
    private Catalogue.Category categoryNameList;
    //authntication
    UserService userService;
    User user = null;
    //shopping cart
    CartService cartService;
    Cart  cart;
    Context context;
    //storage service itemextras
    private StorageService storageService;
    private JSONObject json;
    private Json parser;
    private ArrayList<ItemExtra> itemExtras;
    //listeners
    private authenticationListener authenticationListener;
    private OnSignUpListener onSignUpListener;

    public app42Manager(Context cnt) {
        App42API.initialize(cnt, APIKEY, SECRET_KEY);
        catalogueService = App42API.buildCatalogueService();
        storageService = App42API.buildStorageService();
        userService = App42API.buildUserService();
        cartService= App42API.buildCartService();
        this.context = cnt;
        parser = new Json();

    }
    public void initItems()
    {
        Log.i("harjas", "Getitems calling");
        itemExtras = new ArrayList<ItemExtra>();
        loadItemExtra();
        getItems();
        Log.i("harjas", "Getitems called");
    }


    public void setAuthenticationListener(authenticationListener authenticationListener) {
        this.authenticationListener = authenticationListener;
    }

    public void setOnSignUpListener(OnSignUpListener onSignUpListener) {
        this.onSignUpListener = onSignUpListener;
    }

    public void authenticate(String userName, String pwd) {
        userService.authenticate(userName, pwd, new App42CallBack() {
            public void onSuccess(Object response) {
                User user = (User) response;
                System.out.println("userName is " + user.getUserName());
                System.out.println("sessionId is " + user.getSessionId());
                authenticationListener.onAuthenticationSuccess();
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message : " + ex.getMessage());
                authenticationListener.onAuthenticationFailure();
            }
        });

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

    public void register(String userName, String pwd, String emailId) {
        Log.i("harjas123", "register called");
        userService.createUser(userName, pwd, emailId, new App42CallBack() {
            public void onSuccess(Object response) {
                user = (User) response;
                System.out.println("userName is " + user.getUserName());
                System.out.println("emailId is " + user.getEmail());
                onSignUpListener.signUpSuccess();
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
                // onSignUpListener.signUpFailure();
            }
        });

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

                for (int i = 0; i < categoryList.size(); i++) {
                    System.out.println("category name is : " + catalogue.getCategoryList().get(i).getName());
                    if (categoryList.get(i).getName().equals("categories")) {
                        categoryNameList = categoryList.get(i);
                        categoryList.remove(i);
                    } else {
                        itemList = categoryList.get(i).getItemList();
                        for (int j = 0; j < itemList.size(); j++) {
                            System.out.println("Item list Name:" + itemList.get(j).getName());
                            System.out.println("Item List Id:" + itemList.get(j).getItemId());
                            System.out.println("Item List  Description:" + itemList.get(j).getDescription());//bug here
                            System.out.println("ItemList tiny Url:" + itemList.get(j).getTinyUrl());
                            System.out.println("ItemList url:" + itemList.get(j).getUrl());
                            System.out.println("Price:" + itemList.get(j).getPrice());
                        }
                    }
                }
                flag = true;
                Intent intent = new Intent("com.purefaithstudio.shopbiz");
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
        Collections.sort(categoryList, new customCatComparator());
       return categoryList;
    }
    public Catalogue.Category getNameList()
    {
        Collections.sort(categoryNameList.getItemList(),new CustomComparator());
        return categoryNameList;
    }

    public ArrayList<Catalogue.Category.Item> Itemlist(Catalogue.Category cat) {
        return this.itemList;
    }

    public void putItemExtra(String ItemId, String URL1, String URL2, String URL3, int stock, int dis) throws JSONException {
        ItemExtra ie = new ItemExtra(ItemId);
        ie.setURL(0, URL1);
        ie.setURL(1, URL2);
        ie.setURL(2, URL3);
        ie.setStock(stock);
        ie.setDiscount(dis);
        json = new JSONObject();
        json.put("itemID", ItemId);
        json.put("extras", parser.toJson(ie));
        try {
            Storage storage = storageService.saveOrUpdateDocumentByKeyValue(DBname, collection, "itemID", ItemId, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadItemExtra() {
      try {
          json = new JSONObject();
          Storage storage = storageService.findAllDocuments(DBname, collection);
          ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
          for (Storage.JSONDocument jsonDoc : jsonDocList) {
              System.out.println("objectId is " + jsonDoc.getDocId());
              json = new JSONObject(jsonDoc.getJsonDoc());
              System.out.println(json.get("extras"));
              ItemExtra itemExtra = parser.fromJson(ItemExtra.class, (String) json.get("extras"));
              itemExtras.add(itemExtra);
              System.out.println("stock:" + itemExtra.getStock() + "discount:" + itemExtra.getDiscount() + "\nURL 0:" + itemExtra.getURL(0) + "\n URL 1:" + itemExtra.getURL(1));
          }
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
    }

    public ArrayList<ItemExtra> getItemExtras()
    {
            return itemExtras;
    }
    public ItemExtra getItemExtra(String itemID)
    {
        for(ItemExtra itemExtra:itemExtras)
        {
            if(itemExtra.getItemID().equals(itemID))
            return itemExtra;
        }
        return new ItemExtra(itemID);
    }

    public void createCart()
    {
        cartService.createCart(user.getUserName(), new App42CallBack() {
            public void onSuccess(Object response)
            {
                cart  = (Cart)response;
                System.out.println("userName is " + cart.getUserName());
                System.out.println("cartId is " + cart.getCartId());
                System.out.println("Cart Session is"+cart.getCartSession());
                System.out.println("CreationTime is"+cart.getCreationTime());
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    public void addToCart(String itemID,int itemQuantity,double price)
    {
        cartService.addItem(cart.getCartId(), itemID, itemQuantity, price, new App42CallBack() {
            public void onSuccess(Object response) {
                cart = (Cart) response;
                System.out.println("cartId is : " + cart.getCartId());
                ArrayList<Cart.Item> itemList = cart.getItemList();
                for (int i = 0; i < itemList.size(); i++) {
                    System.out.println("ItemId is " + itemList.get(i).getItemId());
                    System.out.println("Price " + itemList.get(i).getPrice());
                    System.out.println("Quantity " + itemList.get(i).getQuantity());
                    System.out.println("Total Amount " + itemList.get(i).getTotalAmount());
                }
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
            }
        });
    }

    public ArrayList<Cart.Item> getCartItems()
    {
        return cart.getItemList();
    }

    public void increaseQuantity(String itemID,int itemQuantity)
    {
        cartService.increaseQuantity(cart.getCartId() , itemID, itemQuantity,new App42CallBack() {
            public void onSuccess(Object response)
            {
                cart  = (Cart )response;
                System.out.println("cartId is :" + cart.getCartId());
                ArrayList<Cart.Item> itemList =  cart.getItemList();
                for(int i=0;i<itemList.size();i++)
                {
                    System.out.println("ItemId is"+itemList.get(i).getItemId());
                    System.out.println("Price "+itemList.get(i).getPrice());
                    System.out.println("Quantity"+itemList.get(i).getQuantity());
                    System.out.println("Total Amount"+itemList.get(i).getTotalAmount());
                }
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    public void decreaseQuantity(String itemID,int itemQuantity)
    {
        cartService.decreaseQuantity(cart.getCartId(), itemID, itemQuantity, new App42CallBack() {
            public void onSuccess(Object response) {
                cart = (Cart) response;
                System.out.println("cartId is :" + cart.getCartId());
                ArrayList<Cart.Item> itemList = cart.getItemList();
                for (int i = 0; i < itemList.size(); i++) {
                    System.out.println("ItemId is" + itemList.get(i).getItemId());
                    System.out.println("Price " + itemList.get(i).getPrice());
                    System.out.println("Quantity" + itemList.get(i).getQuantity());
                    System.out.println("Total Amount" + itemList.get(i).getTotalAmount());
                }
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
            }
        });
    }

    public void removeItem(String itemId)
    {
        CartService cartService = App42API.buildCartService();
        cartService.removeItem(cart.getCartId(), itemId, new App42CallBack() {
            public void onSuccess(Object response) {
                cart = (Cart) response;
                App42Response app42response = (App42Response) response;
                System.out.println("response is " + app42response);
            }

            public void onException(Exception ex) {
                System.out.println("Exception Message" + ex.getMessage());
            }
        });
    }

    public void emptyCart()
    {
        cartService.removeAllItems(cart.getCartId(),new App42CallBack() {
            public void onSuccess(Object response)
            {
                cart = (Cart) response;
                App42Response app42response = (App42Response)response;
                System.out.println("response is " + app42response) ;
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    public void checkOut()
    {
        cartService.checkOut(cart.getCartId(),new App42CallBack() {
            public void onSuccess(Object response)
            {
                cart  = (Cart )response;
                System.out.println("cartId is :" + cart.getCartId());
                System.out.println("State is : "+cart.getState());
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

    public void updatePaymentStatus(String transactionId,PaymentStatus paymentStatus)
    {
        cartService.payment(cart.getCartId(), transactionId, paymentStatus,new App42CallBack() {
            public void onSuccess(Object response)
            {
                cart  = (Cart )response;
                System.out.println("cartId is :" + cart.getCartId());
                Cart.Payment payment=cart.getPayment();
                System.out.println("Transaction Id"+payment.getTransactionId());
                System.out.println("Total Amonut from payment node"+payment.getTotalAmount());
                System.out.println("Status"+payment.getStatus());
                System.out.println("Date"+payment.getDate());
            }
            public void onException(Exception ex)
            {
                System.out.println("Exception Message"+ex.getMessage());
            }
        });
    }

}
//end of app42manager
class CustomComparator implements Comparator<Catalogue.Category.Item>
{
    @Override
    public int compare(Catalogue.Category.Item item, Catalogue.Category.Item t1) {
        return item.getName().compareTo(t1.getName());
    }
}

class customCatComparator implements Comparator<Catalogue.Category>
{
    @Override
    public int compare(Catalogue.Category category, Catalogue.Category t1) {
        return category.getName().compareTo(t1.getName());
    }
}