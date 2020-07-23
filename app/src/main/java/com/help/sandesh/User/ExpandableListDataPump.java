package com.help.sandesh.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ExpandableListDataPump {
    public static Map<String, List<String>> getData() {
        Map<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();


        List<String> center = new ArrayList<String>();
        center.add("+91-11-23978046");
        List<String> AndhraPradesh = new ArrayList<String>();
        AndhraPradesh.add("0866-2410978");
        List<String> Andman = new ArrayList<String>();
        Andman.add("03192-23210");
        List<String> Arunachal = new ArrayList<String>();
        Arunachal.add("9436055743");
        List<String> Assam = new ArrayList<String>();
        Assam.add("6913347770");
        List<String> Bihar = new ArrayList<String>();
        Bihar.add("104");
        List<String> Chandigarh = new ArrayList<String>();
        Chandigarh.add("9779558282");
        List<String> Chhattisgarh = new ArrayList<String>();
        Chhattisgarh.add("104");
        List<String> Daman = new ArrayList<String>();
        Daman.add("104");
        List<String> Delhi = new ArrayList<String>();
        Delhi.add("011-22307145");
        List<String> Goa = new ArrayList<String>();
        Goa.add("104");
        List<String> Gujarat = new ArrayList<String>();
        Gujarat.add("104");
        List<String> Haryana = new ArrayList<String>();
        Haryana.add("8558893911");
        List<String> Himachal = new ArrayList<String>();
        Himachal.add("104");
        List<String> Jharkhand = new ArrayList<String>();
        Jharkhand.add("104");
        List<String> Jandk = new ArrayList<String>();
        Jandk.add("01912520982");
        Jandk.add("194-2440283");
        List<String> Karnataka = new ArrayList<String>();
        Karnataka.add("104");
        List<String> Kerala = new ArrayList<String>();
        Kerala.add("0471-2552056");
        List<String> Ladakh = new ArrayList<String>();
        Ladakh.add("1982256462");
        List<String> Lakshadweep = new ArrayList<String>();
        Lakshadweep.add("104");
        List<String> Madhya = new ArrayList<String>();
        Madhya.add("0755-2527177");
        List<String> Maharashtra = new ArrayList<String>();
        Maharashtra.add("020-26127394");
        List<String> Manipur = new ArrayList<String>();
        Manipur.add("3852411668");
        List<String> Meghalaya = new ArrayList<String>();
        Meghalaya.add("108");
        List<String> Mizoram = new ArrayList<String>();
        Mizoram.add("102");
        List<String> Nagaland = new ArrayList<String>();
        Nagaland.add("7005539653");
        List<String> Odisha = new ArrayList<String>();
        Odisha.add("9439994859");
        List<String> Punjab= new ArrayList<String>();
        Punjab.add("104");
        List<String> Puducherry= new ArrayList<String>();
        Puducherry.add("104");
        List<String> Rajasthan= new ArrayList<String>();
        Rajasthan.add("0141-2225624");
        List<String> Sikkim= new ArrayList<String>();
        Sikkim.add("104");
        List<String> Tamil= new ArrayList<String>();
        Tamil.add("044-29510500");
        List<String> Telangana= new ArrayList<String>();
        Telangana.add("104");
        List<String> Tripura= new ArrayList<String>();
        Tripura.add("0381-2315879");
        List<String> Uttarakhand= new ArrayList<String>();
        Uttarakhand.add("104");
        List<String> UP= new ArrayList<String>();
        UP.add("18001805145");
        List<String> West= new ArrayList<String>();
        West.add("1800313444222");
        West.add("3323412600");




        expandableListDetail.put("CENTER HELPLINE", center);
        expandableListDetail.put("Andhra Pradesh", AndhraPradesh);
        expandableListDetail.put("Arunachal Pradesh",Arunachal );
        expandableListDetail.put("Assam",Assam);
        expandableListDetail.put("Bihar",Bihar );
        expandableListDetail.put("Chhattisgarh",Chhattisgarh );
        expandableListDetail.put("Goa", Goa);
        expandableListDetail.put("Gujarat",Gujarat );
        expandableListDetail.put("Haryana",Haryana );
        expandableListDetail.put("Himachal Pradesh",Himachal );
        expandableListDetail.put("Jharkhand", Jharkhand);
        expandableListDetail.put("Karnataka",Karnataka );
        expandableListDetail.put("Kerala",Kerala );
        expandableListDetail.put("Madhya Pradesh",Madhya );
        expandableListDetail.put("Maharashtra",Maharashtra );
        expandableListDetail.put("Manipur",Manipur );
        expandableListDetail.put("Meghalaya",Meghalaya );
        expandableListDetail.put("Mizoram",Mizoram );
        expandableListDetail.put("Nagaland",Nagaland );
        expandableListDetail.put("Odisha", Odisha);
        expandableListDetail.put("Punjab",Punjab );
        expandableListDetail.put("Rajasthan",Rajasthan );
        expandableListDetail.put("Sikkim",Sikkim );
        expandableListDetail.put("Tamil Nadu",Tamil );
        expandableListDetail.put("Telangana", Telangana);
        expandableListDetail.put("Tripura",Tripura );
        expandableListDetail.put("Uttarakhand", Uttarakhand);
        expandableListDetail.put("Uttar Pradesh",UP );
        expandableListDetail.put("West Bengal", West);
        expandableListDetail.put("Andaman and NicobarIslands",Andman );
        expandableListDetail.put("Chandigarh", Chandigarh);
        expandableListDetail.put("Dadra and Nagar Haveli and Daman & Diu",Daman );
        expandableListDetail.put("Delhi", Delhi);
        expandableListDetail.put("Jammu & Kashmir",Jandk );
        expandableListDetail.put("Ladakh",Ladakh );
        expandableListDetail.put("Lakshadweep",Lakshadweep );
        expandableListDetail.put("Puducherry", Puducherry);


        TreeMap<String, List<String>> treeMap = new TreeMap<String, List<String>>( expandableListDetail );
        expandableListDetail.clear();
        expandableListDetail.putAll(treeMap);
        return expandableListDetail;

    }
}
