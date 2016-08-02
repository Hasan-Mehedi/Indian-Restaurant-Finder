package com.example.shaon.desirestaurantfinder;

import android.os.Parcel;
import android.os.Parcelable;


public class Results implements Parcelable {
    String name;
    String address;
    String locality;
    String city;
    String zipcode;
    String cuisines;
    double longitude;
    double latitude;
    String average_cost_for_two;
    String price_range;
    String offers;
    String thumb;
    String aggregate_rating;
    String votes;
    String photos_url;
    String menu_url;
    String featured_image_url;
    String has_online_delivery;
    String is_delivering_now;
    String events_url;




    public Results(String name,String address,String locality,String city,String zipcode,double latitude,double longitude,String cuisines,String average_cost_for_two,String price_range,String offers,String thumb,String aggregate_rating,String votes,String photos_url,
                   String menu_url,String featured_image_url,String has_online_delivery,String is_delivering_now,String events_url) {
        this.name = name;
        this.address = address;
        this.locality=locality;
        this.city = city;
        this.zipcode = zipcode;
        this.cuisines = cuisines;
        this.longitude = longitude;
        this.latitude = latitude;
        this.average_cost_for_two = average_cost_for_two;
        this.price_range = price_range;
        this.offers = offers;
        this.thumb = thumb;
        this.aggregate_rating=aggregate_rating;
        this.votes = votes;
        this.photos_url = photos_url;
        this.menu_url = menu_url;
        this.featured_image_url = featured_image_url;
        this.has_online_delivery = has_online_delivery;
        this.is_delivering_now = is_delivering_now;
        this.events_url = events_url;

    }

    protected Results(Parcel in) {
        name = in.readString();
        address = in.readString();
        city = in.readString();
        locality = in.readString();
        zipcode = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        cuisines = in.readString();
        average_cost_for_two = in.readString();
        price_range = in.readString();
        offers = in.readString();
        thumb = in.readString();
        aggregate_rating = in.readString();
        votes = in.readString();
        photos_url = in.readString();
        menu_url = in.readString();
        featured_image_url = in.readString();
        has_online_delivery = in.readString();
        is_delivering_now = in.readString();
        events_url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(locality);
        dest.writeString(zipcode);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(cuisines);
        dest.writeString(average_cost_for_two);
        dest.writeString(price_range);
        dest.writeString(offers);
        dest.writeString(aggregate_rating);
        dest.writeString(votes);
        dest.writeString(photos_url);
        dest.writeString(menu_url);
        dest.writeString(featured_image_url);
        dest.writeString(has_online_delivery);
        dest.writeString(is_delivering_now);
        dest.writeString(events_url);
    }

    @SuppressWarnings("unused")
    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };
}
